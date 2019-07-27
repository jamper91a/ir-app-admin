package inventarioreal.com.inventarioreal_admin.pages.Transferencias.ManifiestoElectronico;

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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Productos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonasHasTransferencias;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transferencias;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;
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
    private ArrayList<Transferencias> transferencias= new ArrayList<>();
    private RecyclerView recyclerView = null;
    private final DataBase db = DataBase.getInstance(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_manifiesto_electronico_ingresos);
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
                Transferencias transferencia = (Transferencias) item;
                //Convierto la transferencia a transferenciadetails
                TransferenciaDetails transferenciaDetails = new TransferenciaDetails();

                //Cuento cuantos productos ya se recibieron
                int recibidos = 0;
                //Determino cuantos productos unicos hay
                int productosUnicos=0;
                LinkedList<ProductosTransferenciaDetail> productos = new LinkedList<>();
                for(ProductosZonasHasTransferencias pzt:transferencia.getProductos()){
                    if(pzt.estado)
                        recibidos++;
                    //Determina si el producto ya existe en la lista o no
                    //Si no existe actualiza la cantidad
                    if(!productExists(pzt, productos)){
                        productosUnicos++;
                        //Como el producto no existe lo creo y lo agrego a la lista
                        try {
                            ProductosZonas productosZonas =
                                    (ProductosZonas) db.findById(
                                            Constants.table_productos_zonas,
                                            pzt.getProductos_zona_id().getId()+"",
                                            ProductosZonas.class);
                            if(productosZonas!=null){

                                Productos producto =
                                        (Productos) db.findById(
                                                Constants.table_productos,
                                                productosZonas.getProductos_id().getId()+"",
                                                Productos.class
                                        );
                                if(producto!=null){
                                    ProductosTransferenciaDetail aux = new ProductosTransferenciaDetail();
                                    aux.setEnviados(1);
                                    if(pzt.estado)
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
                transferenciaDetails.setDestino(transferencia.getLocal_destino_id());
                transferenciaDetails.setGenerador(transferencia.getCreador_id());
                transferenciaDetails.setMensaje(transferencia.getMensaje());
                transferenciaDetails.setManifiestoElectronico(transferencia.getManifiesto());
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
        WebServices.getTransferenciasByTipo(Constants.transferencias_ingreso,this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                transferencias = (ArrayList<Transferencias>) ok.getData();
                //Debo obtener el id del productoZona de cada producto
                for(Transferencias tran :transferencias){
                    for(ProductosZonasHasTransferencias pzht:tran.getProductos()){
                        //Buso el producto
                        Productos productos =
                                (Productos) db.findById(
                                        Constants.table_productos,
                                        pzht.getProductos_zona_id().getProductos_id().getId()+"",
                                        Productos.class);
                        Zonas zonas =
                                (Zonas) db.findById(
                                        Constants.table_zonas,
                                        pzht.getProductos_zona_id().getZonas_id().getId()+"",
                                        Zonas.class);
                        pzht.getProductos_zona_id().setProductos_id(productos);
                        pzht.getProductos_zona_id().setZonas_id(zonas);
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

    private boolean productExists(ProductosZonasHasTransferencias pzt, LinkedList<ProductosTransferenciaDetail> productos) {

        for(ProductosTransferenciaDetail aux: productos){
            if(pzt.getProductos_zona_id().getProductos_id().getId() == aux.getProducto().getId()){
                //Aumento las cantidades
                aux.setEnviados(aux.getEnviados()+1);
                if(pzt.getEstado())
                    aux.setRecibidos(aux.getRecibidos()+1);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
}
