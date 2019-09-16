package inventarioreal.com.inventarioreal_admin.pages.Transfers.ManifiestoElectronico;

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

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransferenciaDetailsEanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransferenciaDetailsEanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransferenciaDetailsEpcFragment;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransferenciaDetailsTotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransferenciaDetailsTotalViewModel;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.TransferenciaDetails;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ManifiestoElectronicoDetalles extends CicloActivity {

    private TransferenciaDetails data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String aux = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.data = gson.fromJson(aux, TransferenciaDetails.class);

        init(this,this, R.layout.activity_manifiesto_electronico_detalles);
        //region Obtener parametros


        //endregion
        this.tabsInit();
        //toolbar
        getSupportActionBar().setTitle("Detalles manifiesto electronico");
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
        totalViewModel = ViewModelProviders.of(this).get(TransferenciaDetailsTotalViewModel.class);
        eanPluVieModel = ViewModelProviders.of(this).get(TransferenciaDetailsEanPluViewModel.class);
        totalViewModel.setTransferencia(this.data);
        eanPluVieModel.setTransferencia(this.data);

    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnSal, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin.callIntent(ManifiestoElectronicoHome.class, null);
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }


    //region Tab Total
    private ViewPager mViewPager;
    TransferenciaDetailsTotalViewModel totalViewModel;
    TransferenciaDetailsEanPluViewModel eanPluVieModel;
    //endregion

    //region Tabs configuration
    private ManifiestoElectronicoDetalles.SectionsPagerAdapter mSectionsPagerAdapter;
    public void tabsInit() {
//        /region Tabs section
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new ManifiestoElectronicoDetalles.SectionsPagerAdapter(getSupportFragmentManager());

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
                    TransferenciaDetailsTotalFragment total = new TransferenciaDetailsTotalFragment ();
                    return total;
                case 1:
                    TransferenciaDetailsEanPluFragment eanPlu = TransferenciaDetailsEanPluFragment.newInstance();
                    eanPlu.setAdmin(admin);
                    return eanPlu;
                case 2:
                    TransferenciaDetailsEpcFragment epcFragment = TransferenciaDetailsEpcFragment.newInstance();
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
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
