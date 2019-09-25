package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class FinishTransferRequet {
    private List<TransfersHasZonesProduct> products;

    public FinishTransferRequet(List<TransfersHasZonesProduct> products, String message) {
        this.products = products;
    }

    public HashMap<String, String> getCampos(){

        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.products, this.getProductosZonaHasTransfenrencias());
        return campos;
    }



    private String getProductosZonaHasTransfenrencias(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (TransfersHasZonesProduct aux: products
        ) {
            if(aux.getState())
            {
                JsonObject object = new JsonObject();
                object.addProperty(Constants.id, aux.getId());
                object.addProperty(Constants.transfer, aux.getTransfer().getId());
                object.addProperty(Constants.product, aux.getProduct().getId());
                array.add(object);
            }


        }
        return gson.toJson(array);
    }

}

