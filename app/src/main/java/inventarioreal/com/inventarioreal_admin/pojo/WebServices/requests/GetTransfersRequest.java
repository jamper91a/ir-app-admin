package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetTransfersRequest {
    private long shop;

    public GetTransfersRequest(long shop) {
        this.shop = shop;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.shop, shop+"");
        return campos;
    }



}
