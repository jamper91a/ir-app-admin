package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetZonesByShopRequest implements WebServiceRequest {

    public GetZonesByShopRequest() {
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
