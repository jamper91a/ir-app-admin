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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Sell;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class CreateSellRequest implements WebServiceRequest {
    private Sell sell;
    private List<ProductHasZone> products;
    private Gson gson = new Gson();

    public CreateSellRequest(Sell sell, List<ProductHasZone> products) {
        this.sell = sell;
        this.products = products;
    }

    public HashMap<String, Object> getCampos(){

        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.sell, this.getSell());
        campos.put(Constants.products, this.getProducts());
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


    private JSONObject getSell(){
        JSONObject object = new JSONObject();
        try {
            object.put(Constants.user, sell.getUser().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    private JSONArray getProducts(){
        JSONArray array = new JSONArray();
        for (ProductHasZone product: products
        ) {
            try {
                JSONObject object = new JSONObject();
                object.put(Constants.id, product.getId());
                object.put(Constants.column_product, product.getProduct().getId());
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return array;
    }

}

