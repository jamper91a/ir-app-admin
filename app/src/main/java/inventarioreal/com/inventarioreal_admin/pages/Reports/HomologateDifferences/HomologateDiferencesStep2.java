package inventarioreal.com.inventarioreal_admin.pages.Reports.HomologateDifferences;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import java.util.LinkedList;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.RFDIListener;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EpcFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EpcViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos.DIFStep1;
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos.tabs.RepDifInvEanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos.tabs.RepDifInvEanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos.tabs.RepDifInvTotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos.tabs.RepDifInvTotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Reports.HomeReportes;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.GetReportByIdResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Report;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ReportsHasProductsZone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.RFDIReader;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class HomologateDiferencesStep2 extends CicloActivity {

    private ArrayList<ProductHasZone> productosZona = new ArrayList<>();
    private LinkedList<ReportsHasProductsZone> newData = new LinkedList<>();
    private Report report=null;
    private List<String> epcs;
    private DataBase db = DataBase.getInstance(this);

    RFDIReader rfdiReader =  null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String epc;
            switch (msg.what){
                case 1:
                    epc = msg.getData().getString("epc");
                    addToList(epc);
                    break ;
                case 3:
                    boolean state = msg.getData().getBoolean("state");
                    changedStateLecture(state);
                    break ;
            }
        }
    } ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.report = gson.fromJson(message, Report.class);
        init(this,this, R.layout.activity_crear_inventario_colaborativo_step2);
        this.tabsInit();
        getSupportActionBar().setTitle("Homologacion diferencia inventario");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //region UhF
        rfdiReader = new RFDIReader(new RFDIListener() {
            @Override
            public void onEpcAdded(String epc) {
                Message msg = new Message();
                msg.what = 1;
                Bundle b = new Bundle();
                b.putString("epc", epc);
                msg.setData(b);
                handler.sendMessage(msg);
            }

            @Override
            public void onEpcRepeated(String epc) {
                Message msg = new Message();
                msg.what = 2;
                Bundle b = new Bundle();
                b.putString("epc", "onEpcRepeated:"+epc);
                msg.setData(b);
                handler.sendMessage(msg);
            }

            @Override
            public void onStateChanged(boolean state) {
                Message msg = new Message();
                msg.what = 3;
                Bundle b = new Bundle();
                b.putBoolean("state", state);
                msg.setData(b);
                handler.sendMessage(msg);
            }

            @Override
            public void onKeyPresses(String key) {

            }
        }, this);
        rfdiReader.initSDK();
//        rfdiReader.startReader();
        //endregion
    }

    @Override
    public void initGui() {

        addElemento(new Animacion(findViewById(R.id.lnl2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnCan),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnLee),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnFin),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBor),Techniques.FadeInLeft));

    }

    @Override
    public void getData() {
        epcs = new ArrayList<>();
        WebServices.getReportById(
                report.getId(),
                this,
                admin,
                new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                GetReportByIdResponse response = (GetReportByIdResponse) ok.getData();
                report = response.getReport();
//                productosZona = (ArrayList<ProductHasZone>) ok.getData();
                if(response.getProductsHasZones()!=null){
                    productosZona = response.getProductsHasZonesAsArray();
                    totalConsolidadoViewModel = ViewModelProviders.of(HomologateDiferencesStep2.this).get(RepDifInvTotalViewModel.class);
                    eanPluConsolidadoVieModel = ViewModelProviders.of(HomologateDiferencesStep2.this).get(RepDifInvEanPluViewModel.class);
                    epcViewModel = ViewModelProviders.of(HomologateDiferencesStep2.this).get(EpcViewModel.class);
                    //Busco la zona del inventory
                    for(ProductHasZone pz: productosZona){
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
        add_on_click(R.id.btnLee, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedStateLecture(!rfdiReader.isStartReader());
            }
        });
        add_on_click(R.id.btnCan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedStateLecture(false);
                clean();
                onBackPressed();
            }
        });
        add_on_click(R.id.btnBor, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean();
            }
        });

        add_on_click(R.id.btnFin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newData.size()>0){
                    WebServices.homologateUnits(newData, HomologateDiferencesStep2.this, admin, new ResultWebServiceInterface() {
                        @Override
                        public void ok(ResultWebServiceOk ok) {
                            admin.toast("Reporte actualizado con exito creado con exito");
                        admin.callIntent(HomeReportes.class, null);
                        }

                        @Override
                        public void fail(ResultWebServiceFail fail) {
                            admin.toast("Error creando reporte");
                        }
                    });
                }else{
                    admin.toast("No ay informacion para enviar");
                }
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
                    RepDifInvTotalFragment total = RepDifInvTotalFragment.newInstance();
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
        admin.callIntent(HomologateDiferencesStep1.class, null);
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

    //region Rfdi
    private void changedStateLecture(boolean state){
        if(state){
            rfdiReader.startReader();
            getElemento(R.id.btnLee).setText("Detener");
        }else{
            rfdiReader.stopReader();
            getElemento(R.id.btnLee).setText("Leer");
        }
    }

    private void clean(){
        rfdiReader.cleanEpcs();
        productosZona.clear();
        epcs.clear();
        eanPluConsolidadoVieModel.clean();
        epcViewModel.clean();
        totalConsolidadoViewModel.setAmount(0);
    }

    @Override
    protected void onResume() {
        rfdiReader.onResume();
        super.onResume();

    }

    @Override
    protected void onPause() {
        rfdiReader.onPause();
        super.onPause();

    }
    @Override
    protected void onDestroy() {
        rfdiReader.onDestroy();
        super.onDestroy();
    }

    private void addToList(final String epc) {
        if(epcs.isEmpty())
            removeEpc(epc);
        else{
            if(!wasRead(epc))
                removeEpc(epc);
        }
    }
    private void removeEpc(String epc){
        //Busco el epc en la base de datos interna
        Epc epcDb= (Epc) db.findOneByColumn(Constants.table_epcs, Constants.epc, "'"+epc+"'", Epc.class);
        if(epcDb!=null){
            //Find the epc in the list of products, if exists I will remove it
            for (ProductHasZone product: productosZona){
                if(product.getEpc().getId() == epcDb.getId()){
                    productosZona.remove(product);
                    eanPluConsolidadoVieModel.removeProductoZona(product);
                    totalConsolidadoViewModel.setAmount(productosZona.size());
                    epcViewModel.removeProductoZona(product);
                    epcs.add(epc);
                    //Agrego a la lista el id en la transferencia
                    for (ReportsHasProductsZone reportsHasProductsZone :
                            report.getProducts()) {
                        if(reportsHasProductsZone.getProduct().getId() == product.getId()){
                            newData.add(reportsHasProductsZone);
                        }

                    }

                }
            }

        }
    }

    private boolean wasRead(String epc){
        for (String epcR:epcs){
            if(epcR.equals(epc))
                return true;
        }

        return false;
    }

    //endregion
}
