package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

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


    private JSONObject getInventario(){
        JSONObject object = new JSONObject();
        try {
            object.put(Constants.id, inventory.getId());
            object.put(Constants.parcial, inventory.getParcial());
            object.put(Constants.collaborative, inventory.getCollaborative());
            object.put(Constants.zone, inventory.getZone().getId());
            object.put(Constants.consolidatedInventory, inventory.getConsolidatedInventory().getId());
            object.put(Constants.message, inventory.getMessage());
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

