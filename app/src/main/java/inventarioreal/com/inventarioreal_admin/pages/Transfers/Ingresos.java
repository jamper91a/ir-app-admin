package inventarioreal.com.inventarioreal_admin.pages.Transfers;

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
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EpcFragment;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs.EpcViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransEanPluFragment;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransEanPluViewModel;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransTotalFragment;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs.TransTotalViewModel;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.RFDIReader;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class Ingresos extends CicloActivity {

//    private UhfManager uhfManager;
    private String TAG="Ingresos";
    private DataBase db = DataBase.getInstance(this);
    private Gson gson = new Gson();
    private Transfer[] transferencias = null;
    private LinkedList<TransfersHasZonesProduct> productosZonasHasTransferencias= new LinkedList<>();
    private Shop local= null;

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
        local = ((LoginResponse) gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class)).getEmployee().getShop();
        this.tabsInit();
        //toolbar
        getSupportActionBar().setTitle("Ingresos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        totalViewModel = ViewModelProviders.of(this).get(TransTotalViewModel.class);
        eanPluVieModel = ViewModelProviders.of(this).get(TransEanPluViewModel.class);
        epcViewModel = ViewModelProviders.of(this).get(EpcViewModel.class);
        getTransferencias();
        totalViewModel.setDate(admin.getCurrentDateAndTime());
    }

    public void getTransferencias(){
        WebServices.getTransfers(
                this,
                admin,
                new ResultWebServiceInterface() {
                    @Override
                    public void ok(ResultWebServiceOk ok) {
                        transferencias = (Transfer[]) ok.getData();

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
                for (TransfersHasZonesProduct pzt:  productosZonasHasTransferencias)
                {
                    if(pzt.getState()==false)
                    {
                        admin.toast("Hay productos que no pertenecen a este local (E) y no seran transferidos");
                    }
                }
                showDialog();
            }
        });

        add_on_click(R.id.btnLee, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedStateLecture(!rfdiReader.isStartReader());
            }
        });

        add_on_click(R.id.btnBor, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean();
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

    private void changedStateLecture(boolean state){
        if(state){
            rfdiReader.startReader();
            getElemento(R.id.btnLee).setText("Detener");
        }else{
            rfdiReader.stopReader();
            getElemento(R.id.btnLee).setText("Leer");
        }
    }
    //region UHD Sdk
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

                if (epcDb!=null) {
                    proZon.setEpc(epcDb);
                }
                if(producto!=null){
                    proZon.setProduct(producto);
                }
                //Determino si el productozona de este tag esta en la lista de transferencia
                for (Transfer transferencia :
                        transferencias) {
                    if(transferencia.getShopDestination().getId()==local.getId()){
                        for(TransfersHasZonesProduct pzt: transferencia.getProducts()){

                            if(pzt.getProduct().getId() == proZon.getId()){
                                //Product was transfered before
                                if(pzt.getState()==true)
                                    pzt.setState(false);
                                else{
                                    //Check if the pzt belongs to the current local
                                    try {

                                        if(transferencia.getShopDestination().getId() == local.getId())
                                            pzt.setState(true);

                                    } catch (Exception e) {
                                        pzt.setState(false);
                                    }


                                }
                                pzt.setProduct(proZon);
                                pzt.setTransfer(transferencia);
                                productosZonasHasTransferencias.add(pzt);
                                epcViewModel.addProductoZona(proZon);
                                eanPluVieModel.addProductoZonaHasTransferencia(pzt);



                            }

                        }
                    }
                }


                //Informacion requeria por el servicio web de crear inventory
                totalViewModel.setAmount(productosZonasHasTransferencias.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        if(epcs.isEmpty())
            createEpc(epc);
        else{
            if(!wasRead(epc))
                createEpc(epc);
        }
    }

    //region Tab Total
    private ViewPager mViewPager;
    TransTotalViewModel totalViewModel;
    TransEanPluViewModel eanPluVieModel;
    EpcViewModel epcViewModel;
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
                    TransTotalFragment total = TransTotalFragment.newInstance();
                    return total;
                case 1:
                    TransEanPluFragment eanPlu = TransEanPluFragment.newInstance();
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
        //Stop reading
//        startFlag=false;
        rfdiReader.setStartReader(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guardar Transferencia");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_crear_inventario, null);
        final TextView txtLocal = dialogView.findViewById(R.id.txtLocal);
        final TextView txtTime = dialogView.findViewById(R.id.txtTime);
        final EditText edtMensaje = dialogView.findViewById(R.id.edtMensaje);


        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        txtLocal.setText("Local : "+empleado.getEmployee().getShop().getName());
        builder.setView(dialogView);


// Set up the buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebServices.finishTransfer(
                        productosZonasHasTransferencias,
                        edtMensaje.getText().toString(),
                        Ingresos.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                ProductHasZone[] pzhs = (ProductHasZone[]) ok.getData();
                                //Update local pzh
                                for (ProductHasZone productHasZone: pzhs
                                     ) {
                                    db.update(
                                            Constants.table_productsHasZones,
                                            productHasZone.getId()+"",
                                            productHasZone.getContentValues()
                                    );

                                }
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
        productosZonasHasTransferencias.clear();
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
