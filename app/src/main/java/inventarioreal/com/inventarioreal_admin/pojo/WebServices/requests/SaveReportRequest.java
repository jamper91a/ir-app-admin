package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Report;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.WebServices.RequestWebServiceInterface;

public class SaveReportRequest implements RequestWebServiceInterface {
    private Report report;

    public SaveReportRequest() {

    }

    public SaveReportRequest(Report report) {
        this.report = report;
    }




    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.report, getReport());
        campos.put(Constants.products, getProducts());
        return campos;
    }

    private String getProducts(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (ProductHasZone product: report.getProducts()
        ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.product, product.getId());
            array.add(object);

        }
        return gson.toJson(array);
    }
    private String getReport(){
        Gson gson = new Gson();
        JsonObject newReport = new JsonObject();
        newReport.addProperty(Constants.firstInventory, report.getFirstInventory().getId());
        newReport.addProperty(Constants.secondInventory, report.getSecondInventory().getId());
        newReport.addProperty(Constants.type, report.getType());
        newReport.addProperty(Constants.column_amount, report.getAmount());
        newReport.addProperty(Constants.column_unitsSell, report.getUnitsSell());
        newReport.addProperty(Constants.column_unitsReturned, report.getUnitsReturned());
        return gson.toJson(newReport);
    }


}


