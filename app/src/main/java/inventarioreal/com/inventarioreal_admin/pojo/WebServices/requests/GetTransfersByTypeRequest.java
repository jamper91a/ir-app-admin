package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetTransfersByTypeRequest {
    private long shopSource;
    private String type;

    public GetTransfersByTypeRequest(long shopSource, String type) {
        this.shopSource = shopSource;
        this.type = type;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.shopSource, shopSource+"");
        campos.put(Constants.type, type);
        return campos;
    }



}
