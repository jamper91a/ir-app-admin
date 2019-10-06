package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class RotationUnitsReportRequest {
    private String firstDate;
    private String secondDate;

    public RotationUnitsReportRequest(String firstDate, String secondDate) {
        this.firstDate = firstDate;
        this.secondDate = secondDate;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.firstDate, firstDate);
        campos.put(Constants.secondDate, secondDate);
        return campos;
    }



}