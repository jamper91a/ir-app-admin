package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Sell;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetReportsByTypeRequest  implements WebServiceRequest{
    private String type;
    private Gson gson = new Gson();

    public GetReportsByTypeRequest(String type) {
        this.type = type;
    }

    public HashMap<String, String> getCampos(){

        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.type, this.type);
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}

