package inventarioreal.com.inventarioreal_admin.pages.Inventario;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.androidanimations.library.Techniques;
import com.handheld.UHF.UhfManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cn.pda.serialport.Tools;
import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapter1;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class InventarioCrear2 extends CicloActivity {

    private SlidingMenu menu;
    private UhfManager uhfManager;
    public ListAdapter1 adapter1;
    private LinkedList<Epc> epcs = new LinkedList<>();
    private String TAG="InventarioCrear2";
    private DataBase db = DataBase.getInstance(this);
    private Toolbar mTopToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_inventario_crear_2);
        //start inventory thread
        Thread thread = new InventoryThread();
        thread.start();
//        this.menu =init_menu(this,R.layout.layout_menu);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
    }
    @Override
    public void initGui() {

        addElemento(new Animacion(findViewById(R.id.lnl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lst1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnCan),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnLee),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnFin),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBor),Techniques.FadeInLeft));

        adapter1 = new ListAdapter1(this, admin, epcs, new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
            }

            @Override
            public void onLongItemClick(Object item) {

            }

            @Override
            public void onItemClick(int view, Object item) {

            }
        });
        RecyclerView lst1 = (RecyclerView)getElemento(R.id.lst1).getElemento();
        lst1.setLayoutManager(new LinearLayoutManager(this));
        lst1.setAdapter(adapter1);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {
    }



    @Override
    public void hasAllPermissions() {
        startFlag=true;
    }

    //region UHD Sdk
    public void initSdk(){
        try {
            uhfManager = UhfManager.getInstance();
            uhfManager.setOutputPower(26);
            uhfManager.setWorkArea(2);
            startFlag=true;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    private boolean runFlag=true;
    private boolean startFlag = false;

    @Override
    protected void onResume() {
        super.onResume();
        uhfManager = UhfManager.getInstance();
        if (uhfManager == null) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initSdk();
    }

    @Override
    protected void onPause() {
        startFlag = false;
        uhfManager.close();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        startFlag = false;
        if (uhfManager != null) {
            uhfManager.close();
        }
        super.onDestroy();
    }

    private Epc createEpc(String epc){
        Epc epcTag = new Epc();
        //Busco el epc en la base de datos interna
        HashMap<String, String> epcDb=db.getById(Constants.table_epcs, Constants.epc, "'"+epc+"'");
        //Convierto el resultado de la base de datos al objecto
        epcTag.setFromHashMap(epcDb);
        if(epcTag.getId()==0)
        {
            epcTag.setEpc(epc);
            epcTag.setCount(1);
        }
        epcs.add(epcTag);
        adapter1.add(epcTag);
        return epcTag;
    }

    private void addToList(final String epc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // The epc for the first time
                if (epcs.isEmpty()) {
                    createEpc(epc);
                } else {
                    for (int i = 0; i < epcs.size(); i++) {
                        Epc mEPC = epcs.get(i);
                        // list contain this epc
                        if (epc.equals(mEPC.getEpc())) {
                            mEPC.setCount(mEPC.getCount() + 1);
                            break;
                        } else if (i == (epcs.size() - 1)) {
                            Epc epcObj = createEpc(epc);
                        }
                    }

                }
            }
        });
    }

    /**
     * Inventory Epc Thread
     */

    class InventoryThread extends Thread {
        private List<byte[]> epcList;

        @Override
        public void run() {
            super.run();
            while (runFlag) {
                if (startFlag) {
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
    //endregion

    //region Menu

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(getString(R.string.log_out));
//        getMenuInflater().inflate(menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG,item.getTitle().toString());
        if(item.getTitle().equals(getString(R.string.log_out))){
            admin.log_out(Login.class);
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_favorite) {
//            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
