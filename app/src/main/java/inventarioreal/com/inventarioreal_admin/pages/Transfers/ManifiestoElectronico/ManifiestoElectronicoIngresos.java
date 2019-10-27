package inventarioreal.com.inventarioreal_admin.pages.Transfers.ManifiestoElectronico;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.RecyclerAdapterTransferencias;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.ProductosTransferenciaDetail;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.TransferenciaDetails;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ManifiestoElectronicoIngresos extends CicloActivity {

    private static final String TAG = "ManifiestoElectronicoIngresos";
    private RecyclerAdapterTransferencias adapter;
    private ArrayList<Transfer> transferencias= new ArrayList<>();
    private RecyclerView recyclerView = null;
    private final DataBase db = DataBase.getInstance(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_manifiesto_electronico_ingresos);
        //toolbar
        getSupportActionBar().setTitle(R.string.manifiesto_electronico_ingresos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.lst1), Techniques.SlideInLeft));
    }

    @Override
    public void getData() {
        this.getTransferenciasIngreso();
    }

    private void getTransferenciasIngreso() {
        recyclerView = (RecyclerView) getElemento(R.id.lst1).getElemento();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapterTransferencias(this, transferencias, admin, new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                //Obtengo la transferencia a consultar
                Transfer transferencia = (Transfer) item;
                //Convierto la transferencia a transferenciadetails
                TransferenciaDetails transferenciaDetails = new TransferenciaDetails();

                //Cuento cuantos productos ya se recibieron
                int recibidos = 0;
                //Determino cuantos productos unicos hay
                int productosUnicos=transferencia.getProducts().length;
                LinkedList<ProductosTransferenciaDetail> productos = new LinkedList<>();
                for(TransfersHasZonesProduct pzt:transferencia.getProducts()){
                    if(pzt.state)
                        recibidos++;
                    //Determina si el producto ya existe en la lista o no
                    //Si no existe actualiza la cantidad
                    if(!productExists(pzt, productos)){
//                        productosUnicos++;
                        //Como el producto no existe lo creo y lo agrego a la lista
                        try {
                            ProductHasZone productosZonas =
                                    (ProductHasZone) db.findById(
                                            Constants.table_productsHasZones,
                                            pzt.getProduct().getId()+"",
                                            ProductHasZone.class);
                            if(productosZonas!=null){

                                Product producto =
                                        (Product) db.findById(
                                                Constants.table_products,
                                                productosZonas.getProduct().getId()+"",
                                                Product.class
                                        );
                                if(producto!=null){
                                    ProductosTransferenciaDetail aux = new ProductosTransferenciaDetail();
                                    aux.setEnviados(1);
                                    if(pzt.state)
                                        aux.setRecibidos(1);
                                    aux.setProducto(producto);
                                    productos.add(aux);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                transferenciaDetails.setEnviados(productosUnicos);
                transferenciaDetails.setRecibidos(recibidos);
                transferenciaDetails.setFaltantes(transferenciaDetails.getEnviados()-transferenciaDetails.getRecibidos());
                transferenciaDetails.setFecha(transferencia.getCreatedAt());
                transferenciaDetails.setDestino(transferencia.getShopDestination());
                transferenciaDetails.setGenerador(transferencia.getEmployee());
                transferenciaDetails.setMensaje(transferencia.getMessage());
                transferenciaDetails.setManifiestoElectronico(transferencia.getManifest());
                ProductosTransferenciaDetail [] auxProd = productos.toArray(new ProductosTransferenciaDetail[productos.size()]);
                transferenciaDetails.setProductos(auxProd);

                admin.callIntent(ManifiestoElectronicoDetalles.class, transferenciaDetails, TransferenciaDetails.class);


            }

            @Override
            public void onLongItemClick(Object item) {

            }

            @Override
            public void onItemClick(int view, Object item) {

            }
        });
        recyclerView.setAdapter(adapter);
        WebServices.getTranfersByType(Constants.transferencias_ingreso,this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                transferencias = (ArrayList<Transfer>) ok.getData();
                //Debo obtener el id del productoZona de cada producto
                for(Transfer tran :transferencias){
                    for(TransfersHasZonesProduct pzht:tran.getProducts()){
                        //Buso el producto
                        Product productos =
                                (Product) db.findById(
                                        Constants.table_products,
                                        pzht.getProduct().getProduct().getId()+"",
                                        Product.class);
                        Zone zonas =
                                (Zone) db.findById(
                                        Constants.table_zones,
                                        pzht.getProduct().getZone().getId()+"",
                                        Zone.class);
                        pzht.getProduct().setProduct(productos);
                        pzht.getProduct().setZone(zonas);
                    }
                }
                adapter.setTransferencias(transferencias);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void fail(ResultWebServiceFail fail) {

            }
        });

    }

    private boolean productExists(TransfersHasZonesProduct pzt, LinkedList<ProductosTransferenciaDetail> productos) {

        for(ProductosTransferenciaDetail aux: productos){
            if(pzt.getProduct().getProduct().getId() == aux.getProducto().getId()){
                //Aumento las cantidades
                aux.setEnviados(aux.getEnviados()+1);
                if(pzt.getState())
                    aux.setRecibidos(aux.getRecibidos()+1);
                return true;
            }
        }

        return false;
    }

    @Override
    public void initOnClick() {

    }

    @Override
    public void hasAllPermissions() {

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
