package inventarioreal.com.inventarioreal_admin.pages.Transferencias;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.handheld.UHF.UhfManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.pda.serialport.Tools;
import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Transferencias.tabs.EanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Transferencias.tabs.EanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Transferencias.tabs.TotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Transferencias.tabs.TotalViewModel;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epcs;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Locales;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Productos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonasHasTransferencias;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transferencias;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class Ingresos extends CicloActivity {

    private UhfManager uhfManager;
    private String TAG="CrearInventarioStep2";
    private DataBase db = DataBase.getInstance(this);
    private LinkedList<ProductosZonasHasTransferencias> auxProZonTrans = new LinkedList<>();
    private Gson gson = new Gson();
    private Transferencias[] transferencias = null;
    private LinkedList<ProductosZonasHasTransferencias> productosZonasHasTransferencias= new LinkedList<>();
    private Locales local= null;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_inventario_parcial_crear_inventario_step_2);
        local = ((LoginResponse) gson.fromJson(admin.obtener_preferencia(Constants.empleado), LoginResponse.class)).getEmpleado().getLocales_id();
        //region UhF
        Thread thread = new InventoryThread();
        thread.start();
        //endregion
        this.tabsInit();
//        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(mTopToolbar);
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
        getTransferencias();
    }

    public void getTransferencias(){
        WebServices.getTransferencias(
                this,
                admin,
                new ResultWebServiceInterface() {
                    @Override
                    public void ok(ResultWebServiceOk ok) {
                        transferencias = (Transferencias[]) ok.getData();
                        startFlag=true;

                    }

                    @Override
                    public void fail(ResultWebServiceFail fail) {

                    }
                }
        );
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnFin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if there are not errors
                for (ProductosZonasHasTransferencias pzt:  productosZonasHasTransferencias)
                {
                    if(pzt.getEstado()==false)
                    {
                        admin.toast("Hay productos que no pertenecen a este local");
                        return;
                    }
                }
                showDialog();
            }
        });

        add_on_click(R.id.btnLee, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startFlag==false)
                {
                    startFlag=true;
                    getElemento(R.id.btnLee).setText("Detener");
                }else{
                    startFlag=false;
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

    //region UHD Sdk
    public void initSdk(){
        try {
            uhfManager = UhfManager.getInstance();
            uhfManager.setOutputPower(26);
            uhfManager.setWorkArea(2);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    private boolean runFlag=true;
    private boolean startFlag = false;

    @Override
    protected void onResume() {
        super.onResume();
        uhfManager = UhfManager.getInstance();
        if (uhfManager == null) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initSdk();
    }

    @Override
    protected void onPause() {
        startFlag = false;
        uhfManager.close();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        startFlag = false;
        if (uhfManager != null) {
            uhfManager.close();
        }
        super.onDestroy();
    }

    private void createEpc(String epc){
        //Busco el epc en la base de datos interna
        Epcs epcDb= (Epcs) db.findOneByColumn(Constants.table_epcs, Constants.epc, "'"+epc+"'", Epcs.class);
        if(epcDb!=null){
            try {
                //Busco el producto zonas al que pertenece este tag
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
                //Determino si el productozona de este tag esta en la lista de transferencia
                for (Transferencias transferencia :
                        transferencias) {

                    for(ProductosZonasHasTransferencias pzt: transferencia.getProductos()){

                        if(pzt.getProductos_zona_id().getId() == proZon.getId()){
                            pzt.setProductos_zona_id(proZon);
                            //Check if the pzt belongs to the current local
                            try {
                                if(transferencia.getLocal_destino_id().getId() == local.getId())
                                    pzt.setEstado(true);
                            } catch (Exception e) {
                                pzt.setEstado(false);
                            }

                            pzt.setTransferencias_id(transferencia);
                            productosZonasHasTransferencias.add(pzt);
                            eanPluVieModel.addProductoZonaHasTransferencia(pzt);
                        }

                    }
                }

                //Informacion requeria por el servicio web de crear inventario
                totalViewModel.setAmount(auxProZonTrans.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            epcs.add(epc);
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

    class InventoryThread extends Thread {
        private List<byte[]> epcList;

        @Override
        public void run() {
            super.run();
            while (runFlag) {
                if (startFlag) {
                    // manager.stopInventoryMulti()
                    epcList = uhfManager.inventoryRealTime(); // inventory real time
                    if (epcList != null && !epcList.isEmpty()) {
                        for (byte[] epc : epcList) {
                            String epcStr = Tools.Bytes2HexString(epc,
                                    epc.length);
                            addToList(epcStr);
                        }
                    }
                    epcList = null;
                    try {
                        Thread.sleep( 40);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
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
        mSectionsPagerAdapter = new Ingresos.SectionsPagerAdapter(getSupportFragmentManager());

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
        //Stop reading
        startFlag=false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear Inventario");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_crear_inventario, null);
        final TextView txtLocal = dialogView.findViewById(R.id.txtLocal);
        final TextView txtTime = dialogView.findViewById(R.id.txtTime);
        final EditText edtMensaje = dialogView.findViewById(R.id.edtMensaje);


        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.empleado), LoginResponse.class);
        txtLocal.setText("Local : "+empleado.getEmpleado().getLocales_id().getName());
        builder.setView(dialogView);


// Set up the buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebServices.finalizarTransferencia(
                        productosZonasHasTransferencias,
                        Ingresos.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                admin.toast("Transferencia realizada con 'exito");
                                admin.callIntent(HomeTransferencia.class, null);
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
