package inventarioreal.com.inventarioreal_admin.util;

import android.util.Log;

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
    private int type=0;
    private RFDIListener listener;
//    private boolean isRunning = false ;
//    private boolean isStart = false ;


    public boolean isStartReader() {
        return startReader;
    }

    public void setStartReader(boolean startReader) {
        this.startReader = startReader;
    }

    private boolean runReader=false;
    private boolean startReader=false;

    /**
     * Var to determinate what kind of device is going to be use
     * Small phone or the big one
     */
    public static final int  SMALL= 1;
    public static final int  BIG= 2;
    public static final String  TAG= "RFDIReader";

//    public RFDIReader(int type, RFDIListener listener){
//        this.type=type;
//        this.listener= listener;
//        epcs = new LinkedList<String>();
//    }
    public RFDIReader(RFDIListener listener){
        this.type=BIG;
        this.listener= listener;
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
    //region Small SDK
    private UhfManager managerSmall;
    private void initSDKSmall(){
        try {
            managerSmall = UhfManager.getInstance();
            managerSmall.setOutputPower(30);
            managerSmall.setWorkArea(2);
            Thread thread = new InventoryThread();
            thread.start();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }



    }
    /**
     * Inventory Epcs Thread
     */

    class InventoryThread extends Thread {
        private List<byte[]> epcList;

        @Override
        public void run() {
            super.run();
            while (runReader) {
                if (startReader) {
                    // managerBig.stopInventoryMulti()
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

//    private void addSmall(final String epc){
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                // The epc for the first time
//                if (epcs.isEmpty()) {
//                    epcs.add(epc);
//                } else {
//                    for (int i = 0; i < epcs.size(); i++) {
//                        Epcs mEPC = epcs.get(i);
//                        // list contain this epc
//                        if (epc.equals(mEPC.getEpc())) {
//                            mEPC.setCount(mEPC.getCount() + 1);
//                            break;
//                        } else if (i == (epcs.size() - 1)) {
//                            //Valido que el epc no este baneado
//                            if(!isBanned(epc)){
//                                createEpc(epc);
//                            }
//                        }
//                    }
//
//                }
//            }
//        });
//    }
    //endregion

    //region Big sdk
    public UHFManager managerBig = null;
    private boolean initSDKBig() {
        String freBand = FreRegion.TMR_REGION_FCC;

        managerBig = UHFManager.getInstance();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        if (managerBig.initRfid()) {
            managerBig.setProtocol(managerBig.PROTOCOL_ISO_18000_6C);
            managerBig.setFreBand(freBand);
            freBand="FCC";
//            freBand = getResources().getStringArray(R.array.freregions)[Integer.parseInt(freBand,16)];
            //set power
//            if (managerBig.setReadPower(mSharePreferences.getInt("readPower",30))){
            if (managerBig.setReadPower(30)){
//                managerBig.setWritePower(mSharePreferences.getInt("writePower",30));
                managerBig.setWritePower(30);
            }
            else  {
//                managerBig.setReadPower(mSharePreferences.getInt("readPower",27));
                managerBig.setReadPower(27);
//                managerBig.setWritePower(mSharePreferences.getInt("writePower",27));
                managerBig.setWritePower(27);
            }
            return true;
        }else{
            managerBig.close();
            managerBig = null ;
            return false;
        }
    }


    private void runInventorySDKBig() {
        startReader = true ;
        runReader = true ;
        new Thread(inventoryTaskBig).start();
    }

    private void stopInventorySDKBig(){
//        managerBig.stopInventory() ;
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        startReader = false ;
        runReader = false ;
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
            }
//            }
        }
    } ;
    //endregion

    //region LyfeCicle
    public void startReader(){
        if(this.type==SMALL){
//            managerSmall = UhfManager.getInstance();
//            if (managerSmall == null) {
//                return;
//            }
            try {
                runReader=true;
                startReader=true;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            initSDKSmall();
        }else{
//            registerReceiver(keyReceiver, filter) ;
//            initSDKBig();
            runInventorySDKBig();
        }
    }

    public void stopReader(){
        switch (type){
            case SMALL:
                initSDKSmall();
                break;
            case BIG:
                stopInventorySDKBig();
                break;
        }
    }

//    @Override
    public void onResume() {
//        super.onResume();
//        startReader();

    }
//    @Override
    public void onPause() {
//        super.onPause();
        if(this.type==SMALL){
            startReader = false;
            if (managerSmall != null) {
                managerSmall.close();
            }
        }else{
            startReader = false;
            if(managerBig!=null)
                managerBig.close();
//            if (isStart) runInventorySDKBig();
        }

    }
//    @Override
    public void onDestroy() {
        if(this.type==SMALL){
            startReader = false;
            if (managerSmall != null) {
                managerSmall.close();
            }
        }else{
            startReader = false ;
            runReader = false ;
            if(managerBig!=null)
                managerBig.close();
        }
//        super.onDestroy();
    }
    //endregion
}
