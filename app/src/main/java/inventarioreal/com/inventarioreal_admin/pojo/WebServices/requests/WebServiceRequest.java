package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public interface WebServiceRequest {

    public HashMap<String, String> getCampos();
    public boolean validar() throws Error;



}
