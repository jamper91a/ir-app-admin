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
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.OnAcceptCancelListener;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransDetailsEanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransDetailsEanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransDetailsEpcFragment;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransDetailsTotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransDetailsTotalViewModel;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.TransferenciaDetails;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CreatePdfElectronicManifestRequest;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.Util;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ManifiestoElectronicoDetalles extends CicloActivity {

    private TransferenciaDetails data;
    private CreatePdfElectronicManifestRequest request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String aux = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.data = gson.fromJson(aux, TransferenciaDetails.class);

        init(this,this, R.layout.activity_tabs_container);
        //region Obtener parametros


        //endregion
        this.tabsInit();
        //toolbar
        getSupportActionBar().setTitle(R.string.detalles_manifiesto_electronico);
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

        getElemento(R.id.titleTxt).setText(this.data.getType());
        ImageView img = (ImageView) getElemento(R.id.titleIcn).getElemento();
        img.setImageDrawable(getDrawable(R.drawable.icn_manifiesto_blue_dark));
    }

    @Override
    public void getData() {
        request = new CreatePdfElectronicManifestRequest(this);
        totalViewModel = ViewModelProviders.of(this).get(TransDetailsTotalViewModel.class);
        eanPluVieModel = ViewModelProviders.of(this).get(TransDetailsEanPluViewModel.class);
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
        add_on_click(R.id.btnEnv, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.context = ManifiestoElectronicoDetalles.this;
                Util.askForEmail(new OnAcceptCancelListener() {
                    @Override
                    public void onAccept(Object item) {
                        Gson gson = new Gson();
                        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
                        request.setTitle(data.getType());
                        request.addHeaders(
                                getString(R.string.enviados),
                                getString(R.string.recibidos),
                                getString(R.string.eanPlu),
                                getString(R.string.description)
                        );
                        request.addRows(data.getProductos());
                        request.setAmountSent(data.getEnviados());
                        request.setAmountReceived(data.getRecibidos());
                        request.setAmountMissing(data.getFaltantes());
                        request.setDestination(data.getDestino().getName());
                        request.setSource(empleado.getEmployee().getShop().getName());
                        request.setTo((String) item);

                        WebServices.createPdf(ManifiestoElectronicoDetalles.this, request, admin, new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
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


            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }


    //region Tab Total
    private ViewPager mViewPager;
    TransDetailsTotalViewModel totalViewModel;
    TransDetailsEanPluViewModel eanPluVieModel;
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
                    TransDetailsTotalFragment total = new TransDetailsTotalFragment();
                    return total;
                case 1:
                    TransDetailsEanPluFragment eanPlu = TransDetailsEanPluFragment.newInstance();
                    eanPlu.setAdmin(admin);
                    return eanPlu;
                case 2:
                    TransDetailsEpcFragment epcFragment = TransDetailsEpcFragment.newInstance();
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
                DataBase db = DataBase.getInstance(this);
                db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
