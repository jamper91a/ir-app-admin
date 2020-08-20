package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetProductsByInventoryRequest implements WebServiceRequest {
    private long inventory;

    public GetProductsByInventoryRequest(long inventory) {
        this.inventory = inventory;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.inventory, inventory+"");
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
