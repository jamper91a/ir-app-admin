package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

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




    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.report, getReport());
        campos.put(Constants.products, getProducts());
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }

    private String getProducts(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (ProductHasZone product: products
        ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.product, product.getId());
            array.add(object);

        }
        return gson.toJson(array);
    }
    private String getReport(){
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        JsonObject newReport = new JsonObject();
        if(report.getFirstInventory()!=null)
            newReport.addProperty(Constants.firstInventory, report.getFirstInventory().getId());
        else
            newReport.add(Constants.firstInventory, JsonNull.INSTANCE);
        if(report.getSecondInventory()!=null)
            newReport.addProperty(Constants.secondInventory, report.getSecondInventory().getId());
        else
            newReport.add(Constants.secondInventory, JsonNull.INSTANCE);
        newReport.addProperty(Constants.type, report.getType());
        newReport.addProperty(Constants.column_amount, report.getAmount());
        newReport.addProperty(Constants.column_unitsSell, report.getUnitsSell());
        newReport.addProperty(Constants.column_unitsReturned, report.getUnitsReturned());
        newReport.addProperty(Constants.firstDate, report.getFirstDate());
        newReport.addProperty(Constants.secondDate, report.getSecondDate());
        return gson.toJson(newReport);
    }


}


