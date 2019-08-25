package inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos.Crear.Step2;

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
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Intents.RequestInventariorCrear2;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.Crear.Step2.tabs.EanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.Crear.Step2.tabs.EanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.Crear.Step2.tabs.TotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.Crear.Step2.tabs.TotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos.Crear.Step1.CrearInventarioColaborativoStep1;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventoryHasProduct;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.RFDIReader;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class CrearInventarioColaborativoStep2 extends CicloActivity {

    //private UhfManager uhfManager;
    private String TAG="CrearInventarioStep2";
    private DataBase db = DataBase.getInstance(this);
    private RequestInventariorCrear2 requestInventariorCrear2;
    private LinkedList<InventoryHasProduct> inventariosProductos = new LinkedList<>();
    private Gson gson = new Gson();

    RFDIReader rfdiReader =  null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String epc;
            switch (msg.what){
                case 1:
                    epc = msg.getData().getString("epc");
                    addToList(epc);
                    //admin.toast("Epc found: "+epc); //readed
                    break ;
                case 2:
                    epc = msg.getData().getString("epc");
                    //admin.toast("Epc repeted: "+epc); // repeted
                    break ;
            }
        }
    } ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        });
        rfdiReader.initSDK();
//        rfdiReader.startReader();
        //endregion
        //region Obtener parametros
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.requestInventariorCrear2 = gson.fromJson(message, RequestInventariorCrear2.class);
        //endregion
        this.tabsInit();
        
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
        totalViewModel = ViewModelProviders.of(this).get(TotalViewModel.class);
        eanPluVieModel = ViewModelProviders.of(this).get(EanPluViewModel.class);
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnFin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        add_on_click(R.id.btnLee, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rfdiReader.isStartReader() ==false)
                {
                    rfdiReader.startReader();
//                    startFlag=true;
                    getElemento(R.id.btnLee).setText("Detener");
                }else{
                    rfdiReader.setStartReader(false);
//                    startFlag=false;
                    getElemento(R.id.btnLee).setText("Leer");
                }
            }
        });
    }

    @Override
    public void hasAllPermissions() {
       // startFlag=true;

    }

    //region UHD Sdk
   /* public void initSdk(){
        try {
            uhfManager = UhfManager.getInstance();
            uhfManager.setOutputPower(26);
            uhfManager.setWorkArea(2);
            startFlag=true;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }*/

    //private boolean runFlag=true;
   // private boolean startFlag = false;

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
        /*startFlag = false;
        if (uhfManager != null) {
            uhfManager.close();
        }*/
        rfdiReader.onDestroy();
        super.onDestroy();
    }

    private void createEpc(String epc){
        //Busco el epc en la base de datos interna
        Epc epcDb= (Epc) db.findOneByColumn(Constants.table_epcs, Constants.epc, "'"+epc+"'", Epc.class);
        if(epcDb!=null){
            //Busco el producto zonas al que pertenece este tag
            ProductHasZone proZon=
                    (ProductHasZone) db.getByColumn(
                            Constants.table_productsHasZones,
                            Constants.column_epc_id,
                            epcDb.getId()+"",
                            ProductHasZone.class).get(0);
            //Busco el producto de este producto zona
            Product producto= (Product) db.findById(
                    Constants.table_products,
                    proZon.getProduct().getId()+"",
                    Product.class
            );

            if (epc!=null) {
                proZon.setEpc(epcDb);
            }
            if(producto!=null){
                proZon.setProduct(producto);
            }
            //Informacion requeria por el servicio web de crear inventory
            InventoryHasProduct ip = new InventoryHasProduct();
            ip.setZone(requestInventariorCrear2.getZone());
            ip.setProduct(proZon);
            ip.setEpc(epcDb);

            inventariosProductos.add(ip);
            eanPluVieModel.addProductoZona(proZon);
            totalViewModel.setAmount(inventariosProductos.size());
            epcs.add(epc);
        }
    }

    private List<String> epcs;

    private boolean wasRead(String epc){
//        for (int i = 0; i < eanPluVieModel.getProducts().getValue().size(); i++) {
//            //Determino si ese epc ya se leyo antes
//            ProductosZonas mEPC = eanPluVieModel.getProducts().getValue().get(i);
//            if (epc.equals(mEPC.getEpc().getEpc())){
//                return true;
//            }
//
//        }
//        return false;
        for (String epcR:epcs){
            if(epcR.equals(epc))
                return true;
        }

        return false;
    }

    private void addToList(final String epc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // The epc for the first time
//                if (eanPluVieModel.getProducts().getValue().isEmpty()) {
//                    createEpc(epc);
//                } else {
//                    //Determino si ese epc ya se leyo antes
//                    if(!wasRead(epc))
//                        createEpc(epc);
//                }

                if(epcs.isEmpty())
                    createEpc(epc);
                else{
                    if(!wasRead(epc))
                        createEpc(epc);
                }
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

        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Tab Total
    private ViewPager mViewPager;
    TotalViewModel totalViewModel;
    EanPluViewModel eanPluVieModel;
    //endregion

    //region Tabs configuration
    private CrearInventarioColaborativoStep2.SectionsPagerAdapter mSectionsPagerAdapter;
    public void tabsInit() {
//        /region Tabs section
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new CrearInventarioColaborativoStep2.SectionsPagerAdapter(getSupportFragmentManager());

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
                    TotalFragment total = new TotalFragment();
                    return total;
                case 1:
                    EanPluFragment eanPlu = EanPluFragment.newInstance();
                    eanPlu.setAdmin(admin);
                    return eanPlu;
//                case 2:
//                    Epc epc = new Epc();
//                    return epc;
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

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(!requestInventariorCrear2.isUnion())
            builder.setTitle("Crear Inventario Colaborativo");
        else
            builder.setTitle("Modificar Inventario Colaborativo");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_crear_inventario, null);
        final TextView txtLocal = dialogView.findViewById(R.id.txtLocal);
        final TextView txtZona = dialogView.findViewById(R.id.txtNum);
        final TextView txtTime = dialogView.findViewById(R.id.txtTime);
        final EditText edtMensaje = dialogView.findViewById(R.id.edtMensaje);


        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        txtLocal.setText("Local : "+empleado.getEmployee().getShop().getName());
        txtZona.setText("Zonas : "+requestInventariorCrear2.getZone().getName());
        builder.setView(dialogView);


// Set up the buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Determino si es para crear o para adjuntar
                if(requestInventariorCrear2.inventory ==null){
                    WebServices.createCollaborativeInventory(
                            requestInventariorCrear2.getZone().getId(),
                            inventariosProductos,
                            CrearInventarioColaborativoStep2.this,
                            admin,
                            new ResultWebServiceInterface() {
                                @Override
                                public void ok(ResultWebServiceOk ok) {
                                    admin.toast("Inventario creado con exito");
                                    admin.callIntent(CrearInventarioColaborativoStep1.class, null);
                                }

                                @Override
                                public void fail(ResultWebServiceFail fail) {
                                    admin.toast("fail");
                                }
                            }

                    );
                }else{
                    WebServices.attachInventory(
                            requestInventariorCrear2.inventory,
                            inventariosProductos,
                            CrearInventarioColaborativoStep2.this,
                            admin,
                            new ResultWebServiceInterface() {
                                @Override
                                public void ok(ResultWebServiceOk ok) {
                                    admin.toast("Inventario actualizado con exito");
                                    admin.callIntent(CrearInventarioColaborativoStep1.class, null);
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
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
