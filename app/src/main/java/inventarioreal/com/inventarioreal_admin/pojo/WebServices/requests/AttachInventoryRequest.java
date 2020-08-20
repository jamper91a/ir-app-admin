package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventoryHasProduct;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class AttachInventoryRequest implements WebServiceRequest {
    private Inventory inventory;
    private List<InventoryHasProduct> products;

    public AttachInventoryRequest(Inventory inventory, String message, List<InventoryHasProduct> products) {
        this.inventory = inventory;
        this.inventory.setMessage(message);
        this.products = products;
    }


    public HashMap<String, Object> getCampos(){

        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.inventory, this.getInventario());
        campos.put(Constants.products, this.getInventarioProductos());
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


    private String getInventario(){
        Gson gson = new Gson();
        return gson.toJson(inventory);
    }

    private JSONArray getInventarioProductos(){
        JSONArray array = new JSONArray();
        for (InventoryHasProduct iv: products
        ) {
            try {
            JSONObject object = new JSONObject();
            object.put(Constants.zone, iv.getZone().getId());
            object.put(Constants.product, iv.getProduct().getId());
            object.put(Constants.epc, iv.getEpc().getId());
            array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return array;
    }

}

