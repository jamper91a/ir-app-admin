package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ListConsolidatedInventoriesRequest implements WebServiceRequest {
    private boolean collaborative;

    public ListConsolidatedInventoriesRequest(boolean collaborative) {
        this.collaborative = collaborative;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.password, collaborative ? "1" : "0");
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
