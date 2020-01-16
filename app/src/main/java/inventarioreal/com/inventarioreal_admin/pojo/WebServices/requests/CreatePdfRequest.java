package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import jamper91.com.easyway.Util.Administrador;

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

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.username, this.getUsername());
        campos.put(Constants.active, this.getActive()? "1": "0");
        return campos;
    }

    public boolean validar() throws Error{

        return true;
    }

}

