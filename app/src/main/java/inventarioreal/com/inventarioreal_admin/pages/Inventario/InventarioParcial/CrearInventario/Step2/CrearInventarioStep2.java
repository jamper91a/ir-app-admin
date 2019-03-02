package inventarioreal.com.inventarioreal_admin.pages.Inventario.InventarioParcial.CrearInventario.Step2;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;
import com.handheld.UHF.UhfManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.LinkedList;
import java.util.List;

import cn.pda.serialport.Tools;
import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Intents.RequestInventariorCrear2;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventarioParcial.CrearInventario.Step2.tabs.EanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventarioParcial.CrearInventario.Step2.tabs.EanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventarioParcial.CrearInventario.Step2.tabs.TotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventarioParcial.CrearInventario.Step2.tabs.TotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epcs;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosProductos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class CrearInventarioStep2 extends CicloActivity {

    private SlidingMenu menu;
    private UhfManager uhfManager;
//    public ListAdapter1 adapter1;
//    private LinkedList<Epcs> epcs = new LinkedList<>();
    private String TAG="CrearInventarioStep2";
    private DataBase db = DataBase.getInstance(this);
    private Toolbar mTopToolbar;
    private RequestInventariorCrear2 requestInventariorCrear2;
    private LinkedList<InventariosProductos> inventariosProductos = new LinkedList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_inventario_parcial_crear_inventario_step_2);
        //region UhF
        Thread thread = new InventoryThread();
        thread.start();
        //endregion
        //region Obtener parametros
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.requestInventariorCrear2 = gson.fromJson(message, RequestInventariorCrear2.class);
        //endregion
        this.tabsInit();
//        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(mTopToolbar);
    }
    @Override
    public void initGui() {


        addElemento(new Animacion(findViewById(R.id.lnl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnCan),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnLee),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnFin),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBor),Techniques.FadeInLeft));


    }

    @Override
    public void getData() {
        totalViewModel = ViewModelProviders.of(this).get(TotalViewModel.class);
        eanPluVieModel = ViewModelProviders.of(this).get(EanPluViewModel.class);
        eanPluVieModel.init();
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnFin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServices.crearInventario(
                        requestInventariorCrear2.getZona_id().getId(),
                        inventariosProductos,
                        CrearInventarioStep2.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                admin.toast("Ok");
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast("fail");
                            }
                        }

                );
            }
        });
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

    private Epcs createEpc(String epc){
        Epcs epcTag = new Epcs();
        //Busco el epc en la base de datos interna
        Epcs epcDb= (Epcs) db.getById(Constants.table_epcs, Constants.epc, "'"+epc+"'", Epcs.class);
        if(epcDb!=null){
            //Busco el producto zonas al que pertenece este tag
            ProductosZonas proZon=
                    (ProductosZonas) db.getByColumn(
                            Constants.table_productos_zonas,
                            Constants.epcs_id,
                            epcDb.getId()+"",
                            ProductosZonas.class).get(0);
            if(epcTag.getId()==0)
            {
                epcTag.setEpc(epc);
                epcTag.setCount(1);
            }

            //Informacion requeria por el servicio web de crear inventario
            InventariosProductos ip = new InventariosProductos();
            ip.setZonas_id(requestInventariorCrear2.getZona_id());
            ip.setProductoz_zona_id(proZon);
            ip.setProductos_epcs_id(epcDb);

            inventariosProductos.add(ip);
            eanPluVieModel.addEpc(epcTag);
//            epcs.add(epcTag);
            totalViewModel.setAmount(inventariosProductos.size());
        }
        return epcTag;
    }

    private void addToList(final String epc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // The epc for the first time
                if (eanPluVieModel.getEpcs().getValue().isEmpty()) {
                    createEpc(epc);
                } else {
                    for (int i = 0; i < eanPluVieModel.getEpcs().getValue().size(); i++) {
                        Epcs mEPC = eanPluVieModel.getEpcs().getValue().get(i);
                        // list contain this epc
                        if (epc.equals(mEPC.getEpc())) {
                            mEPC.setCount(mEPC.getCount() + 1);
                            break;
                        } else if (i == (eanPluVieModel.getEpcs().getValue().size() - 1)) {
                            Epcs epcObj = createEpc(epc);
                        }
                    }

                }
            }
        });
    }

    /**
     * Inventory Epcs Thread
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

    //region Tab Total
    private ViewPager mViewPager;
    TotalViewModel totalViewModel;
    EanPluViewModel eanPluVieModel;
    //endregion

    //region Tabs configuration
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public void tabsInit() {
//        /region Tabs section
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new CrearInventarioStep2.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        //endregion
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TotalFragment total = new TotalFragment();
                    return total;
                case 1:
                    EanPluFragment eanPlu = EanPluFragment.newInstance();
                    eanPlu.setAdmin(admin);
                    return eanPlu;
//                case 2:
//                    Epc epc = new Epc();
//                    return epc;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
    //endregion
}
