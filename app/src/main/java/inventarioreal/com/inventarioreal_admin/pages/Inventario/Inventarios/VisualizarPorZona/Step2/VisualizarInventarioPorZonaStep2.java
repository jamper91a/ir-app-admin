package inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.VisualizarPorZona.Step2;

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
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Intents.RequestInventarioPorZonaStep2;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.VisualizarConsolidados.Step1.VisualizarInventariosConsolidadosStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.VisualizarPorZona.Step1.VisualizarInventarioPorZonaStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventario.EanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventario.EanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventario.TotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventario.TotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventariosConsolidados.EanPluConsolidadoFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventariosConsolidados.EanPluConsolidadoViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventariosConsolidados.TotalConsolidadoFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventariosConsolidados.TotalConsolidadoViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.GetProductosInventariosConsolidados;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epcs;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosConsolidados;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosProductos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class VisualizarInventarioPorZonaStep2 extends CicloActivity {

    final DataBase db = DataBase.getInstance(this);
    private String TAG="VisualizarInventarioColaborativoPorZonaStep2";
    private LinkedList<InventariosProductos> inventariosProductos = new LinkedList<>();
    private Gson gson = new Gson();
    private RequestInventarioPorZonaStep2 requestInventarioPorZonaStep2=null;
    private Inventarios inventario=null;
    private InventariosConsolidados inventarioConsolidado=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //region Obtener parametros
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        try {
            this.requestInventarioPorZonaStep2 = gson.fromJson(message, RequestInventarioPorZonaStep2.class);
            this.inventario = requestInventarioPorZonaStep2.getInventarios();
            this.inventarioConsolidado = requestInventarioPorZonaStep2.inventariosConsolidados;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        init(this,this,R.layout.activity_inventario_parcial_visualizar_por_zona_step_2);
        this.tabsInit();
        //endregion
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
            WebServices.getProductsByInventario(inventario.getId(), this, admin, new ResultWebServiceInterface() {
                @Override
                public void ok(ResultWebServiceOk ok) {
                    inventario = (Inventarios) ok.getData();
                    //Busco la zona del inventario
                    Zonas zona = (Zonas) db.findById(Constants.table_zonas, inventario.getZonas_id().getId()+"", Zonas.class);
                    if(zona!=null){
                        inventario.setZonas_id(zona);
                    }
                    //Actualizo la cantidad
                    totalViewModel.stInventario(inventario);
                    for (ProductosZonas pz: inventario.getProductos_zona()
                    ) {
                        //Busco el epc del producto
                        Epcs epc = (Epcs) db.findById(
                                Constants.table_epcs,
                                pz.getEpcs_id().getId()+"",
                                Epcs.class
                        );
                        if(epc!=null)
                            pz.setEpcs_id(epc);
                        eanPluVieModel.addProductoZona(pz);
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
            WebServices.getProductsByInventariConsolidado(inventarioConsolidado.getId(), this, admin, new ResultWebServiceInterface() {
                @Override
                public void ok(ResultWebServiceOk ok) {
                    GetProductosInventariosConsolidados aux = (GetProductosInventariosConsolidados) ok.getData();
                    //Busco la zona del inventario
                    for (ProductosZonas pz: aux.getProductosZonas()){
                        Zonas zona = (Zonas) db.findById(Constants.table_zonas, pz.getZonas_id().getId()+"", Zonas.class);
                        if(zona!=null){
                            pz.setZonas_id(zona);
                        }
                    }

                    //Actualizo la cantidad
                    totalConsolidadoViewModel.stInventario(aux.getInventariosConsolidados());
                    for (ProductosZonas pz: aux.getProductosZonas()){
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
                if(inventario!=null)
                    admin.callIntent(VisualizarInventarioPorZonaStep1.class, null);
                else
                    admin.callIntent(VisualizarInventariosConsolidadosStep1.class, null);
            }
        });

        add_on_click(R.id.btnEnv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.toast("No implemented yet");
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

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

        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Tab Total
    private ViewPager mViewPager;
    TotalViewModel totalViewModel;
    EanPluViewModel eanPluVieModel;



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
