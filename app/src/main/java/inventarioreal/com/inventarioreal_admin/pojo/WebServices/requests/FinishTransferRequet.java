package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class FinishTransferRequet implements WebServiceRequest {
    private List<TransfersHasZonesProduct> products;

    public FinishTransferRequet(List<TransfersHasZonesProduct> products, String message) {
        this.products = products;
    }

    public HashMap<String, Object> getCampos(){

        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.products, this.getProductosZonaHasTransfenrencias());
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


    private JSONArray getProductosZonaHasTransfenrencias(){
        JSONArray array = new JSONArray();
        for (TransfersHasZonesProduct aux: products
        ) {
            if(aux.getState())
            {
                try {
                    JSONObject object = new JSONObject();
                    object.put(Constants.id, aux.getId());
                    object.put(Constants.transfer, aux.getTransfer().getId());
                    object.put(Constants.product, aux.getProduct().getId());
                    array.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
        return array;
    }

}

