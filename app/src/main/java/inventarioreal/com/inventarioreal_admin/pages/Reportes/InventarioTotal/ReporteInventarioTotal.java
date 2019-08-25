package inventarioreal.com.inventarioreal_admin.pages.Reportes.InventarioTotal;

import android.arch.lifecycle.ViewModelProviders;
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

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Reportes.HomeReportes;
import inventarioreal.com.inventarioreal_admin.pages.Reportes.InventarioTotal.tabs.InvTotTotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reportes.InventarioTotal.tabs.InvTotTotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Reportes.InventarioTotal.tabs.InvTotalEanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Reportes.InventarioTotal.tabs.InvTotalEanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.UltimoInventarioResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
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

public class ReporteInventarioTotal extends CicloActivity {

    final DataBase db = DataBase.getInstance(this);
    private String TAG="ReporteInventarioTotal";
    private UltimoInventarioResponse inventarioConsolidado=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        WebServices.getLastConsolidatedInventory(this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                inventarioConsolidado = (UltimoInventarioResponse) ok.getData();
                if(inventarioConsolidado!=null){
                    totalConsolidadoViewModel = ViewModelProviders.of(ReporteInventarioTotal.this).get(InvTotTotalViewModel.class);
                    eanPluConsolidadoVieModel = ViewModelProviders.of(ReporteInventarioTotal.this).get(InvTotalEanPluViewModel.class);
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
                            eanPluConsolidadoVieModel.addProductoZona(pz);
                        }
                    }


                    //Actualizo la cantidad
                    totalConsolidadoViewModel.setInventario(inventarioConsolidado);
//                    for(Inventory inv: inventarioConsolidado.getInventories()){
//                        for (ProductHasZone pz: inv.getProducts()){
//                            eanPluConsolidadoVieModel.addProductoZona(pz);
//                        }
//                    }
                }
            }

            @Override
            public void fail(ResultWebServiceFail fail) {

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



    InvTotTotalViewModel totalConsolidadoViewModel;
    InvTotalEanPluViewModel eanPluConsolidadoVieModel;
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
                        InvTotTotalFragment total = new InvTotTotalFragment();
                        return total;

                case 1:
                        InvTotalEanPluFragment eanPlu = InvTotalEanPluFragment.newInstance();
                        eanPlu.setAdmin(admin);
                        return eanPlu;
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
