package inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.Create.Step2;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.RFDIListener;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.Join.UnirseInventariosCooperativo;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.Intents.RequestCreateInventory2;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EpcFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EpcViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.TotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.TotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.Create.Step1.CrearInventarioCooperativoStep1;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventoryHasProduct;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.RFDIReader;
import inventarioreal.com.inventarioreal_admin.util.SocketHelper;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class CrearInventarioCooperativoStep2 extends CicloActivity {

    //private UhfManager uhfManager;
    private String TAG="CrearInventarioStep2";
//    private DataBase db = DataBase.getInstance(this);
    private RequestCreateInventory2 request;
    private LinkedList<InventoryHasProduct> products = new LinkedList<>();
    private Gson gson = new Gson();
    LoginResponse empleado;

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

    private SocketHelper socketHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //region Obtener parametros
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.request = gson.fromJson(message, RequestCreateInventory2.class);
        //endregion
        init(this,this,R.layout.activity_crear_inventario_colaborativo_step2);
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

        this.tabsInit();
        // toolbar
        if(!request.isUnion())
            getSupportActionBar().setTitle(R.string.crear_inventario_cooperativo);
        else
            getSupportActionBar().setTitle(R.string.unirse_inventario_cooperativo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Create socket Connection
        socketHelper = new SocketHelper(admin);
        socketHelper.connect();
        socketHelper.subs();
        
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
        empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        epcs = new ArrayList<>();
        totalViewModel = ViewModelProviders.of(this).get(TotalViewModel.class);
        eanPluVieModel = ViewModelProviders.of(this).get(EanPluViewModel.class);
        epcViewModel = ViewModelProviders.of(this).get(EpcViewModel.class);
        Inventory inventory = new Inventory();
        inventory.setZone(this.request.getZone());
        inventory.setDate(this.request.getDate());
        eanPluVieModel.setInventory(inventory);
        totalViewModel.setInventory(inventory);
        epcViewModel.setInventario(inventory);
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnFin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(products.size()>0)
                    showDialog();
                else
                    admin.toast(R.string.error_minimo_un_producto);
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
    }

    @Override
    public void hasAllPermissions() {
       // startFlag=true;

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
                            //Check if product was sell before
                            if(proZon.getSell()!= null && proZon.getSell().getId()==1){
                                //Valido que este producto pertenezca al local del usuario logeado
                                if(proZon.getZone()!=null && (proZon.getZone().getShop().getId() == empleado.getEmployee().getShop().getId())){
                                    //Informacion requeria por el servicio web de crear inventory
                                    InventoryHasProduct ip = new InventoryHasProduct();
                                    ip.setZone(request.getZone());
                                    ip.setProduct(proZon);
                                    ip.setEpc(proZon.getEpc());

                                    //Informacion requeria por el servicio web de crear inventory
                                    products.add(ip);
                                    eanPluVieModel.addProductoZona(proZon);
                                    totalViewModel.setAmount(products.size());
                                    epcViewModel.addProductoZona(proZon);
                                    epcs.add(epc);
                                }
                            }
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


//        //Busco el epc en la base de datos interna
//        Epc epcDb= (Epc) db.findOneByColumn(Constants.table_epcs, Constants.epc, "'"+epc+"'", Epc.class);
//        if(epcDb!=null){
//            //Busco el producto zonas al que pertenece este tag
//            ProductHasZone proZon=
//                    (ProductHasZone) db.findOneByColumn(
//                            Constants.table_productsHasZones,
//                            Constants.column_epc_id,
//                            epcDb.getId()+"",
//                            ProductHasZone.class);
//            if(proZon!=null){
//                //Determino si el producto no ha sido vendido
//                if(proZon.getSell().getId()==1){
//                    //Busco el producto de este producto zona
//                    Product producto= (Product) db.findById(
//                            Constants.table_products,
//                            proZon.getProduct().getId()+"",
//                            Product.class
//                    );
//                    //Busco la zona del producto zona
//                    Zone zona = (Zone) db.findById(
//                            Constants.table_zones,
//                            proZon.getZone().getId()+"",
//                            Zone.class
//                    );
//
//                    if (epc!=null) {
//                        proZon.setEpc(epcDb);
//                    }
//                    if(producto!=null){
//                        proZon.setProduct(producto);
//                    }
//                    if(zona!=null){
//                        proZon.setZone(zona);
//                    }
//                    //Valido que este producto pertenezca al local del usuario logeado
//                    if (zona!=null && (zona.getShop().getId() == empleado.getEmployee().getShop().getId())) {
//                        //Informacion requeria por el servicio web de crear inventory
//                        InventoryHasProduct ip = new InventoryHasProduct();
//                        ip.setZone(request.getZone());
//                        ip.setProduct(proZon);
//                        ip.setEpc(epcDb);
//
//                        products.add(ip);
//                        eanPluVieModel.addProductoZona(proZon);
//                        totalViewModel.setAmount(products.size());
//                        epcViewModel.addProductoZona(proZon);
//                        epcs.add(epc);
//                    }
//                }
//
//            }
//
//        }
    }

    private List<String> epcs;

    private boolean wasRead(String epc){
        for (String epcR:epcs){
            if(epcR.equals(epc))
                return true;
        }

        return false;
    }

    private void addToList(final String epc) {
        if(epcs.isEmpty())
            createEpc(epc);
        else{
            if(!wasRead(epc))
                createEpc(epc);
        }
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
    TotalViewModel totalViewModel;
    EanPluViewModel eanPluVieModel;
    EpcViewModel epcViewModel;
    //endregion

    //region Tabs configuration
    private CrearInventarioCooperativoStep2.SectionsPagerAdapter mSectionsPagerAdapter;
    public void tabsInit() {
//        /region Tabs section
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new CrearInventarioCooperativoStep2.SectionsPagerAdapter(getSupportFragmentManager());

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
                    TotalFragment total = TotalFragment.newInstance();
                    return total;
                case 1:
                    EanPluFragment eanPlu = EanPluFragment.newInstance();
                    eanPlu.setAdmin(admin);
                    return eanPlu;
                case 2:
                    EpcFragment epc = EpcFragment.newInstance();
                    epc.setAdmin(admin);
                    return epc;
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

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(!request.isUnion())
            builder.setTitle(R.string.crear_inventario_cooperativo);
        else
            builder.setTitle(R.string.modificar_inventario_cooperativo);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_crear_inventario, null);
        final TextView txtLocal = dialogView.findViewById(R.id.txtLocal);
        final TextView txtZona = dialogView.findViewById(R.id.txtNum);
        final TextView txtTime = dialogView.findViewById(R.id.txtTime);
        final EditText edtMensaje = dialogView.findViewById(R.id.edtMensaje);


        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        txtLocal.setText(getString(R.string.local) +": "+empleado.getEmployee().getShop().getName());
        txtZona.setText(getString(R.string.zona) + ": "+ request.getZone().getName());
        builder.setView(dialogView);


// Set up the buttons
        builder.setPositiveButton(R.string.guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Determino si es para crear o para adjuntar
                if(request.getInventory() ==null){
                    WebServices.createCollaborativeInventory(
                            request.getZone().getId(),
                            edtMensaje.getText().toString(),
                            products,
                            CrearInventarioCooperativoStep2.this,
                            admin,
                            new ResultWebServiceInterface() {
                                @Override
                                public void ok(ResultWebServiceOk ok) {
                                    admin.toast("Inventario creado con exito");
                                    admin.callIntent(CrearInventarioCooperativoStep1.class, null);
                                }

                                @Override
                                public void fail(ResultWebServiceFail fail) {
                                    admin.toast("fail");
                                }
                            }

                    );
                }else{
                    WebServices.attachInventory(
                            request.getInventory(),
                            edtMensaje.getText().toString(),
                            products,
                            CrearInventarioCooperativoStep2.this,
                            admin,
                            new ResultWebServiceInterface() {
                                @Override
                                public void ok(ResultWebServiceOk ok) {
                                    admin.toast("Inventario actualizado con exito");
                                    admin.callIntent(UnirseInventariosCooperativo.class, null);
                                }

                                @Override
                                public void fail(ResultWebServiceFail fail) {
                                    admin.toast("fail");
                                }
                            }

                    );
                }

            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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
        eanPluVieModel.clean();
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
                DataBase db = DataBase.getInstance(this);
                db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
