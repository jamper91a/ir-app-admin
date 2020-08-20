package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetReportsByTypeRequest  implements WebServiceRequest{
    private String type;
    private Gson gson = new Gson();

    public GetReportsByTypeRequest(String type) {
        this.type = type;
    }

    public HashMap<String, Object> getCampos(){

        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.type, this.type);
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}

