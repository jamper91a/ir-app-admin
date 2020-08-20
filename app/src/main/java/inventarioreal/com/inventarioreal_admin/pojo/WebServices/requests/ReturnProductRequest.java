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
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ReturnProductRequest  implements WebServiceRequest{
    private List<ProductHasZone> products;

    public ReturnProductRequest(List<ProductHasZone> products) {
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
        for (ProductHasZone pz: products
        ) {
            try {
                JSONObject object = new JSONObject();
                object.put(Constants.id, pz.getId());
                object.put(Constants.zone, pz.getZone().getId());
                object.put(Constants.epc, pz.getEpc().getId());
                object.put(Constants.devolution, pz.getDevolution().getId());
                object.put(Constants.notes_return, pz.getNotes_return());
                object.put(Constants.product, pz.getProduct().getId());
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return array;
    }

}

