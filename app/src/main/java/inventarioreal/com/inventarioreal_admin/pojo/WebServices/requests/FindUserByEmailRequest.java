package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import android.content.Context;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import jamper91.com.easyway.Util.Administrador;

public class FindUserByEmailRequest implements WebServiceRequest {
    private String email;
    private Context context;
    private Administrador admin;

    public FindUserByEmailRequest(Context context, Administrador admin) {
        this.context = context;
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.username, email);
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        if(!admin.validar_email(this.getEmail())){
            throw new Error(this.context.getString(R.string.error_email_valido));
        }
        return false;
    }


}
