package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class GetDiferenceBetweenInventoriesRequest implements WebServiceRequest {
    private long firstInventory;
    private long secondInventory;

    public GetDiferenceBetweenInventoriesRequest(long firstInventory, long secondInventory) {
        this.firstInventory = firstInventory;
        this.secondInventory = secondInventory;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.firstInventory, firstInventory+"");
        campos.put(Constants.secondInventory, secondInventory+"");
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
