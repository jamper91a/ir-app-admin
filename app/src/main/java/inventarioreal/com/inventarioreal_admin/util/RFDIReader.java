package inventarioreal.com.inventarioreal_admin.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.handheld.UHF.UhfManager;

import java.util.LinkedList;
import java.util.List;

import cn.pda.serialport.Tools;
import inventarioreal.com.inventarioreal_admin.listener.RFDIListener;
import inventarioreal.com.inventarioreal_admin.util.uhfm.EPCDataModel;
import inventarioreal.com.inventarioreal_admin.util.uhfm.FreRegion;
import inventarioreal.com.inventarioreal_admin.util.uhfm.UHFManager;

public class RFDIReader {

    private List<String> epcs;
    private RFDIListener listener;

    private boolean runReader=false;
    private boolean startReader=false;

    private Activity myActivity;

    /**
     * Var to determinate what kind of device is going to be use
     * Small phone or the big one
     */

    private int type=0;
    public static final int  SMALL= 1;
    public static final int  BIG= 2;
    public static final String  TAG= "RFDIReader";


    //region Public functions
    public RFDIReader(RFDIListener listener, Activity myActivity){
        this.type=SMALL;
        this.listener= listener;
        this.myActivity = myActivity;
        epcs = new LinkedList<String>();
    }

    public void initSDK(){
        epcs = new LinkedList<String>();
        switch (type){
            case SMALL:
                initSDKSmall();
                break;
            case BIG:
                initSDKBig();
                break;
        }
    }


    public void cleanEpcs(){
        this.epcs=new LinkedList<String>();
    }
    private void addToList(final String epc) {
        if (epcs.isEmpty()) {
            epcs.add(epc);
            listener.onEpcAdded(epc);
        } else {
            for (int i = 0; i < epcs.size(); i++) {
                String myEpc = epcs.get(i);
                if(myEpc.equals(epc)){
                    listener.onEpcRepeated(epc);
                    return;
                }
            }
            epcs.add(epc);
            listener.onEpcAdded(epc);

        }
    }

    public void startReader(){
        runReader=true;
        startReader=true;
        if(this.type==SMALL){
            startSmallReader();
        }else{
            startBigReader();
        }
    }

    public void stopReader(){
        runReader=false;
        startReader=false;
        switch (type){
            case SMALL:
                stopSmallReader();
                break;
            case BIG:
                stopBigReader();
                break;
        }
    }

    public boolean isStartReader() {
        return startReader;
    }

    public void setStartReader(boolean startReader) {
        this.startReader = startReader;
    }

    //endregion

    //region Small SDK
    private UhfManager managerSmall=null;
    private Thread threadSmall;
    private void initSDKSmall(){
        if (managerSmall==null) {
            try {
                managerSmall = UhfManager.getInstance();
                managerSmall.setOutputPower(30);
                managerSmall.setWorkArea(2);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }


    }
    private void startSmallReader(){
        initSDKSmall();
        threadSmall = new InventoryThread();
        threadSmall.start();
    }
    private void stopSmallReader(){
        try {
            if(threadSmall!=null)
                threadSmall.interrupt();
            if (managerSmall != null) {
                managerSmall.stopInventoryMulti();
                managerSmall.close();
                managerSmall = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class InventoryThread extends Thread {
        private List<byte[]> epcList;

        @Override
        public void run() {
            super.run();
            while (runReader) {
                if (startReader) {
                    epcList = managerSmall.inventoryRealTime(); // inventory real time
                    if (epcList != null && !epcList.isEmpty()) {
                        for (byte[] epc : epcList) {
                            String epcStr = Tools.Bytes2HexString(epc,
                                    epc.length);
                            addToList(epcStr);
                        }
                    }
                    epcList = null;
                    try {
                        Thread.sleep( 40);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //endregion

    //region Big sdk
    public UHFManager managerBig = null;
    private Thread threadBig;
    private void initSDKBig() {
        if (managerBig==null) {
            String freBand = FreRegion.TMR_REGION_FCC;
            managerBig = UHFManager.getInstance();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (managerBig.initRfid()) {
                managerBig.setProtocol(managerBig.PROTOCOL_ISO_18000_6C);
                managerBig.setFreBand(freBand);
                freBand="FCC";
                if (managerBig.setReadPower(30)){
                    managerBig.setWritePower(30);
                }
                else  {
                    managerBig.setReadPower(27);
                    managerBig.setWritePower(27);
                }
            }else{
                managerBig.close();
                managerBig = null ;
            }
        }
    }
    private void startBigReader(){
        initSDKBig();
        threadBig = new Thread(inventoryTaskBig);
            threadBig.start();
    }
    private void stopBigReader(){
        if(threadBig!=null)
            threadBig.interrupt();
        if(managerBig!=null){
            managerBig.stopInventory();
            managerBig.close();
            managerBig = null;
        }
    }

    private Runnable inventoryTaskBig = new Runnable() {
        @Override
        public void run() {

            if (managerBig!=null) {
                managerBig.startInventory(true);
                while (runReader) {
                    if (startReader) {
                        byte[] bytess = managerBig.getEPCByteBuff();
                        if (bytess != null) {
                            EPCDataModel epcDataModel = managerBig.getEPC(bytess);
                            if (epcDataModel != null) {
                                byte[] epcdata = epcDataModel.EPC;
                                int rssi = epcDataModel.RSSI;
                                String epc = Tools.Bytes2HexString(epcdata, epcdata.length);
                                addToList(epc);
                            }
                        }
                    }
                }
                Log.i("thread", "Finished");
            }
//            }
        }
    } ;

    private BroadcastReceiver keyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int keyCode = intent.getIntExtra("keyCode", 0) ;

            listener.onKeyPresses(keyCode+"");
            if(keyCode == 0){//H941
                keyCode = intent.getIntExtra("keycode", 0) ;
            }
            Log.e("key ","keyCode = " + keyCode) ;
            boolean keyDown = intent.getBooleanExtra("keydown", false) ;

            Log.e("key ","down = " + keyDown) ;
            if(keyDown && (keyCode == KeyEvent.KEYCODE_F1 || keyCode == KeyEvent.KEYCODE_F2
                    ||keyCode == KeyEvent.KEYCODE_F3 || keyCode == KeyEvent.KEYCODE_F4 ||
                    keyCode == KeyEvent.KEYCODE_F5 )){
                listener.onStateChanged(!isStartReader());
            }
        }
    } ;
    //endregion

    //region LyfeCicle




//    @Override
    public void onResume() {
        IntentFilter filter = new IntentFilter() ;
        filter.addAction("android.rfid.FUN_KEY");
        this.myActivity.registerReceiver(keyReceiver, filter) ;
//        super.onResume();
//        startReader();

    }
//    @Override
    public void onPause() {
//        super.onPause();
        startReader = false;
        runReader = false;
        this.myActivity.unregisterReceiver(keyReceiver);
        if(this.type==SMALL){

            if (managerSmall != null) {
                managerSmall.close();
            }
        }else{
            if(managerBig!=null)
                managerBig.close();
        }

    }
//    @Override
    public void onDestroy() {
        runReader=false;
        startReader = false;
        stopReader();
    }
    //endregion
}
