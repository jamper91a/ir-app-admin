package inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioTotal;

import android.arch.lifecycle.ViewModelProviders;
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

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.OnAcceptCancelListener;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Reports.HomeReportes;
import inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioTotal.tabs.InvTotTotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioTotal.tabs.InvTotTotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioTotal.tabs.InvTotalEanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioTotal.tabs.InvTotalEanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioTotal.tabs.InvTotalEpcFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioTotal.tabs.InvTotalEpcViewModel;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.UltimoInventarioResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CreatePdfTotalnventoryRequest;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.Util;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ReporteInventarioTotal extends CicloActivity {

    final DataBase db = DataBase.getInstance(this);
    private String TAG="ReporteInventarioTotal";
    private UltimoInventarioResponse inventarioConsolidado=null;
    private int tabSelected = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_tabs_container);
        this.tabsInit();
        //toolbar
        getSupportActionBar().setTitle(R.string.reporte_inventario_total);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.titleIcn),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.titleTxt),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSal),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnEnv),Techniques.FadeInLeft));

        getElemento(R.id.titleTxt).setText(getString(R.string.reporte_inventario_total));

    }

    @Override
    public void getData() {

        WebServices.getLastConsolidatedInventory(this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                inventarioConsolidado = (UltimoInventarioResponse) ok.getData();
                if(inventarioConsolidado!=null){
                    totalConsolidadoViewModel = ViewModelProviders.of(ReporteInventarioTotal.this).get(InvTotTotalViewModel.class);
                    eanPluConsolidadoVieModel = ViewModelProviders.of(ReporteInventarioTotal.this).get(InvTotalEanPluViewModel.class);
                    epcConsolidadoVieModel = ViewModelProviders.of(ReporteInventarioTotal.this).get(InvTotalEpcViewModel.class);
                    //Busco la zona del inventory
                    for(Inventory inv: inventarioConsolidado.getInventories()){
                        for (ProductHasZone pz: inv.getProducts()){
                            Zone zona = (Zone) db.findById(Constants.table_zones, pz.getZone().getId()+"", Zone.class);
                            if(zona!=null){
                                pz.setZone(zona);
                            }
                            Product producto = (Product) db.findById(Constants.table_products, pz.getProduct().getId()+"", Product.class);
                            if(producto!=null){
                                pz.setProduct(producto);
                            }
                            //Busco el epc
                            Epc epc = (Epc) db.findById(Constants.table_epcs, pz.getEpc().getId()+"", Epc.class);
                            if(epc!=null){
                                pz.setEpc(epc);
                            }
                            eanPluConsolidadoVieModel.addProductoZona(pz);
                            epcConsolidadoVieModel.addAllProductoZona(pz);
                        }
                    }


                    //Actualizo la cantidad
                    totalConsolidadoViewModel.setInventario(inventarioConsolidado);
                    eanPluConsolidadoVieModel.setInventario(inventarioConsolidado);
                    epcConsolidadoVieModel.setInventario(inventarioConsolidado);
                }
            }

            @Override
            public void fail(ResultWebServiceFail fail) {
                onBackPressed();
            }
        });



    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnSal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    admin.callIntent(HomeReportes.class, null);
            }
        });

        add_on_click(R.id.btnEnv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (tabSelected){

                    case 1:
                        admin.toast(R.string.no_implementado);
                        break;
                    case 2:
                        Util.context = ReporteInventarioTotal.this;
                        Util.askForEmail(new OnAcceptCancelListener() {
                            @Override
                            public void onAccept(Object item) {
                                CreatePdfTotalnventoryRequest request = new CreatePdfTotalnventoryRequest(ReporteInventarioTotal.this);
                                request.setTitle(getString(R.string.total_inventory));
                                LinkedList<ProductHasZone> products = eanPluConsolidadoVieModel.getStaticProducts();
                                request.addRows(products);
                                request.setTo((String) item);
                                WebServices.createPdf(ReporteInventarioTotal.this, request, admin, new ResultWebServiceInterface() {
                                    @Override
                                    public void ok(ResultWebServiceOk ok) {
//                                        String url = (String)ok.getData();
//                                        Intent i = new Intent(Intent.ACTION_VIEW);
//                                        i.setData(Uri.parse(Constants.url+url));
//                                        startActivity(i);
                                        admin.toast(getString(R.string.email_sent));
                                    }

                                    @Override
                                    public void fail(ResultWebServiceFail fail) {

                                    }
                                });
                            }

                            @Override
                            public void onCancel(Object item) {

                            }
                        });

                        break;
                    case 3:
                        admin.toast(R.string.no_implementado);
                        break;
                }

            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }


    //region Tab Total
    private ViewPager mViewPager;



    InvTotTotalViewModel totalConsolidadoViewModel;
    InvTotalEanPluViewModel eanPluConsolidadoVieModel;
    InvTotalEpcViewModel epcConsolidadoVieModel;
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
            tabSelected = position;
            switch (position) {
                case 0:
                        InvTotTotalFragment total = new InvTotTotalFragment();
                        return total;

                case 1:
                        InvTotalEanPluFragment eanPlu = InvTotalEanPluFragment.newInstance();
                        eanPlu.setAdmin(admin);
                        return eanPlu;

                case 2:
                        InvTotalEpcFragment epcFragment = InvTotalEpcFragment.newInstance();
                        epcFragment.setAdmin(admin);
                        return epcFragment;
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
        admin.callIntent(HomeReportes.class, null);
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
