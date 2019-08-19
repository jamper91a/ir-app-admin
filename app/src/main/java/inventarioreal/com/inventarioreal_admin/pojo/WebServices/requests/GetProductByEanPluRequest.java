package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetProductByEanPluRequest {
    private String code;

    public GetProductByEanPluRequest(String code) {
        this.code = code;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.code, code);
        return campos;
    }



}
