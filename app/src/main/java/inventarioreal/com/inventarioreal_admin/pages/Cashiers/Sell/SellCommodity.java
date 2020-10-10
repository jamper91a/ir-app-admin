package inventarioreal.com.inventarioreal_admin.pages.Cashiers.Sell;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.RFDIListener;
import inventarioreal.com.inventarioreal_admin.pages.Cashiers.HomeCashier;
import inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs.SellEanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs.SellEanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs.SellEpcFragment;
import inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs.SellEpcViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs.SellTotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs.SellTotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Sell;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.RFDIReader;
import inventarioreal.com.inventarioreal_admin.util.SocketHelper;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Administrador;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class SellCommodity extends CicloActivity {

    private String TAG="SellCommodity";
//    private DataBase db = DataBase.getInstance(this);
    private Gson gson = new Gson();
    private LinkedList<ProductHasZone> products = new LinkedList<>();
    private Shop shop = null;

    RFDIReader rfdiReader =  null;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String epc = msg.getData().getString("epc");
                    addToList(epc);
                    break ;
                case 3:
                    boolean state = msg.getData().getBoolean("state");
                    changedStateLecture(state);
                    break ;
            }
        }
    } ;

    private SocketHelper socketHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        init(this,this,R.layout.get_product_by_epc);
        shop = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class).getEmployee().getShop();
        this.tabsInit();
        // toolbar
        getSupportActionBar().setTitle(getString(R.string.salida_mercancia));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Create socket Connection
        socketHelper = new SocketHelper(admin);
        socketHelper.connect();
        socketHelper.subs();

    }


    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.titleTxt),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.titleIcn),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnCan),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnLee),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnFin),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBor),Techniques.FadeInLeft));

        getElemento(R.id.titleTxt).setText(getString(R.string.vender));
        ImageView titleIcn = (ImageView)getElemento(R.id.titleIcn).getElemento();
        titleIcn.setImageDrawable(getDrawable(R.drawable.icn_commodity_blue));
    }

    @Override
    public void getData() {
        epcs = new ArrayList<>();
        String date = admin.getCurrentDateAndTime();

        totalViewModel = ViewModelProviders.of(this).get(SellTotalViewModel.class);
        eanPluViewModel = ViewModelProviders.of(this).get(SellEanPluViewModel.class);
        epcViewModel = ViewModelProviders.of(this).get(SellEpcViewModel.class);

        totalViewModel.setDate(admin.getCurrentDateAndTime());
        eanPluViewModel.setDate(admin.getCurrentDateAndTime());
        epcViewModel.setDate(admin.getCurrentDateAndTime());


    }


    @Override
    public void initOnClick() {
        add_on_click(R.id.btnFin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check there is not error
                boolean pass=true;
                for(ProductHasZone product: products){
                    if(product.isError()){
                        pass=false;
                        admin.toast(getString(R.string.error_productos_no_vender));
                    }
                }
                if(pass)
                    showDialog();
            }
        });

        add_on_click(R.id.btnLee, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedStateLecture(!rfdiReader.isStartReader());
            }
        });

        add_on_click(R.id.btnCan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Administrador.callIntent(HomeCashier.class, null);
            }
        });

        add_on_click(R.id.btnBor, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean();
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

    private void createEpc(final String epc){

        socketHelper.findProductZoneByEpcCode(epc, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                final ProductHasZone proZon = (ProductHasZone) ok.getData();

                if(proZon!=null){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Determino si la zona del producto pertenece al shop del usuario
                            if(proZon.getZone().getShop().getId()!= shop.getId()){
                                proZon.setError(true);
                            }
                            //Check if product was sell before
                            if(proZon.getSell()!= null && proZon.getSell().getId()>1){
                                proZon.setError(true);
                            }
                            products.add(proZon);
                            eanPluViewModel.addProductoZonaHasTransferencia(proZon);
                            epcViewModel.addAllProductoZona(proZon);

                            //Informacion requeria por el servicio web de crear inventory
                            totalViewModel.setAmount(products.size());
                        }
                    });

                }
                epcs.add(epc);

            }

            @Override
            public void fail(ResultWebServiceFail fail) {
                System.out.println("Fail");
            }
        });


        //Busco el epc en la base de datos interna
//        Epc epcDb= (Epc) db.findOneByColumn(Constants.table_epcs, Constants.column_epc, "'"+epc+"'", Epc.class);
//        if(epcDb!=null){
//            try {
//                //Busco el producto zonas al que pertenece este tag
//                ProductHasZone proZon=
//                        (ProductHasZone) db.findOneByColumn(
//                                Constants.table_productsHasZones,
//                                Constants.column_epc_id,
//                                epcDb.getId()+"",
//                                ProductHasZone.class);
//                //Busco el producto de este producto zona
//                Product producto= (Product) db.findById(
//                        Constants.table_products,
//                        proZon.getProduct().getId()+"",
//                        Product.class
//                        );
//
//                //Busco la zona de este producto
//                Zone zone = (Zone) db.findById(Constants.table_zones, proZon.getZone().getId()+"", Zone.class);
//
//                if (epcDb!=null) {
//                    proZon.setEpc(epcDb);
//                }
//                if(producto!=null){
//                    proZon.setProduct(producto);
//                }
//                if(zone!=null)
//                {
//                    proZon.setZone(zone);
//                }
//
//                //Determino si la zona del producto pertenece al shop del usuario
//                if(proZon.getZone().getShop().getId()!= shop.getId()){
//                    proZon.setError(true);
//                }
//                //Check if product was sell before
//                if(proZon.getSell().getId()>1){
//                    proZon.setError(true);
//                }
//                products.add(proZon);
//                eanPluViewModel.addProductoZonaHasTransferencia(proZon);
//                epcViewModel.addAllProductoZona(proZon);
//
//                //Informacion requeria por el servicio web de crear inventory
//                totalViewModel.setAmount(products.size());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            epcs.add(epc);
//        }
    }


    private List<String> epcs;

    private void addToList(final String epc) {
        createEpc(epc);
    }

    private void changedStateLecture(boolean state){
        if(state){
            rfdiReader.startReader();
            getElemento(R.id.btnLee).setText(getString(R.string.detener));
        }else{
            rfdiReader.stopReader();
            getElemento(R.id.btnLee).setText(getString(R.string.leer));
        }
    }


    //region Tab Total
    private ViewPager mViewPager;
    SellTotalViewModel totalViewModel;
    SellEanPluViewModel eanPluViewModel;
    SellEpcViewModel epcViewModel;
    //endregion

    //region Tabs configuration
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public void tabsInit() {
        mSectionsPagerAdapter = new SellCommodity.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

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
                    SellTotalFragment total = new SellTotalFragment();
                    return total;
                case 1:
                    SellEanPluFragment eanPlu = SellEanPluFragment.newInstance();
                    eanPlu.setAdmin(admin);
                    return eanPlu;
                case 2:
                    SellEpcFragment epcFragment= SellEpcFragment.newInstance();
                    epcFragment.setAdmin(admin);
                    return epcFragment;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
    //endregion

    private void showDialog(){
        rfdiReader.stopReader();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.sacar_producto));

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_crear_inventario, null);
        final TextView titleTxt =dialogView.findViewById(R.id.titleTxt);
        final ImageView titleIcn =dialogView.findViewById(R.id.titleIcn);
        final TextView txtLocal = dialogView.findViewById(R.id.txtLocal);
        final TextView txtNum = dialogView.findViewById(R.id.txtNum);
        final TextView txtFecha = dialogView.findViewById(R.id.txtFecha);
        final TextView txtTime = dialogView.findViewById(R.id.txtTime);
        final LinearLayout messageContainer = dialogView.findViewById(R.id.messageContainer);
        final Button btnGuardar = dialogView.findViewById(R.id.btnGuardar);
        final Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);

        String date = admin.getCurrentDateAndTime();

        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        txtLocal.setText(getString(R.string.local) +": "+empleado.getEmployee().getShop().getName());
        titleTxt.setText(R.string.sacar_producto);
        titleIcn.setImageDrawable(getDrawable(R.drawable.icn_commodity_blue));
        txtFecha.setText(date.split(" ")[0]);
        txtTime.setText(date.split(" ")[1]);
        txtNum.setVisibility(View.GONE);
        messageContainer.setVisibility(View.GONE);
        builder.setView(dialogView);

        final Sell newSell = new Sell();
        newSell.setUser(empleado.getEmployee().getUser());

        final AlertDialog show = builder.show();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebServices.createSell(
                        newSell,
                        products,
                        SellCommodity.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                admin.toast(getString(R.string.salida_mercancia_exitosa));
//                                ProductHasZone[] newProducts = (ProductHasZone[]) ok.getData();
//                                //Update local information
//                                for(ProductHasZone product: newProducts){
//                                    try {
//                                        db.update(
//                                                Constants.table_productsHasZones,
//                                                product.getId()+"",
//                                                product.getContentValues()
//                                                );
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
                                Administrador.callIntent(HomeCashier.class, null);
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast(getString(R.string.salida_mercancia_error));
                            }
                        }

                );
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });
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

    private void clean(){
        rfdiReader.cleanEpcs();
        epcs.clear();
        products.clear();
        eanPluViewModel.clean();
        epcViewModel.clean();
        totalViewModel.setAmount(0);
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
                //DataBase db = DataBase.getInstance(this);
                //db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
