package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Report;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class SaveReportRequest implements WebServiceRequest {
    private Report report;
    private ProductHasZone[] products;

    public SaveReportRequest() {

    }

    public SaveReportRequest(Report report, ProductHasZone[] products) {
        this.report = report;
        this.products = products;
    }




    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.report, getReport());
        campos.put(Constants.products, getProducts());
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }

    private JSONArray getProducts(){
        JSONArray array = new JSONArray();
        for (ProductHasZone product: products
        ) {
            try {
                JSONObject object = new JSONObject();
                object.put(Constants.product, product.getId());
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return array;
    }
    private JSONObject getReport(){
        JSONObject newReport = null;
        try {
            newReport = new JSONObject();
            if(report.getFirstInventory()!=null)
                newReport.put(Constants.firstInventory, report.getFirstInventory().getId());
            else
                newReport.put(Constants.firstInventory, JSONObject.NULL);
            if(report.getSecondInventory()!=null)
                newReport.put(Constants.secondInventory, report.getSecondInventory().getId());
            else
                newReport.put(Constants.secondInventory, JSONObject.NULL);
            newReport.put(Constants.type, report.getType());
            newReport.put(Constants.column_amount, report.getAmount());
            newReport.put(Constants.column_unitsSell, report.getUnitsSell());
            newReport.put(Constants.column_unitsReturned, report.getUnitsReturned());
            newReport.put(Constants.firstDate, report.getFirstDate());
            newReport.put(Constants.secondDate, report.getSecondDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newReport;
    }


}


