package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetTransfersRequest implements WebServiceRequest {
    private long shop;

    public GetTransfersRequest(long shop) {
        this.shop = shop;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.shop, shop+"");
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
