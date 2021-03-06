package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ListInventoriesRequest {
    private String type;
    private boolean collaborative;

    public ListInventoriesRequest(String type, boolean collaborative) {
        this.type = type;
        this.collaborative = collaborative;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.type, type);
        campos.put(Constants.collaborative, collaborative ? "1" : "0");
        return campos;
    }



}
