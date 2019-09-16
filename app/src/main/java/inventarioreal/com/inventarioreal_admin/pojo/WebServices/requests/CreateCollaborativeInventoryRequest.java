package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventoryHasProduct;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class CreateCollaborativeInventoryRequest {
    private Inventory inventory;
    private List<InventoryHasProduct> products;

    public CreateCollaborativeInventoryRequest(long zonas_id, String message,  List<InventoryHasProduct> products) {
        this.inventory = new Inventory();
        this.inventory.setZone(new Zone(zonas_id));
        this.inventory.setMessage(message);
        this.products = products;
    }

    public HashMap<String, String> getCampos(){

        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.inventory, this.getInventario());
        campos.put(Constants.products, this.getInventarioProductos());
        return campos;
    }


    private String getInventario(){
        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        object.addProperty(Constants.parcial, true);
        object.addProperty(Constants.collaborative, true);
        object.addProperty(Constants.zone, this.inventory.getZone().getId());
        object.addProperty(Constants.consolidatedInventory  , 1);
        object.addProperty(Constants.message, this.inventory.getMessage());
        return gson.toJson(object);
    }

    private String getInventarioProductos(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (InventoryHasProduct iv: products
        ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.zone, iv.getZone().getId());
            object.addProperty(Constants.product, iv.getProduct().getId());
            object.addProperty(Constants.epc, iv.getEpc().getId());
            array.add(object);

        }
        return gson.toJson(array);
    }

}

