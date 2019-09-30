package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetReportByIdRequest {
    private long id;
    private Gson gson = new Gson();

    public GetReportByIdRequest(long id) {
        this.id = id;
    }

    public HashMap<String, String> getCampos(){

        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.id, this.id+"");
        return campos;
    }


}

