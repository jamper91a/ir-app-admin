package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.util.Constants;


public class CreatePdfTotalnventoryRequest extends CreatePdfRequest implements WebServiceRequest  {
    private String shop;
    private Activity activity;

    public CreatePdfTotalnventoryRequest(Activity activity) {
        super();
        this.activity = activity;
        this.setTemplateId("63425");
    }

    public void addRows(LinkedList<ProductHasZone> products){
        JsonArray rows = new JsonArray();
        //Add header of columns

        JsonObject headers = new JsonObject();
        headers.addProperty(Constants.total, activity.getString(R.string.total));
        headers.addProperty(Constants.column_ean, activity.getString(R.string.eanPlu));
        headers.addProperty(Constants.column_description, activity.getString(R.string.description));
        rows.add(headers);
        for (ProductHasZone product : products) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.total, product.getTotal());
            object.addProperty(Constants.column_ean, product.getProduct().getEan());
            object.addProperty(Constants.column_description, product.getProduct().getDescription());
            rows.add(object);
        }
        super.setRows(rows);
    }


    public HashMap<String, String> getCampos(){
        Gson gson = new Gson();
        //Get the jsob Data and attach the shop
        JsonObject data = this.getData();
        data.addProperty(Constants.shop, this.getShop());

        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.templateId, this.getTemplateId());
        campos.put(Constants.data, gson.toJson(data));
        if(!this.getTo().isEmpty()) campos.put(Constants.to, this.getTo());
        return campos;
    }

    public boolean validar() throws Error{

        return true;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }
}

