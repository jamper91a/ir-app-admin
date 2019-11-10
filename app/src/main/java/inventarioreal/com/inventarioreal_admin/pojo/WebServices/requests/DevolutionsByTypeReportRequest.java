package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class DevolutionsByTypeReportRequest implements WebServiceRequest {
    private final String firstDate;
    private final String secondDate;
    private final int type;

    public DevolutionsByTypeReportRequest(String firstDate, String secondDate, int type) {
        this.firstDate = firstDate;
        this.secondDate = secondDate;
        this.type = type;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.firstDate, firstDate);
        campos.put(Constants.secondDate, secondDate);
        campos.put(Constants.type, type+"");
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
