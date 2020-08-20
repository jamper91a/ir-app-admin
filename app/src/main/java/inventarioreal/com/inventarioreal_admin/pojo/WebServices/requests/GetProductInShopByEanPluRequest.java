package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetProductInShopByEanPluRequest implements WebServiceRequest {
    private long product;

    public GetProductInShopByEanPluRequest(long product) {
        this.product = product;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.product, product+"");
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
