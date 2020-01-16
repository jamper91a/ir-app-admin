package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;


public class CreatePdfRequest implements WebServiceRequest {
    private String templateId = "";
    private String title="";
    private String logo="";
    private String header="";
    private String rows="";

    public CreatePdfRequest() {
    }


    public HashMap<String, String> getCampos(){
        return null;
    }

    public boolean validar() throws Error{

        return true;
    }

}

