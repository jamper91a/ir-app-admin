package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ChangeStateUserRequest implements WebServiceRequest {
    private String username = "";
    private boolean active;

    public ChangeStateUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.username, this.getUsername());
        campos.put(Constants.active, this.getActive()? "1": "0");
        return campos;
    }

    public boolean validar() throws Error{

        return true;
    }

}

