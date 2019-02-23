package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epcs;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class AddMercanciaRequest {
    private long productos_id;
    private List<ProductosZonas> productos_zona;

    public AddMercanciaRequest(long productos_id, List<ProductosZonas> productosZonas) {
        this.productos_id = productos_id;
        this.productos_zona = productosZonas;
    }

    public HashMap<String, String> getCampos(){
        Gson gson = new Gson();
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.products_id, productos_id +"");
        campos.put(Constants.productos_zona, this.getProductosZona());
        return campos;
    }

    /**
     * Se encarga de convertir los productos zonas al formato requerido por el servicio web
     * @return
     */
    private String getProductosZona(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (ProductosZonas pz: productos_zona
             ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.zonas_id, pz.getZonas_id().getId());
            object.addProperty(Constants.productos_id, pz.getProductos_id().getId());
            object.addProperty(Constants.ecps_id, pz.getEpcs_id().getEpc());
            object.addProperty(Constants.devoluciones_id,1);
            object.addProperty(Constants.ventas_id,1);
            array.add(object);

        }
        return gson.toJson(array);
    }


}

//class ProductosZonaHere{
//    private long zonas_id;
//    private long productos_id;
//    private String ecps_id;
//    private long devoluciones;
//    private long ventas_id;
//
//    public ProductosZonaHere() {
//    }
//
//    public ProductosZonaHere(long zonas_id, long productos_id, String ecps_id) {
//        this.zonas_id = zonas_id;
//        this.productos_id = productos_id;
//        this.ecps_id = ecps_id;
//        this.ventas_id = 1;
//    }
//
//    public ProductosZonaHere(long zonas_id, long productos_id, String ecps_id, long devoluciones, long ventas_id) {
//        this.zonas_id = zonas_id;
//        this.productos_id = productos_id;
//        this.ecps_id = ecps_id;
//        this.devoluciones = devoluciones;
//        this.ventas_id = ventas_id;
//    }
//}
