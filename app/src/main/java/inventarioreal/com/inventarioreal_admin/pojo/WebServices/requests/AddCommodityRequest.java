package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class AddCommodityRequest implements WebServiceRequest {
    private long product;
    private List<ProductHasZone> products;

    public AddCommodityRequest(long product, List<ProductHasZone> productosZonas) {
        this.product = product;
        this.products = productosZonas;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.product, product);
        campos.put(Constants.products, this.getProductosZona());
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }

    /**
     * Se encarga de convertir los productos zonas al formato requerido por el servicio web
     * @return
     */
    private JSONArray getProductosZona(){
        Gson gson = new Gson();
        JSONArray array = new JSONArray();
        for (ProductHasZone pz: products
             ) {
            JSONObject object = new JSONObject();
            try {
                object.put(Constants.zone, pz.getZone().getId());
                object.put(Constants.product, pz.getProduct().getId());
                object.put(Constants.epc, pz.getEpc().getEpc());
                object.put(Constants.devolution,1);
                object.put(Constants.sell,1);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return array;
    }


}
