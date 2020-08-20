package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class RotationProyectedReportRequest  implements WebServiceRequest{
    private int days;
    private String product_id;

    public RotationProyectedReportRequest() {
    }

    public RotationProyectedReportRequest(int days, String product_id) {
        this.days = days;
        this.product_id = product_id;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.days, this.days+"");
        campos.put(Constants.product_id, this.product_id);
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }


}
