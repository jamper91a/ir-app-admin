package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetReportByIdRequest implements WebServiceRequest {
    private long id;
    private Gson gson = new Gson();

    public GetReportByIdRequest(long id) {
        this.id = id;
    }

    public HashMap<String, Object> getCampos(){

        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.id, this.id+"");
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}

