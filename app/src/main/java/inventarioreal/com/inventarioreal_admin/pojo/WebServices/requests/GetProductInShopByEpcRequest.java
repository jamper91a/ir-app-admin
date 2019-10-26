package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetProductInShopByEpcRequest {
    private String epc;

    public GetProductInShopByEpcRequest(String epc) {
        this.epc = epc;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.epc, epc+"");
        return campos;
    }



}
