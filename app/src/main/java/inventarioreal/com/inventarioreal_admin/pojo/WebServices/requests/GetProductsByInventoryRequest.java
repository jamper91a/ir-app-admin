package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetProductsByInventoryRequest {
    private long inventory;

    public GetProductsByInventoryRequest(long inventory) {
        this.inventory = inventory;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.inventory, inventory+"");
        return campos;
    }



}
