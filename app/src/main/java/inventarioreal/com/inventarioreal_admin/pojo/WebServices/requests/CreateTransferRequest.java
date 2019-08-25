package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class CreateTransferRequest {
    private Transfer transfer;
    private List<TransfersHasZonesProduct> products;
    private Gson gson = new Gson();

    public CreateTransferRequest(Transfer transfer, List<TransfersHasZonesProduct> products) {
        this.transfer = transfer;
        this.products = products;
    }

    public HashMap<String, String> getCampos(){

        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.transfer, this.getTransfer());
        campos.put(Constants.products, this.getProductosZonaHasTransfenrencias());
        return campos;
    }


    private String getTransfer(){
        JsonObject object = new JsonObject();
        object.addProperty(Constants.shopSource, transfer.getShopSource().getId());
        object.addProperty(Constants.shopDestination, transfer.getShopDestination().getId());
        object.addProperty(Constants.message, transfer.getMessage());
        return gson.toJson(object);
    }
    private String getProductosZonaHasTransfenrencias(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (TransfersHasZonesProduct aux: products
        ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.state, false);
            object.addProperty(Constants.product, aux.getProduct().getId());
            array.add(object);

        }
        return gson.toJson(array);
    }

}

