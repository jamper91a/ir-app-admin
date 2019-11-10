package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetProductsByConsolidatedInventoryRequest implements WebServiceRequest {
    private long consolidatedInventory;

    public GetProductsByConsolidatedInventoryRequest(long consolidatedInventory) {
        this.consolidatedInventory = consolidatedInventory;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.consolidatedInventory, consolidatedInventory +"");
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
