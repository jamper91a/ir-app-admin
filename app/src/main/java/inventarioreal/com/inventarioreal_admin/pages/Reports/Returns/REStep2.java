package inventarioreal.com.inventarioreal_admin.pages.Reports.Returns;

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
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Reports.Returns.tabs.REEanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reports.Returns.tabs.REEanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Report;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Supplier;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class REStep2 extends CicloActivity {

    final DataBase db = DataBase.getInstance(this);
    private ArrayList<ProductHasZone> productosZona = new ArrayList<>();
    private RequestREStep2 request=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.request = gson.fromJson(message, RequestREStep2.class);
        init(this,this, R.layout.activity_report_rotation_step_2);
        this.tabsInit();
        if(this.request.getType() == 1)
            getSupportActionBar().setTitle("Reporte Devolucion Clientes");
        else
            getSupportActionBar().setTitle("Reporte Devolucion Proveedores");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //endregion
    }

    @Override
    public void initGui() {

        addElemento(new Animacion(findViewById(R.id.lnl2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnEnv),Techniques.FadeInLeft));

    }

    @Override
    public void getData() {

        WebServices.devolutionsByType(
                request.getType(),
                request.getFirstDateToString(),
                request.getSecondDateToString(),
                this,
                admin,
                new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                ArrayList<ProductHasZone> arrayUnits = (ArrayList<ProductHasZone>) ok.getData();
                productosZona.addAll(arrayUnits);
                if(productosZona!=null && productosZona.size()>0){
                    eanPluViewModel = ViewModelProviders.of(REStep2.this).get(REEanPluViewModel.class);
                    //Busco la zona del inventory
                    for(ProductHasZone pz: productosZona){
                        Zone zona = (Zone) db.findById(Constants.table_zones, pz.getZone().getId()+"", Zone.class);
                        if(zona!=null){
                            pz.setZone(zona);
                        }
                        Epc epc = (Epc) db.findById(Constants.table_epcs, pz.getEpc().getId()+"", Epc.class);
                        if(epc!=null){
                            pz.setEpc(epc);
                        }
                        eanPluViewModel.addProductoZona(pz);

                    }




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


    //region Tab Total
    private ViewPager mViewPager;



    REEanPluViewModel eanPluViewModel;
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
                    REEanPluFragment eanPlu = REEanPluFragment.newInstance();
                    eanPlu.setAdmin(admin);
                    return eanPlu;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
    //endregion


    @Override
    public void onBackPressed() {
        admin.callIntent(REStep1.class, this.request.getType(), Integer.class);
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
