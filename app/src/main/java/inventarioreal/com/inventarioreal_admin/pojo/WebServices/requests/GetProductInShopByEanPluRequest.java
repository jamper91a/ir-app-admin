package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetProductInShopByEanPluRequest {
    private long product;

    public GetProductInShopByEanPluRequest(long product) {
        this.product = product;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.product, product+"");
        return campos;
    }



}
