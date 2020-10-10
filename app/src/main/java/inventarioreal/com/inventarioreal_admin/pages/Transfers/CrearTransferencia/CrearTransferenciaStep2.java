package inventarioreal.com.inventarioreal_admin.pages.Transfers.CrearTransferencia;

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
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.RFDIListener;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.tabs.EanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.tabs.EanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.tabs.EpcFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.tabs.EpcViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.tabs.TotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.tabs.TotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;
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

public class CrearTransferenciaStep2 extends CicloActivity {

    private Transfer request = null;

    private String TAG="CrearInventarioStep2";
//    private DataBase db = DataBase.getInstance(this);
    private Gson gson = new Gson();
    private LinkedList<TransfersHasZonesProduct> products = new LinkedList<>();
    RFDIReader rfdiReader =  null;
    LoginResponse empleado;
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
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.request = gson.fromJson(message, Transfer.class);

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
        init(this, this, R.layout.get_product_by_epc);

        this.tabsInit();
        //toolbar
        getSupportActionBar().setTitle(R.string.crear_transferencia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Create socket Connection
        socketHelper = new SocketHelper(admin);
        socketHelper.connect();
        socketHelper.subs();
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.titleTxt), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.titleIcn), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnCan),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnLee),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnFin),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBor),Techniques.FadeInLeft));

        getElemento(R.id.titleTxt).setText(getString(R.string.crear_transferencia));
        ImageView img = (ImageView) getElemento(R.id.titleIcn).getElemento();
        img.setImageDrawable(getDrawable(R.drawable.icn_transfer_blue));
    }

    @Override
    public void getData() {
        empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        epcs = new ArrayList<>();
        totalViewModel = ViewModelProviders.of(this).get(TotalViewModel.class);
        eanPluVieModel = ViewModelProviders.of(this).get(EanPluViewModel.class);
        epcViewModel = ViewModelProviders.of(this).get(EpcViewModel.class);
        eanPluVieModel.setTransfer(this.request);
        epcViewModel.setTransfer(this.request);
        totalViewModel.setTransfer(this.request);
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnFin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(products.size()>0)
                    showDialog();
                else{
                    admin.toast(R.string.error_minimo_un_producto);
                }
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
                Administrador.callIntent(CrearTransferenciaStep1.class, null);
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
                            //Valido que este producto pertenezca al local del usuario logeado
                            if(proZon.getZone()!=null && (proZon.getZone().getShop().getId() == empleado.getEmployee().getShop().getId())){
                                //Informacion requeria por el servicio web de crear inventory
                                TransfersHasZonesProduct pzt = new TransfersHasZonesProduct();
                                pzt.setProduct(proZon);
                                products.add(pzt);
                                eanPluVieModel.addProductoZona(proZon);
                                epcViewModel.addProductoZona(proZon);
                                totalViewModel.setAmount(products.size());
                                epcs.add(epc);
                            }
                        }
                    });

                }

            }

            @Override
            public void fail(ResultWebServiceFail fail) {
                System.out.println("Fail");
            }
        });


        //Busco el epc en la base de datos interna
//        Epc epcDb= (Epc) db.findOneByColumn(Constants.table_epcs, Constants.epc, "'"+epc+"'", Epc.class);
//        if(epcDb!=null){
//            //Busco el producto zonas al que pertenece este tag
//            try {
//                ProductHasZone proZon=
//                        (ProductHasZone) db.getByColumn(
//                                Constants.table_productsHasZones,
//                                Constants.column_epc_id,
//                                epcDb.getId()+"",
//                                ProductHasZone.class).get(0);
//                //Busco el producto de este producto zona
//                Product producto= (Product) db.findById(
//                        Constants.table_products,
//                        proZon.getProduct().getId()+"",
//                        Product.class
//                );
//
//                //Busco la zona del producto zona
//                Zone zona = (Zone) db.findById(
//                        Constants.table_zones,
//                        proZon.getZone().getId()+"",
//                        Zone.class
//                );
//
//                if (epcDb!=null) {
//                    proZon.setEpc(epcDb);
//                }
//                if(producto!=null){
//                    proZon.setProduct(producto);
//                }
//                if(zona!=null){
//                    proZon.setZone(zona);
//                }
//
//                //Valido que este producto pertenezca al local del usuario logeado
//                if(zona!=null && (zona.getShop().getId() == empleado.getEmployee().getShop().getId())){
//                    //Informacion requeria por el servicio web de crear inventory
//                    TransfersHasZonesProduct pzt = new TransfersHasZonesProduct();
//                    pzt.setProduct(proZon);
//                    products.add(pzt);
//                    eanPluVieModel.addProductoZona(proZon);
//                    epcViewModel.addProductoZona(proZon);
//                    totalViewModel.setAmount(products.size());
//                    epcs.add(epc);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else{
////            admin.toast(getString(R.string.error_epc_no_encontrado)+": "+ epc);
//        }
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

    //region Tab Total
    private ViewPager mViewPager;
    TotalViewModel totalViewModel;
    EanPluViewModel eanPluVieModel;
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
                    TotalFragment total = TotalFragment.newInstance();
                    return total;
                case 1:
                    EanPluFragment eanPlu = EanPluFragment.newInstance();
                    eanPlu.setAdmin(admin);
                    return eanPlu;
                case 2:
                    EpcFragment epcFragment = EpcFragment.newInstance();
                    epcFragment.setAdmin(admin);
                    return epcFragment;
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
            return 3;
        }
    }
    //endregion

    private void showDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.crear_transferencia);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_crear_inventario, null);
        final TextView titleTxt =dialogView.findViewById(R.id.titleTxt);
        final ImageView titleIcn =dialogView.findViewById(R.id.titleIcn);
        final TextView txtLocal = dialogView.findViewById(R.id.txtLocal);
        final TextView txtNum = dialogView.findViewById(R.id.txtNum);
        final TextView txtFecha = dialogView.findViewById(R.id.txtFecha);
        final TextView txtTime = dialogView.findViewById(R.id.txtTime);
        final EditText edtMensaje = dialogView.findViewById(R.id.edtMensaje);
        final Button btnGuardar = dialogView.findViewById(R.id.btnGuardar);
        final Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);

        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        txtLocal.setText(getString(R.string.local_origen)+": "+empleado.getEmployee().getShop().getName());
        txtNum.setText(getString(R.string.local_destino) + ": "+request.getShopDestination().getName());
        txtFecha.setText(request.getCreatedAt().split(" ")[0]);
        txtTime.setText(request.getCreatedAt().split(" ")[1]);
        builder.setView(dialogView);

        final AlertDialog show = builder.show();

        titleTxt.setText(R.string.crear_transferencia);
        titleIcn.setImageDrawable(getDrawable(R.drawable.icn_transfer_blue));

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin.toast(edtMensaje.getText().toString());
                request.setMessage(edtMensaje.getText().toString());
                WebServices.createTransfer(
                        request,
                        products,
                        CrearTransferenciaStep2.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                admin.toast(R.string.transferencia_exito);
                                Administrador.callIntent(CrearTransferenciaStep1.class, null);
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast(R.string.transferencia_error);
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

    @Override
    public void onBackPressed() {
        Administrador.callIntent(CrearTransferenciaStep1.class, null);
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
                //DataBase db = DataBase.getInstance(this);
                //db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
