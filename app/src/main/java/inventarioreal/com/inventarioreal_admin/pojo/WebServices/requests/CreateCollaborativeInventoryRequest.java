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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class CreateCollaborativeInventoryRequest implements WebServiceRequest {
    private Inventory inventory;
    private List<InventoryHasProduct> products;

    public CreateCollaborativeInventoryRequest(long zonas_id, String message,  List<InventoryHasProduct> products) {
        this.inventory = new Inventory();
        this.inventory.setZone(new Zone(zonas_id));
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


    private JSONObject getInventario(){
        Gson gson = new Gson();
       JSONObject object = new JSONObject();

        try {
            object.put(Constants.parcial, true);
            object.put(Constants.collaborative, true);
            object.put(Constants.zone, this.inventory.getZone().getId());
            object.put(Constants.consolidatedInventory  , 1);
            object.put(Constants.message, this.inventory.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
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

