package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ReportsHasProductsZone;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class HomologateUnitsRequest  implements WebServiceRequest{
    private LinkedList<ReportsHasProductsZone> products;
    private Gson gson = new Gson();

    public HomologateUnitsRequest() {

    }

    public HomologateUnitsRequest(LinkedList<ReportsHasProductsZone> products) {
        this.products = products;
    }

    public HashMap<String, Object> getCampos(){

        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.products, this.getProducts());
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }

    private JSONArray getProducts(){
        JSONArray array = new JSONArray();
        for (ReportsHasProductsZone product: products) {
            try {
                JSONObject object = new JSONObject();
                object.put(Constants.id, product.getId());
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return array;


    }


}

