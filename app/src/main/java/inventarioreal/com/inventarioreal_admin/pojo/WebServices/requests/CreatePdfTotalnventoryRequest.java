package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        JSONArray rows = new JSONArray();
        //Add header of columns

        JSONObject headers = new JSONObject();
        try {
            headers.put(Constants.total, activity.getString(R.string.total));
            headers.put(Constants.column_ean, activity.getString(R.string.eanPlu));
            headers.put(Constants.column_description, activity.getString(R.string.description));
            rows.put(headers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (ProductHasZone product : products) {
           JSONObject object = new JSONObject();
            try {
                object.put(Constants.total, product.getTotal());
                object.put(Constants.column_ean, product.getProduct().getEan());
                object.put(Constants.column_description, product.getProduct().getDescription());
                rows.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.setRows(rows);
    }


    public HashMap<String, Object> getCampos(){
        Gson gson = new Gson();
        //Get the jsob Data and attach the shop
        JSONObject data = this.getData();
        try {
            data.put(Constants.shop, this.getShop());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, Object> campos = new HashMap<>();
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

