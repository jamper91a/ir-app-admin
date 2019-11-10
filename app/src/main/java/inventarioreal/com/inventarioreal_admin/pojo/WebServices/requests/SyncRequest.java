package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class SyncRequest implements WebServiceRequest {
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
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.last_updated, getLast_update());
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}


