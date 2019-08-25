package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventoryHasProduct;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class AttachInventoryRequest {
    private Inventory inventory;
    private List<InventoryHasProduct> products;

    public AttachInventoryRequest(Inventory inventory, List<InventoryHasProduct> products) {
        this.inventory = inventory;
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
        return gson.toJson(inventory);
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

