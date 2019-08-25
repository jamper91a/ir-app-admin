package inventarioreal.com.inventarioreal_admin.pages.Cashier.Sell;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.RFDIListener;
import inventarioreal.com.inventarioreal_admin.pages.Cashier.HomeCashier;
import inventarioreal.com.inventarioreal_admin.pages.Cashier.tabs.SellEanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Cashier.tabs.SellEanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Cashier.tabs.SellTotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Cashier.tabs.SellTotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Transferencias.HomeTransferencia;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Sell;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.RFDIReader;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class SellCommodity extends CicloActivity {

    private String TAG="SellCommodity";
    private DataBase db = DataBase.getInstance(this);
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
            }
        }
    } ;

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
        });
        rfdiReader.initSDK();
        init(this,this,R.layout.get_product_by_epc);
        shop = ((LoginResponse) gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class)).getEmployee().getShop();
        this.tabsInit();
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.lnl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnCan),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnLee),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnFin),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBor),Techniques.FadeInLeft));
    }

    @Override
    public void getData() {
        epcs = new ArrayList<>();
        totalViewModel = ViewModelProviders.of(this).get(SellTotalViewModel.class);
        eanPluVieModel = ViewModelProviders.of(this).get(SellEanPluViewModel.class);
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
                        admin.toast("Hay productos que no se pueden sacar");
                    }
                }
                if(pass)
                    showDialog();
            }
        });

        add_on_click(R.id.btnLee, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rfdiReader.isStartReader()==false)
                {
                    rfdiReader.startReader();
                    getElemento(R.id.btnLee).setText("Detener");
                }else{
                    rfdiReader.stopReader();
                    getElemento(R.id.btnLee).setText("Leer");
                }
            }
        });

        add_on_click(R.id.btnCan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(HomeTransferencia.class, null);
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        rfdiReader.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rfdiReader.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        rfdiReader.onDestroy();
    }

    private void createEpc(String epc){
        //Busco el epc en la base de datos interna
        Epc epcDb= (Epc) db.findOneByColumn(Constants.table_epcs, Constants.column_epc, "'"+epc+"'", Epc.class);
        if(epcDb!=null){
            try {
                //Busco el producto zonas al que pertenece este tag
                ProductHasZone proZon=
                        (ProductHasZone) db.findOneByColumn(
                                Constants.table_productsHasZones,
                                Constants.column_epc_id,
                                epcDb.getId()+"",
                                ProductHasZone.class);
                //Busco el producto de este producto zona
                Product producto= (Product) db.findById(
                        Constants.table_products,
                        proZon.getProduct().getId()+"",
                        Product.class
                        );

                //Busco la zona de este producto
                Zone zone = (Zone) db.findById(Constants.table_zones, proZon.getZone().getId()+"", Zone.class);

                if (epcDb!=null) {
                    proZon.setEpc(epcDb);
                }
                if(producto!=null){
                    proZon.setProduct(producto);
                }
                if(zone!=null)
                {
                    proZon.setZone(zone);
                }

                //Determino si la zona del producto pertenece al shop del usuario
                if(proZon.getZone().getShop().getId()!= shop.getId()){
                    proZon.setError(true);
                }
                //Check if product was sell before
                if(proZon.getSell().getId()>1){
                    proZon.setError(true);
                }
                products.add(proZon);
                eanPluVieModel.addProductoZonaHasTransferencia(proZon);

                //Informacion requeria por el servicio web de crear inventory
                totalViewModel.setAmount(products.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            epcs.add(epc);
        }
    }


    private List<String> epcs;



    private void addToList(final String epc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // The epc for the first time
                createEpc(epc);
//                if(epcs.isEmpty())
//                    createEpc(epc);
//                else{
//                    if(!wasRead(epc))
//                        createEpc(epc);
//                }
            }
        });
    }


    //region Menu

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(getString(R.string.log_out));
//        getMenuInflater().inflate(menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG,item.getTitle().toString());
        if(item.getTitle().equals(getString(R.string.log_out))){
            admin.log_out(Login.class);
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_favorite) {
//            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Tab Total
    private ViewPager mViewPager;
    SellTotalViewModel totalViewModel;
    SellEanPluViewModel eanPluVieModel;
    //endregion

    //region Tabs configuration
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public void tabsInit() {
        mSectionsPagerAdapter = new SellCommodity.SectionsPagerAdapter(getSupportFragmentManager());

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
                    SellTotalFragment total = new SellTotalFragment();
                    return total;
                case 1:
                    SellEanPluFragment eanPlu = SellEanPluFragment.newInstance();
                    eanPlu.setAdmin(admin);
                    return eanPlu;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
    //endregion

    private void showDialog(){
        rfdiReader.stopReader();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sacar Productos");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_crear_inventario, null);
        final TextView txtLocal = dialogView.findViewById(R.id.txtLocal);
        final TextView txtTime = dialogView.findViewById(R.id.txtTime);
        final EditText edtMensaje = dialogView.findViewById(R.id.edtMensaje);


        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        txtLocal.setText("Local : "+empleado.getEmployee().getShop().getName());
        builder.setView(dialogView);

        final Sell newSell = new Sell();
        newSell.setUser(empleado.getEmployee().getUser());

// Set up the buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebServices.createSell(
                        newSell,
                        products,
                        SellCommodity.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                admin.toast("Salida de mercancia realizada con 'exito");
                                ProductHasZone[] newProducts = (ProductHasZone[]) ok.getData();
                                //Update local information
                                for(ProductHasZone product: newProducts){
                                    try {
                                        db.update(
                                                Constants.table_productsHasZones,
                                                product.getId()+"",
                                                product.getContentValues()
                                                );
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                admin.callIntent(HomeCashier.class, null);
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast("fail");
                            }
                        }

                );
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
