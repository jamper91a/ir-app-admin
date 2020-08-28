package inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EpcFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EpcViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos.tabs.RepDifInvEanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos.tabs.RepDifInvEanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos.tabs.RepDifInvTotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos.tabs.RepDifInvTotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Reports.HomeReportes;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Report;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class DIFStep2 extends CicloActivity {

    final DataBase db = DataBase.getInstance(this);
    private String TAG="ReporteInventarioTotal";
    private ArrayList<ProductHasZone> productosZona = new ArrayList<>();
    private RequestDIFStep2 request=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.request = gson.fromJson(message, RequestDIFStep2.class);
        init(this,this, R.layout.activity_tabs_container);
        this.tabsInit();
        getSupportActionBar().setTitle(R.string.diferencias_inventarios_fisicos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //endregion
    }

    @Override
    public void initGui() {

        addElemento(new Animacion(findViewById(R.id.lnl2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSal),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnGua),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnEnv),Techniques.FadeInLeft));

    }

    @Override
    public void getData() {

        WebServices.getDiferenceBetweenInventories(
                request.getInventarioInicial().getId(),
                request.getInventarioFinal().getId(),
                this,
                admin,
                new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                productosZona = (ArrayList<ProductHasZone>) ok.getData();
                if(productosZona!=null){
                    totalConsolidadoViewModel = ViewModelProviders.of(DIFStep2.this).get(RepDifInvTotalViewModel.class);
                    eanPluConsolidadoVieModel = ViewModelProviders.of(DIFStep2.this).get(RepDifInvEanPluViewModel.class);
                    epcViewModel = ViewModelProviders.of(DIFStep2.this).get(EpcViewModel.class);
                    //Busco la zona del inventory
                    for(ProductHasZone pz: productosZona){
                        Zone zona = (Zone) db.findById(Constants.table_zones, pz.getZone().getId()+"", Zone.class);
                        if(zona!=null){
                            pz.setZone(zona);
                        }
                        Product producto = (Product) db.findById(Constants.table_products, pz.getProduct().getId()+"", Product.class);
                        if(producto!=null){
                            pz.setProduct(producto);
                        }
                        Epc epc = (Epc) db.findById(Constants.table_epcs, pz.getEpc().getId()+"", Epc.class);
                        if(epc!=null){
                            pz.setEpc(epc);
                        }
                        eanPluConsolidadoVieModel.addProductoZona(pz);
                        epcViewModel.addProductoZona(pz);

                    }


                    //Actualizo la cantidad
                    totalConsolidadoViewModel.setAmount(productosZona.size());
                    totalConsolidadoViewModel.setDate(admin.getCurrentDateAndTime());


                }
            }

            @Override
            public void fail(ResultWebServiceFail fail) {

            }
        }
        );



    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnSal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(HomeReportes.class, null);
            }
        });

        add_on_click(R.id.btnGua, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creo el reporte
                Report report = new Report();
                report.setFirstInventory(request.getInventarioInicial());
                report.setSecondInventory(request.getInventarioFinal());
                report.setType(Constants.inventory_diference_between_inventories);
                report.setType(Constants.inventory_diference_between_inventories);
                report.setAmount(productosZona.size());
                report.setUnitsReturned(0);
                report.setUnitsSell(0);
                ProductHasZone[] products = productosZona.toArray(new ProductHasZone[productosZona.size()]);

                WebServices.saveReport(report, products, DIFStep2.this, admin, new ResultWebServiceInterface() {
                    @Override
                    public void ok(ResultWebServiceOk ok) {
                        admin.toast(R.string.reporte_creado_exito);
                        admin.callIntent(HomeReportes.class, null);
                    }

                    @Override
                    public void fail(ResultWebServiceFail fail) {
                        admin.toast(R.string.reporte_creado_error);
                    }
                });
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



    RepDifInvTotalViewModel totalConsolidadoViewModel;
    RepDifInvEanPluViewModel eanPluConsolidadoVieModel;
    EpcViewModel epcViewModel;
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
                    RepDifInvTotalFragment total = new RepDifInvTotalFragment();
                    return total;

                case 1:
                    RepDifInvEanPluFragment eanPlu = RepDifInvEanPluFragment.newInstance();
                    eanPlu.setAdmin(admin);
                    return eanPlu;

                case 2:
                    EpcFragment epcF = EpcFragment.newInstance();
                    epcF.setAdmin(admin);
                    return epcF;
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


    @Override
    public void onBackPressed() {
        admin.callIntent(DIFStep1.class, null);
    }

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
