package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetProductInShopByEpcRequest implements WebServiceRequest {
    private String epc;

    public GetProductInShopByEpcRequest(String epc) {
        this.epc = epc;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.epc, epc+"");
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
