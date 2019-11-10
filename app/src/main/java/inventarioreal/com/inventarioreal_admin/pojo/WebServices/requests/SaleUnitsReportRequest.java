package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class SaleUnitsReportRequest implements WebServiceRequest {
    private String firstDate;
    private String secondDate;

    public SaleUnitsReportRequest(String firstDate, String secondDate) {
        this.firstDate = firstDate;
        this.secondDate = secondDate;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.firstDate, firstDate);
        campos.put(Constants.secondDate, secondDate);
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
