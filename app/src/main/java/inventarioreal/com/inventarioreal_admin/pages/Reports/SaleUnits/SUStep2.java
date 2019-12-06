package inventarioreal.com.inventarioreal_admin.pages.Reports.SaleUnits;

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
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos.DIFStep1;
import inventarioreal.com.inventarioreal_admin.pages.Reports.HomeReportes;
import inventarioreal.com.inventarioreal_admin.pages.Reports.SaleUnits.tabs.SUEanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reports.SaleUnits.tabs.SUEanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Reports.SaleUnits.tabs.SUTotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reports.SaleUnits.tabs.SUTotalViewModel;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.SaleUnitsResponse;
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

public class SUStep2 extends CicloActivity {

    final DataBase db = DataBase.getInstance(this);
    private ArrayList<ProductHasZone> productosZona = new ArrayList<>();
    private RequestSUStep2 request=null;
    Report reportToSave = new Report();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.request = gson.fromJson(message, RequestSUStep2.class);
        init(this,this, R.layout.activity_inventario_parcial_visualizar_por_zona_step_2);
        this.tabsInit();
        getSupportActionBar().setTitle(R.string.reporte_unidades_vendidas);
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

        WebServices.saleUnits(
                request.getFirstDateToString(),
                request.getSecondDateToString(),
                this,
                admin,
                new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                SaleUnitsResponse response = (SaleUnitsResponse) ok.getData();
                productosZona.addAll(response.getArraySaleUnits());
                productosZona.addAll(response.getArrayReturnedUnits());
                if(productosZona!=null){
                    totalViewModel = ViewModelProviders.of(SUStep2.this).get(SUTotalViewModel.class);
                    eanPluViewModel = ViewModelProviders.of(SUStep2.this).get(SUEanPluViewModel.class);
                    epcViewModel = ViewModelProviders.of(SUStep2.this).get(EpcViewModel.class);
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
                        eanPluViewModel.addProductoZona(pz);
                        epcViewModel.addProductoZona(pz);

                    }


                    //Actualizo la cantidad
                    totalViewModel.setAmountSelled(response.getArraySaleUnits().size());
                    reportToSave.setUnitsSell(response.getArraySaleUnits().size());
                    totalViewModel.setAmountReturned(response.getArrayReturnedUnits().size());
                    reportToSave.setUnitsReturned(response.getArrayReturnedUnits().size());
                    totalViewModel.setDate(admin.getCurrentDateAndTime());


                }else{
                    onBackPressed();
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

                reportToSave.setFirstDate(request.getFirstDateToString());
                reportToSave.setSecondDate(request.getSecondDateToString());
                reportToSave.setType(Constants.inventory_sell_units);
                reportToSave.setAmount(productosZona.size());
                ProductHasZone[] products = productosZona.toArray(new ProductHasZone[productosZona.size()]);

                WebServices.saveReport(reportToSave, products, SUStep2.this, admin, new ResultWebServiceInterface() {
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



    SUTotalViewModel totalViewModel;
    SUEanPluViewModel eanPluViewModel;
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
                    SUTotalFragment total = SUTotalFragment.newInstance();
                    return total;

                case 1:
                    SUEanPluFragment eanPlu = SUEanPluFragment.newInstance();
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
        admin.callIntent(SUStep1.class, null);
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
