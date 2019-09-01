package inventarioreal.com.inventarioreal_admin.pages.Devolutions.DeClientes;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.RFDIListener;
import inventarioreal.com.inventarioreal_admin.pages.Devolutions.HomeDevoluciones;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step1.CrearInventarioStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.TotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.TotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Devolution;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
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

public class DevolutionStep2 extends CicloActivity {

    private String TAG="DevolucionDeClientesStep2";
    private ProductHasZone request = new ProductHasZone();
    private DataBase db = DataBase.getInstance(this);
    private ProductHasZone products;
    private LinkedList<ProductHasZone> productosZonas = new LinkedList<>();
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
                    break ;
                case 3:
                    boolean state = msg.getData().getBoolean("state");
                    changedStateLecture(state);
                    break ;
            }
        }
    } ;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            }

            @Override
            public void onKeyPresses(String key) {

            }
        }, this);
        rfdiReader.initSDK();
        //rfdiReader.startReader();
        //endregion
        //region Obtener parametros
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.products = gson.fromJson(message, ProductHasZone.class);
        //endregion
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
        totalViewModel = ViewModelProviders.of(this).get(TotalViewModel.class);
        eanPluVieModel = ViewModelProviders.of(this).get(EanPluViewModel.class);
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnFin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rfdiReader.setStartReader(false);
                getElemento(R.id.btnLee).setText("Leer");
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
                admin.callIntent(CrearInventarioStep1.class, null);
            }
        });
    }


    private void changedStateLecture(boolean state){
        if(state){
            rfdiReader.startReader();
            getElemento(R.id.btnLee).setText("Detener");
        }else{
            rfdiReader.stopReader();
            getElemento(R.id.btnLee).setText("Leer");
        }
    }

    @Override
    public void hasAllPermissions() {
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
            // verifico si el epc pertenece a la zona seleccionada
            /*if (proZon.getZone().getId() == products.getZone().getId())
            {*/
                if (epcDb!=null) {
                    proZon.setEpc(epcDb);
                }
                if(producto!=null){
                    proZon.setProduct(producto);
                }
                /*
                if (epcDb!=null) {
                    //proZon.setDevolution(epcDb);

                }*/
                productosZonas.add(proZon);
                eanPluVieModel.addProductoZona(proZon);
                totalViewModel.setAmount(productosZonas.size());
                epcs.add(epc);
            /*}
            else{
                admin.toast("Epc no found: "+ epc);
            }*/

        }else{
            admin.toast("Epc no found: "+ epc);
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
        if(epcs.isEmpty())
            createEpc(epc);
        else{
            if(!wasRead(epc))
                createEpc(epc);
        }
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
    TotalViewModel totalViewModel;
    EanPluViewModel eanPluVieModel;
    //endregion

    //region Tabs configuration
    private DevolutionStep2.SectionsPagerAdapter mSectionsPagerAdapter;
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
                    TotalFragment total = new TotalFragment();
                    return total;
                case 1:
                    EanPluFragment eanPlu = EanPluFragment.newInstance();
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

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear Devolucion");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_devolver_producto_zona, null);
        final TextView txtLocal = dialogView.findViewById(R.id.txtLocal);
        final TextView txtZona = dialogView.findViewById(R.id.txtNum);
        final TextView txtTime = dialogView.findViewById(R.id.txtTime);
        final EditText edtMensaje = dialogView.findViewById(R.id.edtMensaje);


        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        txtLocal.setText("Local : "+empleado.getEmployee().getShop().getName());
        txtZona.setText("Zonas : "+ products.getZone().getName());
        request.setCreatedAt(admin.getCurrentDateAndTime());
        txtTime.setText(request.getCreatedAt());
        // inicio spnMotDev
        final LinkedList devoluciones = db.getByColumn(
                Constants.table_devolutions,"type",this.products.getDevolution().getType().toString(), Devolution.class);

        ArrayAdapter<Zone> adapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, devoluciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner) dialogView.findViewById(R.id.spnMotDev)).setAdapter(adapter);

        ((Spinner) dialogView.findViewById(R.id.spnMotDev)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (ProductHasZone pr: productosZonas) {
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


// Set up the buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (ProductHasZone pr: productosZonas) {
                    pr.setNotes_return(edtMensaje.getText().toString());
                }
                WebServices.returnProducts(
                        productosZonas,
                        DevolutionStep2.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                admin.toast("Productos devueltos con exito");
                                admin.callIntent(HomeDevoluciones.class, null);
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
    //endregion

    @Override
    public void onBackPressed() {
        admin.callIntent(DevolutionStep1.class, null);
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
}
