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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class CreateTransferRequest implements WebServiceRequest {
    private Transfer transfer;
    private List<TransfersHasZonesProduct> products;
    private Gson gson = new Gson();

    public CreateTransferRequest(Transfer transfer, List<TransfersHasZonesProduct> products) {
        this.transfer = transfer;
        this.products = products;
    }

    public HashMap<String, Object> getCampos(){

        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.transfer, this.getTransfer());
        campos.put(Constants.products, this.getProductosZonaHasTransfenrencias());
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


    private JSONObject getTransfer(){
       JSONObject object = new JSONObject();
        try {
            object.put(Constants.shopSource, transfer.getShopSource().getId());
            object.put(Constants.shopDestination, transfer.getShopDestination().getId());
            object.put(Constants.message, transfer.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    private JSONArray getProductosZonaHasTransfenrencias(){
        JSONArray array = new JSONArray();
        for (TransfersHasZonesProduct aux: products
        ) {
            try {
                JSONObject object = new JSONObject();
                object.put(Constants.state, false);
                object.put(Constants.product, aux.getProduct().getId());
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return array;
    }

}

