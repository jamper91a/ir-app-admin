package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Sell;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class CreateSellRequest {
    private Sell sell;
    private List<ProductHasZone> products;
    private Gson gson = new Gson();

    public CreateSellRequest(Sell sell, List<ProductHasZone> products) {
        this.sell = sell;
        this.products = products;
    }

    public HashMap<String, String> getCampos(){

        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.sell, this.getSell());
        campos.put(Constants.products, this.getProducts());
        return campos;
    }


    private String getSell(){
        JsonObject object = new JsonObject();
        object.addProperty(Constants.user, sell.getUser().getId());
        return gson.toJson(object);
    }
    private String getProducts(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (ProductHasZone product: products
        ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.id, product.getId());
            object.addProperty(Constants.column_product, product.getProduct().getId());
            array.add(object);

        }
        return gson.toJson(array);
    }

}

