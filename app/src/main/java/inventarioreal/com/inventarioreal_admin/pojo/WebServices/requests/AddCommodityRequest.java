package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class AddCommodityRequest {
    private long product;
    private List<ProductHasZone> products;

    public AddCommodityRequest(long product, List<ProductHasZone> productosZonas) {
        this.product = product;
        this.products = productosZonas;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.product, product +"");
        campos.put(Constants.products, this.getProductosZona());
        return campos;
    }

    /**
     * Se encarga de convertir los productos zonas al formato requerido por el servicio web
     * @return
     */
    private String getProductosZona(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (ProductHasZone pz: products
             ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.zone, pz.getZone().getId());
            object.addProperty(Constants.product, pz.getProduct().getId());
            object.addProperty(Constants.epc, pz.getEpc().getEpc());
            object.addProperty(Constants.devolution,1);
            object.addProperty(Constants.sell,1);
            array.add(object);

        }
        return gson.toJson(array);
    }


}
