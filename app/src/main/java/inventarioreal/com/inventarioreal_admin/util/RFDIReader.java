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
    public UHFManager manager = null;

    private boolean runReader=false;
    private boolean startReader=false;

    /**
     * Var to determinate what kind of device is going to be use
     * Small phone or the big one
     */
    public static final int  SMALL= 1;
    public static final int  BIG= 2;
    public static final String  TAG= "RFDIReader";

    public RFDIReader(int type, RFDIListener listener){
        this.type=type;
        this.listener= listener;
        epcs = new LinkedList<String>();
    }

    public void initSDK(){
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
                    break;
                }
            }
            epcs.add(epc);
            listener.onEpcAdded(epc);

        }
    }
    //region Small SDK
    private UhfManager uhfManager;
    private void initSDKSmall(){
        try {
            uhfManager = UhfManager.getInstance();
            uhfManager.setOutputPower(30);
            uhfManager.setWorkArea(2);
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
                    // manager.stopInventoryMulti()
                    epcList = uhfManager.inventoryRealTime(); // inventory real time
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
    private void initSDKBig() {
        String freBand = FreRegion.TMR_REGION_FCC;

        manager = UHFManager.getInstance();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (manager.initRfid()) {
            manager.setProtocol(manager.PROTOCOL_ISO_18000_6C);
            manager.setFreBand(freBand);
            freBand="FCC";
//            freBand = getResources().getStringArray(R.array.freregions)[Integer.parseInt(freBand,16)];
            //set power
//            if (manager.setReadPower(mSharePreferences.getInt("readPower",30))){
            if (manager.setReadPower(30)){
//                manager.setWritePower(mSharePreferences.getInt("writePower",30));
                manager.setWritePower(30);
            }
            else  {
//                manager.setReadPower(mSharePreferences.getInt("readPower",27));
                manager.setReadPower(27);
//                manager.setWritePower(mSharePreferences.getInt("writePower",27));
                manager.setWritePower(27);
            }
        }else{
            manager.close();
            manager = null ;
        }
    }
    long startTime = 0L ;
    private boolean isRunning = false ;
    private boolean isStart = false ;

    private void runInventory() {
        if (!isStart) {
            isStart = true ;
            isRunning = true ;
            new Thread(inventoryTask).start();
        }else{
            manager.stopInventory() ;
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isStart = false ;
            isRunning = false ;
        }
    }

    private Runnable inventoryTask = new Runnable() {
        @Override
        public void run() {
//            if (checkBox_tid.isChecked()) {
//                while (isRunning) {
//                    if (isStart) {
//                        List<TagDataModel>  tagDataModels = manager.ReadData(UHFManager.TID,0,6,new byte[4]);
//                        if (tagDataModels != null&& tagDataModels.size()>0) {
//                            for (TagDataModel tagDataModel:tagDataModels){
//                                String tid = tagDataModel.DATA;
//                                Message msg = new Message();
//                                msg.what = 1;
//                                Bundle b = new Bundle();
//                                b.putString("epc", tid);
//                                msg.setData(b);
//                                handler.sendMessage(msg);
//                            }
//                        }
//                    }
//                }
//            } else {
//                manager.startInventory(isMulti);
//            Log.e("inventoryTask", "start inventory task") ;
            manager.startInventory(true);
                while (isRunning) {
                    if (isStart) {
                        byte[] bytess = manager.getEPCByteBuff();
                        if (bytess != null) {
                            EPCDataModel epcDataModel = manager.getEPC(bytess);
                            if (epcDataModel != null) {
                                byte[] epcdata = epcDataModel.EPC;
                                int rssi = epcDataModel.RSSI;
                                String epc = Tools.Bytes2HexString(epcdata, epcdata.length);
                                addToList(epc);
                            }
                        }
                    }
                }
                manager.stopInventory();
//            }
        }
    } ;
    //endregion

    //region LyfeCicle
    public void startReader(){
        if(this.type==SMALL){
            uhfManager = UhfManager.getInstance();
            if (uhfManager == null) {
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            initSDKSmall();
        }else{
//            registerReceiver(keyReceiver, filter) ;
//            initSDKBig();
            runInventory();
        }
    }

//    @Override
    public void onResume() {
//        super.onResume();
        startReader();

    }
//    @Override
    public void onPause() {
//        super.onPause();
        if(this.type==SMALL){
            startReader = false;
            if (uhfManager != null) {
                uhfManager.close();
            }
        }else{
            if (isStart) runInventory();
        }

    }
//    @Override
    public void onDestroy() {
        if(this.type==SMALL){
            startReader = false;
            if (uhfManager != null) {
                uhfManager.close();
            }
        }else{
            isStart = false ;
            isRunning = false ;
        }
//        super.onDestroy();
    }
    //endregion
}
