package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

public interface WebServiceRequest {

    public HashMap<String, Object> getCampos();
    public boolean validar() throws Error;



}
