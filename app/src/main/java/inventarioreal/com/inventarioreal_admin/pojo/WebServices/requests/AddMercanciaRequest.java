package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

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
