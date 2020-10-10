package inventarioreal.com.inventarioreal_admin.pages.Devolutions;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Devolution;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
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

public class DevolutionStep2 extends CicloActivity {

    private String TAG="DevolucionDeClientesStep2";
    private ProductHasZone request = new ProductHasZone();
//    private DataBase db = DataBase.getInstance(this);
    private ProductHasZone product;
    private LinkedList<ProductHasZone> products = new LinkedList<>();
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
        this.product = gson.fromJson(message, ProductHasZone.class);
        //endregion
        init(this,this,R.layout.get_product_by_epc);
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
        //rfdiReader.startReader();
        //endregion

        this.tabsInit();
        // toolbar
        getSupportActionBar().setTitle(R.string.devoluciones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Create socket Connection
        socketHelper = new SocketHelper(admin);
        socketHelper.connect();
        socketHelper.subs();
    }
    @Override
    public void initGui() {

        addElemento(new Animacion(findViewById(R.id.titleIcn),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.titleTxt),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnCan),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnLee),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnFin),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBor),Techniques.FadeInLeft));

        ImageView titleIcn = (ImageView)getElemento(R.id.titleIcn).getElemento();
        switch (this.product.getDevolution().getType()){
            case 1:
                getElemento(R.id.titleTxt).setText(getString(R.string.devoluciones_clientes));
                titleIcn.setImageDrawable(getDrawable(R.drawable.icn_returns_clients_blue));
                break;
            case 2:
                getElemento(R.id.titleTxt).setText(getString(R.string.devoluciones_proveedores));
                titleIcn.setImageDrawable(getDrawable(R.drawable.icn_returns_providers_blue));
                break;
        }


    }

    @Override
    public void getData() {
        empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        epcs = new ArrayList<>();
        totalViewModel = ViewModelProviders.of(this).get(TotalViewModel.class);
        eanPluVieModel = ViewModelProviders.of(this).get(EanPluViewModel.class);
        epcViewModel = ViewModelProviders.of(this).get(EpcViewModel.class);
        Inventory inventory = new Inventory();
        inventory.setDate(this.product.getCreatedAt());
        inventory.setZone(this.product.getZone());
//        epcViewModel.setT(inventory);
        eanPluVieModel.setInventory(inventory);
        totalViewModel.setInventory(inventory);
        epcViewModel.setInventario(inventory);
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnFin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedStateLecture(false);
                boolean pass=true;
                for(ProductHasZone product: products){
                    if(product.isError()){
                        pass=false;
                        admin.toast(R.string.error_devolver_producto);
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
                Administrador.callIntent(HomeDevoluciones.class, null);
            }
        });
        add_on_click(R.id.btnBor, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean();
            }
        });
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
                            //Check if product was sell before
                            if(proZon.getSell()!= null && proZon.getSell().getId()>1){
                                //Valido que este producto pertenezca al local del usuario logeado
                                if(proZon.getZone()!=null && (proZon.getZone().getShop().getId() == empleado.getEmployee().getShop().getId())){
                                    //Check if product was sold, if was not sold, it can not be returned
                                    if(proZon.getSell()!=null && proZon.getSell().getId()<=1){
                                        proZon.setError(true);
                                    }
                                    //Check if product was returned before
                                    if(proZon.getDevolution()!=null && proZon.getDevolution().getId()>1){
                                        proZon.setError(true);
                                    }
                                    products.add(proZon);
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

        //Busco el epc en la base de datos interna
//        Epc epcDb= (Epc) db.findOneByColumn(Constants.table_epcs, Constants.epc, "'"+epc+"'", Epc.class);
//        if(epcDb!=null){
//            //Busco el producto zonas al que pertenece este tag
//            LinkedList<ProductHasZone> proZons=
//                    db.getByColumn(
//                            Constants.table_productsHasZones,
//                            Constants.column_epc_id,
//                            epcDb.getId()+"",
//                            ProductHasZone.class);
//            if(proZons !=null && proZons.size()>0){
//                ProductHasZone proZon = proZons.get(0);
//                //Busco el producto de este producto zona
//                Product producto= (Product) db.findById(
//                        Constants.table_products,
//                        proZon.getProduct().getId()+"",
//                        Product.class
//                );
//                //Busco la zona del producto zona
//                Zone zona = (Zone) db.findById(
//                        Constants.table_zones,
//                        proZon.getZone().getId()+"",
//                        Zone.class
//                );
//                if (epcDb!=null) {
//                    proZon.setEpc(epcDb);
//                }
//                if(producto!=null){
//                    proZon.setProduct(producto);
//                }
//                if(zona!=null){
//                    proZon.setZone(zona);
//                }
//                //Valido que este producto pertenezca al local del usuario logeado
//                if (zona!=null && (zona.getShop().getId() == empleado.getEmployee().getShop().getId())) {
//                    //Check if product was sold, if was not sold, it can not be returned
//                    if(proZon.getSell().getId()<=1){
//                        proZon.setError(true);
//                    }
//                    //Check if product was returned before
//                    if(proZon.getDevolution().getId()>1){
//                        proZon.setError(true);
//                    }
//                    products.add(proZon);
//                    eanPluVieModel.addProductoZona(proZon);
//                    totalViewModel.setAmount(products.size());
//                    epcViewModel.addProductoZona(proZon);
//                    epcs.add(epc);
//                }
//            }
//
//
//        }else{
//            admin.toast(getString(R.string.error_epc_no_encontrado)+ ": " +epc);
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


    //region Tab Total
    private ViewPager mViewPager;
    TotalViewModel totalViewModel;
    EanPluViewModel eanPluVieModel;
    EpcViewModel epcViewModel;
    //endregion

    //region Tabs configuration
    private DevolutionStep2.SectionsPagerAdapter mSectionsPagerAdapter;
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
        builder.setTitle(R.string.crear_devolucion);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_devolver_producto_zona, null);
//        final TextView titleTxt =dialogView.findViewById(R.id.titleTxt);
//        final ImageView titleIcn =dialogView.findViewById(R.id.titleIcn);
        final TextView txtLocal = dialogView.findViewById(R.id.txtLocal);
        final TextView txtZona = dialogView.findViewById(R.id.txtNum);
        final TextView txtFecha = dialogView.findViewById(R.id.txtFecha);
        final TextView txtTime = dialogView.findViewById(R.id.txtTime);
        final EditText edtMensaje = dialogView.findViewById(R.id.edtMensaje);
        final Button btnGuardar = dialogView.findViewById(R.id.btnGuardar);
        final Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);


        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        txtLocal.setText(getString(R.string.local) +": "+empleado.getEmployee().getShop().getName());
        txtZona.setText(getString(R.string.zona) + ": "+ product.getZone().getName());
        request.setCreatedAt(admin.getCurrentDateAndTime());
        txtFecha.setText(request.getCreatedAt().split(" ")[0]);
        txtTime.setText(request.getCreatedAt().split(" ")[1]);

        final LinkedList devoluciones = new LinkedList();
        if(this.product.getDevolution().getType() == 1) {
            devoluciones.add(new Devolution(2, getString(R.string.garantia), this.product.getDevolution().getType()));
            devoluciones.add(new Devolution(3, getString(R.string.talla_no_coincide), this.product.getDevolution().getType()));
            devoluciones.add(new Devolution(4, getString(R.string.mal_estado_producto), this.product.getDevolution().getType()));
        } else {
            devoluciones.add(new Devolution(5, getString(R.string.garantia), this.product.getDevolution().getType()));
            devoluciones.add(new Devolution(6, getString(R.string.talla_no_coincide), this.product.getDevolution().getType()));
            devoluciones.add(new Devolution(7, getString(R.string.mal_estado_producto), this.product.getDevolution().getType()));
        }
        ArrayAdapter<Zone> adapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, devoluciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner) dialogView.findViewById(R.id.spnMotDev)).setAdapter(adapter);

        ((Spinner) dialogView.findViewById(R.id.spnMotDev)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (ProductHasZone pr: products) {
                    pr.setDevolution((Devolution)devoluciones.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                request.setZone(null);
            }
        });
        // end region



        builder.setView(dialogView);

        final AlertDialog show = builder.show();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ProductHasZone pr: products) {
                    pr.setNotes_return(edtMensaje.getText().toString());
                }
                WebServices.returnProducts(
                        products,
                        DevolutionStep2.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                admin.toast(R.string.productos_devueltos_exito);
                                Administrador.callIntent(HomeDevoluciones.class, null);
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast(R.string.productos_devueltos_error);
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
    //endregion

//    @Override
//    public void onBackPressed() {
////        admin.callIntent(DevolutionStep1.class, null);
//    }

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
                //DataBase db = DataBase.getInstance(this);
                //db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}