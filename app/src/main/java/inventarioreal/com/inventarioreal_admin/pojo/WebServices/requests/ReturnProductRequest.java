package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ReturnProductRequest {
    private List<ProductHasZone> products;

    public ReturnProductRequest(List<ProductHasZone> products) {
        this.products = products;
    }


    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.products, this.getProducts());
        return campos;
    }


    private String getProducts(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (ProductHasZone pz: products
        ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.id, pz.getId());
            object.addProperty(Constants.zone, pz.getZone().getId());
            object.addProperty(Constants.epc, pz.getEpc().getId());
            object.addProperty(Constants.devolution, pz.getDevolution().getId());
            object.addProperty(Constants.notes_return, pz.getNotes_return());
            object.addProperty(Constants.product, pz.getProduct().getId());
            array.add(object);

        }
        return gson.toJson(array);
    }

}

