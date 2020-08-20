package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class SyncRequest implements WebServiceRequest {
    private String last_update;
    private int page = 0;

    public SyncRequest() {

    }

    public SyncRequest(String last_update, int page) {
        this.last_update = last_update;
        this.page = page;
    }

    public String getLast_update() {
        return last_update;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.last_updated, getLast_update());
        campos.put(Constants.page, getPage()+"");
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}


