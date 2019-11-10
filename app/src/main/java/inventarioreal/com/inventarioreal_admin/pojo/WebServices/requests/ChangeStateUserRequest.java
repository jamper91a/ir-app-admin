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
    private int active = 0;

    public ChangeStateUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.username, this.getUsername());
        campos.put(Constants.active, this.getActive()+"");
        return campos;
    }

    public boolean validar() throws Error{

        return true;
    }

}

