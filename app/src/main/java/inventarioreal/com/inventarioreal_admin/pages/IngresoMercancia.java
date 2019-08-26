package inventarioreal.com.inventarioreal_admin.pages;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterEpcs;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.listener.RFDIListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
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

public class IngresoMercancia extends CicloActivity {

    private String TAG="IngresoMercancia";
    //private UhfManager uhfManager;
    public ListAdapterEpcs adapter1;
    private LinkedList<Epc> epcs = new LinkedList<>();
    private LinkedList<Epc> epcsBanned = new LinkedList<>();
    private LinkedList<ProductHasZone> products = new LinkedList<>();
    final DataBase db = DataBase.getInstance(this);

    private Zone zonas_id;
    private Product productos_id=null;
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
        init(this,this,R.layout.activity_ingreso_mercancia);
        //region Lector Rhfi
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
//        rfdiReader.initSDK();
        //endregion
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.lbl1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEanPlu),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBus),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes3),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.img1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnZona),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lbl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSi),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnNo),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl2),Techniques.FadeInLeft,null, false));
        addElemento(new Animacion(findViewById(R.id.lst1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnCan),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnEmp),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnGua),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBor),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtTags),Techniques.FadeInLeft));

        adapter1 = new ListAdapterEpcs(this, admin, epcs, new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                admin.toast("onItemClick");
            }

            @Override
            public void onLongItemClick(Object item) {
                //Agrego el item a una lista de elementos baneados para esta lectura
                Epc epc = (Epc) item;
                epcsBanned.add(epc);
                adapter1.remove(epc);
                updatedAmountTags();
            }

            @Override
            public void onItemClick(int view, Object item) {
                admin.toast("onItemClick");
            }
        });
        RecyclerView lst1 = (RecyclerView)getElemento(R.id.lst1).getElemento();
        lst1.setLayoutManager(new LinearLayoutManager(this));
        lst1.setAdapter(adapter1);
    }

    @Override
    public void getData() {
        this.getZonas();
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnBus, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServices.getProductByEanPlu(
                        getElemento(R.id.edtEanPlu).getText(),
                        IngresoMercancia.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                Product productoConsultado = (Product) ok.getData();
                                mostrarInformacionProducto(productoConsultado);
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast(fail.getError());
                            }
                        });
            }
        });

        add_on_click(R.id.btnCan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(IngresoMercancia.class, null);
            }
        });

        add_on_click(R.id.btnBor, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });

        add_on_click(R.id.btnSi, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productos_id!=null){

                    getElemento(R.id.lnl1).getElemento().setVisibility(View.GONE);
                    getElemento(R.id.lnl2).getElemento().setVisibility(View.VISIBLE);

                }else{
                    admin.toast("Se debe buscar un producto");
                }
            }
        });
        add_on_click(R.id.btnEmp, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productos_id!=null){
                    if(rfdiReader.isStartReader()==false)
                    {
                        rfdiReader.startReader();
                        getElemento(R.id.btnEmp).setText("Detener");
                    }else{
                        rfdiReader.stopReader();
                        getElemento(R.id.btnEmp).setText("Leer");
                    }

                }else{
                    admin.toast("Se debe buscar un producto");
                }
            }
        });

        add_on_click(R.id.btnGua, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validate there is not error
                for (ProductHasZone product :
                        products) {
                    if (product.getEpc().isError()){
                        admin.toast("Algunos tags no se pueden usar, por favor valida la informacion");
                        return;
                    }
                }
                WebServices.addCommodity(
                        productos_id.id,
                        products,
                        IngresoMercancia.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok)
                            {
                                admin.toast("Productos agregados con 'exito");
                                admin.callIntent(IngresoMercancia.class, null);
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast(fail.getError());
                            }
                        }
                );
            }
        });
    }

    private void mostrarInformacionProducto(Product p){
        this.productos_id = p;
        getElemento(R.id.lblDes1).setText(p.getDescription());
        getElemento(R.id.lblDes2).setText(p.getBranch());
        getElemento(R.id.lblDes3).setText(p.getColor());
        admin.loadImageFromInternet(
                p.getImagen(),
                (NetworkImageView)getElemento(R.id.img1).getElemento(),
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background);
    }

    @Override
    public void hasAllPermissions() {

    }

    private void getZonas(){
        Gson gson = new Gson();
        //Obtengo el usuario almacenado desdes el login para usar el local al cual el usuario es asignado
        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        //Obtengo las zonas usando el local del employee
//        LinkedList<HashMap<String, String>> zonas=db.getByColumn(Constants.table_zones,Constants.locales_id, employee.getEmployee().getShop().getId());
        final LinkedList zonas=db.getByColumn(
                Constants.table_zones,
                Constants.column_shop,
                empleado.getEmployee().getShop().getId()+"",
                Zone.class);

        ArrayAdapter<Zone> adapter =
                new ArrayAdapter<>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, zonas);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        ((Spinner)getElemento(R.id.spnZona).getElemento()).setAdapter(adapter);

        ((Spinner)getElemento(R.id.spnZona).getElemento()).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zonas_id = (Zone) zonas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        WebServices.listarZonas(this, admin, new ResultWebServiceInterface() {
//            @Override
//            public void ok(ResultWebServiceOk ok) {
//                final ZonasListarResponse response = (ZonasListarResponse)ok.getData();
//
//
//            }
//
//            @Override
//            public void fail(ResultWebServiceFail fail) {
//                admin.toast(fail.getError());
//            }
//        });
    }


    //private boolean runFlag=true;
    //private boolean startFlag = false;

    @Override
    protected void onResume() {
        super.onResume();
        rfdiReader.onResume();
        /*uhfManager = UhfManager.getInstance();
        if (uhfManager == null) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    protected void onPause() {
        /*startFlag = false;
        uhfManager.close();*/
        super.onPause();
        rfdiReader.onPause();

    }
    @Override
    protected void onDestroy() {
        /*startFlag = false;
        if (uhfManager != null) {
            uhfManager.close();
        }*/
        super.onDestroy();
    }

    private Epc createEpc(String epc){
        Epc epcDb= (Epc) db.findOneByColumn(Constants.table_epcs, Constants.column_epc, "'"+epc+"'", Epc.class);
        if(epcDb!=null){
            if(epcDb.getState()==1)
                epcDb.setError(true);

            epcDb.setCount(1);
            epcDb.setEpc(epc);
            epcDb.setCount(1);
            epcs.add(epcDb);
            adapter1.add(epcDb);
            //Creo el producto zona a enviar
            ProductHasZone productosZonas = new ProductHasZone();
            productosZonas.setZone(this.zonas_id);
            productosZonas.setProduct(this.productos_id);
            productosZonas.setEpc(epcDb);
            //Check the epc was not used before

            products.add(productosZonas);
            updatedAmountTags();
            return epcDb;
        }else{
            return null;
        }


    }

    private void updatedAmountTags(){
        getElemento(R.id.txtTags).setText("Tags leidos: "+epcs.size());
    }

    private void borrar(){
        epcs.clear();
        products.clear();
        adapter1.notifyDataSetChanged();
    }

    private boolean isBanned(String epc){
        for (Epc epcB:epcsBanned){
            if(epcB.getEpc().equals(epc))
                return true;
        }
        return false;
    }

    private void addToList(final String epc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // The epc for the first time
                if (epcs.isEmpty()) {
                    createEpc(epc);
                } else {
                    for (int i = 0; i < epcs.size(); i++) {
                        Epc mEPC = epcs.get(i);
                        // list contain this epc
                        if (epc.equals(mEPC.getEpc())) {
                            mEPC.setCount(mEPC.getCount() + 1);
                            break;
                        } else if (i == (epcs.size() - 1)) {
                            //Valido que el epc no este baneado
                            if(!isBanned(epc)){
                                createEpc(epc);
                            }
                        }
                    }

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        admin.callIntent(Home.class, null);
    }


}
