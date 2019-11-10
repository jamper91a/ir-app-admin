package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ReportsHasProductsZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class HomologateUnitsRequest  implements WebServiceRequest{
    private LinkedList<ReportsHasProductsZone> products;
    private Gson gson = new Gson();

    public HomologateUnitsRequest() {

    }

    public HomologateUnitsRequest(LinkedList<ReportsHasProductsZone> products) {
        this.products = products;
    }

    public HashMap<String, String> getCampos(){

        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.products, this.getProducts());
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }

    private String getProducts(){
        JsonArray array = new JsonArray();
        for (ReportsHasProductsZone product: products) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.id, product.getId());
            array.add(object);

        }
        return gson.toJson(array);


    }


}

