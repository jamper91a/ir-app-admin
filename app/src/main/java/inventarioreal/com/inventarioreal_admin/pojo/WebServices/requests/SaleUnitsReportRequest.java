package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class SaleUnitsReportRequest {
    private long firstInventory;
    private long secondInventory;

    public SaleUnitsReportRequest(long firstInventory, long secondInventory) {
        this.firstInventory = firstInventory;
        this.secondInventory = secondInventory;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.firstInventory, firstInventory+"");
        campos.put(Constants.secondInventory, secondInventory+"");
        return campos;
    }



}
