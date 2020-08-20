package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ListInventoriesRequest implements WebServiceRequest {
    private String type;
    private boolean collaborative;

    public ListInventoriesRequest(String type, boolean collaborative) {
        this.type = type;
        this.collaborative = collaborative;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.type, type);
        campos.put(Constants.collaborative, collaborative ? "1" : "0");
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
