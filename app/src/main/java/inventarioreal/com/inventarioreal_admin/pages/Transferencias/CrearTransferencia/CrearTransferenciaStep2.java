package inventarioreal.com.inventarioreal_admin.pages.Transferencias.CrearTransferencia;

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
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.Crear.Step1.CrearInventarioStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.Crear.Step2.tabs.EanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.Crear.Step2.tabs.EanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.Crear.Step2.tabs.TotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.Crear.Step2.tabs.TotalViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epcs;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Productos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonasHasTransferencias;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transferencias;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.RFDIReader;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class CrearTransferenciaStep2 extends CicloActivity {

    private Transferencias request = null;

    private String TAG="CrearInventarioStep2";
    private DataBase db = DataBase.getInstance(this);
    private Gson gson = new Gson();
    private LinkedList<ProductosZonasHasTransferencias> productos = new LinkedList<>();
    RFDIReader rfdiReader =  null;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String epc = msg.getData().getString("epc");
//                    admin.toast(epc);
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
                Message msg = new Message();
                msg.what = 1;
                Bundle b = new Bundle();
                b.putString("epc", "onEpcRepeated:"+epc);
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        rfdiReader.initSDK();
        init(this, this, R.layout.get_product_by_epc);
        //region UhF
//        Thread thread = new InventoryThread();
//        thread.start();
        //endregion
        //region Obtener parametros
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.request = gson.fromJson(message, Transferencias.class);
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
//                if(startFlag==false)
                if(rfdiReader.isStartReader()==false)
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

        add_on_click(R.id.btnCan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(CrearInventarioStep1.class, null);
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

    //region UHD Sdk
//    public void initSdk(){
//        try {
//            uhfManager = UhfManager.getInstance();
//            uhfManager.setOutputPower(26);
//            uhfManager.setWorkArea(2);
//            startFlag=true;
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//        }
//
//    }

//    private boolean runFlag=true;
//    private boolean startFlag = false;

    @Override
    protected void onResume() {
        super.onResume();
        rfdiReader.onResume();
//        uhfManager = UhfManager.getInstance();
//        if (uhfManager == null) {
//            return;
//        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        initSdk();
    }

    @Override
    protected void onPause() {
//        startFlag = false;
//        uhfManager.close();
        super.onPause();
        rfdiReader.onPause();
    }
    @Override
    protected void onDestroy() {
//        startFlag = false;
//        if (uhfManager != null) {
//            uhfManager.close();
//        }
        super.onDestroy();
        rfdiReader.onDestroy();
    }

    private void createEpc(String epc){
        //Busco el epc en la base de datos interna
        Epcs epcDb= (Epcs) db.findOneByColumn(Constants.table_epcs, Constants.epc, "'"+epc+"'", Epcs.class);
        if(epcDb!=null){
            //Busco el producto zonas al que pertenece este tag
            try {
                ProductosZonas proZon=
                        (ProductosZonas) db.getByColumn(
                                Constants.table_productos_zonas,
                                Constants.epcs_id,
                                epcDb.getId()+"",
                                ProductosZonas.class).get(0);
                //Busco el producto de este producto zona
                Productos producto= (Productos) db.findById(
                        Constants.table_productos,
                        proZon.getProductos_id().getId()+"",
                        Productos.class
                );

                if (epcDb!=null) {
                    proZon.setEpcs_id(epcDb);
                }
                if(producto!=null){
                    proZon.setProductos_id(producto);
                }
                //Informacion requeria por el servicio web de crear inventario
                ProductosZonasHasTransferencias pzt = new ProductosZonasHasTransferencias();
                pzt.setProductos_zona_id(proZon);
                productos.add(pzt);
                eanPluVieModel.addProductoZona(proZon);
                totalViewModel.setAmount(productos.size());
                epcs.add(epc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
//            admin.toast("Epc no found: "+ epc);
        }
    }


    private List<String> epcs;

    private boolean wasRead(String epc){
//        for (int i = 0; i < eanPluVieModel.getProductosZonaHasTransferencia().getValue().size(); i++) {
//            //Determino si ese epc ya se leyo antes
//            ProductosZonas mEPC = eanPluVieModel.getProductosZonaHasTransferencia().getValue().get(i);
//            if (epc.equals(mEPC.getEpcs_id().getEpc())){
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
//                if (eanPluVieModel.getProductosZonaHasTransferencia().getValue().isEmpty()) {
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

    /**
     * Inventory Epcs Thread
     */

//    class InventoryThread extends Thread {
//        private List<byte[]> epcList;
//
//        @Override
//        public void run() {
//            super.run();
//            while (runFlag) {
//                if (startFlag) {
//                    // manager.stopInventoryMulti()
//                    epcList = uhfManager.inventoryRealTime(); // inventory real time
//                    if (epcList != null && !epcList.isEmpty()) {
//                        for (byte[] epc : epcList) {
//                            String epcStr = Tools.Bytes2HexString(epc,
//                                    epc.length);
//                            addToList(epcStr);
//                        }
//                    }
//                    epcList = null;
//                    try {
//                        Thread.sleep( 40);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
    //endregion

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
        builder.setTitle("Crear Inventario");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_crear_inventario, null);
        final TextView txtLocal = dialogView.findViewById(R.id.txtLocal);
        final TextView txtNum = dialogView.findViewById(R.id.txtNum);
        final TextView txtTime = dialogView.findViewById(R.id.txtTime);
        final EditText edtMensaje = dialogView.findViewById(R.id.edtMensaje);


        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.empleado), LoginResponse.class);
        txtLocal.setText("Local Origen : "+empleado.getEmpleado().getLocales_id().getName());
        txtNum.setText("Local Destino: "+request.getLocal_destino_id().getName());
        builder.setView(dialogView);


// Set up the buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                admin.toast(edtMensaje.getText().toString());
                request.setMensaje(edtMensaje.getText().toString());
                WebServices.crearTransferencia(
                        request,
                        productos,
                        CrearTransferenciaStep2.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                admin.toast("Transferencia creada con 'exito");
                                admin.callIntent(CrearTransferenciaStep1.class, null);
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast(fail.getError());
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
