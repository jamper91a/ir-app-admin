package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.WebServices.RequestWebServiceInterface;

public class SyncRequest implements RequestWebServiceInterface {
    private String last_update;

    public SyncRequest() {

    }

    public SyncRequest(String last_update) {
        this.last_update = last_update;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public HashMap<String, String> getCampos(){
        Gson gson = new Gson();
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.last_updated, getLast_update());
        return campos;
    }


}


