package inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.ViewInventoriesByZone.Step2;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.Intents.RequestInventoryZoneStep2;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.ViewInventoriesByZone.Step1.VisualizarInventarioCooperativoPorZonaStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EpcFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EpcViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventario.EanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventario.EanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventario.TotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventario.TotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventariosConsolidados.EanPluConsolidadoFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventariosConsolidados.EanPluConsolidadoViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventariosConsolidados.TotalConsolidadoFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventariosConsolidados.TotalConsolidadoViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.GetProductsByConsolidatedInventoryResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventoryHasProduct;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class VisualizarInventarioColaborativoPorZonaStep2 extends CicloActivity {

    final DataBase db = DataBase.getInstance(this);
    private String TAG="VisualizarInventarioColaborativoPorZonaStep2";
    private LinkedList<InventoryHasProduct> inventariosProductos = new LinkedList<>();
    private Gson gson = new Gson();
    private RequestInventoryZoneStep2 requestInventarioPorZonaStep2=null;
    private Inventory inventario=null;
    private ConsolidatedInventory inventarioConsolidado=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //region Obtener parametros
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        try {
            this.requestInventarioPorZonaStep2 = gson.fromJson(message, RequestInventoryZoneStep2.class);
            this.inventario = requestInventarioPorZonaStep2.getInventory();
            this.inventarioConsolidado = requestInventarioPorZonaStep2.consolidatedInventory;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        init(this,this,R.layout.activity_inventario_parcial_visualizar_por_zona_step_2);
        this.tabsInit();
        //endregion
        // toolbar
        if(this.requestInventarioPorZonaStep2.getInventory()!=null)
            getSupportActionBar().setTitle(R.string.visualizar_inventario_cooperativo_por_zonas);
        else
            getSupportActionBar().setTitle(R.string.visualizar_inventario_cooperativo_consolidado);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {

        addElemento(new Animacion(findViewById(R.id.lnl2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSal),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnEnv),Techniques.FadeInLeft));

    }

    @Override
    public void getData() {

        if(inventario!=null){
            totalViewModel = ViewModelProviders.of(this).get(TotalViewModel.class);
            eanPluVieModel = ViewModelProviders.of(this).get(EanPluViewModel.class);
            epcVieModel = ViewModelProviders.of(this).get(EpcViewModel.class);

            WebServices.getProductsByInventory(inventario.getId(), this, admin, new ResultWebServiceInterface() {
                @Override
                public void ok(ResultWebServiceOk ok) {
                    inventario = (Inventory) ok.getData();
                    //Busco la zona del inventory
                    Zone zona = (Zone) db.findById(Constants.table_zones, inventario.getZone().getId()+"", Zone.class);
                    if(zona!=null){
                        inventario.setZone(zona);
                    }
                    //Actualizo la cantidad
                    totalViewModel.setInventario(inventario);
                    for (ProductHasZone pz: inventario.getProducts()
                    ) {
                        //Busco el epc del producto
                        Epc epc = (Epc) db.findById(
                                Constants.table_epcs,
                                pz.getEpc().getId()+"",
                                Epc.class
                        );
                        if(epc!=null)
                            pz.setEpc(epc);
                        eanPluVieModel.addProductoZona(pz);
                        epcVieModel.addProductoZona(pz);
                    }

                }

                @Override
                public void fail(ResultWebServiceFail fail) {
                    admin.toast(fail.getError());
                }
            });
        }

        if(inventarioConsolidado!=null){
            totalConsolidadoViewModel = ViewModelProviders.of(this).get(TotalConsolidadoViewModel.class);
            eanPluConsolidadoVieModel = ViewModelProviders.of(this).get(EanPluConsolidadoViewModel.class);
            epcVieModel = ViewModelProviders.of(this).get(EpcViewModel.class);
            WebServices.getProductsByConsolidatedInventory(inventarioConsolidado.getId(), this, admin, new ResultWebServiceInterface() {
                @Override
                public void ok(ResultWebServiceOk ok) {
                    GetProductsByConsolidatedInventoryResponse aux = (GetProductsByConsolidatedInventoryResponse) ok.getData();
                    //Busco la zona del inventory
                    for (ProductHasZone pz: aux.getProductosZonas()){
                        Zone zona = (Zone) db.findById(Constants.table_zones, pz.getZone().getId()+"", Zone.class);
                        if(zona!=null){
                            pz.setZone(zona);
                        }

                    }

                    //Actualizo la cantidad
                    totalConsolidadoViewModel.setInventario(aux.getConsolidatedInventories());
                    for (ProductHasZone pz: aux.getProductosZonas()){
                        Epc epc = (Epc) db.findById(
                                Constants.table_epcs,
                                pz.getEpc().getId()+"",
                                Epc.class
                        );
                        if(epc!=null)
                            pz.setEpc(epc);
                        epcVieModel.addProductoZona(pz);
                        eanPluConsolidadoVieModel.addProductoZona(pz);
                    }


                }

                @Override
                public void fail(ResultWebServiceFail fail) {
                    admin.toast(fail.getError());
                }
            });
        }

    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnSal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(VisualizarInventarioCooperativoPorZonaStep1.class, null);
            }
        });

        add_on_click(R.id.btnEnv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.toast(R.string.no_implementado);
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }


    //region Tab Total
    private ViewPager mViewPager;
    TotalViewModel totalViewModel;
    EanPluViewModel eanPluVieModel;
    EpcViewModel epcVieModel;

    TotalConsolidadoViewModel totalConsolidadoViewModel;
    EanPluConsolidadoViewModel eanPluConsolidadoVieModel;
    //endregion

    //region Tabs configuration
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public void tabsInit() {
//        /region Tabs section
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

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
                    if(inventario!=null){
                        TotalFragment total = new TotalFragment();
                        return total;
                    } else{
                        TotalConsolidadoFragment total = new TotalConsolidadoFragment();
                        return total;
                    }
                case 1:
                    if(inventario!=null){
                        EanPluFragment eanPlu = EanPluFragment.newInstance();
                        eanPlu.setAdmin(admin);
                        return eanPlu;
                    } else{
                        EanPluConsolidadoFragment eanPlu = EanPluConsolidadoFragment.newInstance();
                        eanPlu.setAdmin(admin);
                        return eanPlu;
                    }
                case 2:
                    EpcFragment epcFragment = EpcFragment.newInstance();
                    epcFragment.setAdmin(admin);
                    return epcFragment;
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
            return 3;
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


        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }
        if(item.getTitle()!= null){
            if(item.getTitle().equals(getString(R.string.log_out))){
                DataBase db = DataBase.getInstance(this);
                db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
