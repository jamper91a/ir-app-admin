package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import android.app.Activity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.ProductosTransferenciaDetail;
import inventarioreal.com.inventarioreal_admin.util.Constants;


public class CreatePdfElectronicManifestRequest extends CreatePdfRequest implements WebServiceRequest  {
    private String amountSentTitle;
    private int amountSent;
    private String amountReceivedTitle;
    private int amountReceived;
    private String missingTitle;
    private int amountMissing;
    private String destinationTitle;
    private String destination;
    private String source;
    private JSONArray headers;
    private Activity activity;

    public CreatePdfElectronicManifestRequest(Activity activity) {
        super();
        this.activity = activity;
        this.setTemplateId("133097");
        this.amountSentTitle =activity.getString(R.string.enviados);
        this.amountReceivedTitle =activity.getString(R.string.recibidos);
        this.missingTitle =activity.getString(R.string.faltantes);
        this.destinationTitle =activity.getString(R.string.local_destino);
    }

    public void addHeaders(String sent, String received, String ean, String description){
        JSONArray  header= new JSONArray();
            JSONObject object = new JSONObject();
            try {
                object.put(Constants.col1, sent);
                object.put(Constants.col2, received);
                object.put(Constants.col3, ean);
                object.put(Constants.col4, description);
                header.put(object);
                this.headers = header;
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    public void addRows(ProductosTransferenciaDetail [] products){
        JSONArray rows = new JSONArray();

        for (ProductosTransferenciaDetail product : products) {
           JSONObject object = new JSONObject();
            try {
                object.put(Constants.sent, product.getEnviados());
                object.put(Constants.received, product.getRecibidos());
                object.put(Constants.ean, product.getProducto().getEan());
                object.put(Constants.description, product.getProducto().getDescription());
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
            data.put(Constants.title, this.getTitle());
            data.put(Constants.header, this.getHeaders());
            data.put(Constants.rows, this.getRows());
            data.put(Constants.amountSentTitle, this.getAmountSentTitle());
            data.put(Constants.amountSent, this.getAmountSent());
            data.put(Constants.amountReceivedTitle, this.getAmountReceivedTitle());
            data.put(Constants.amountReceived, this.getAmountReceived());
            data.put(Constants.missingTitle, this.getMissingTitle());
            data.put(Constants.amountMissing, this.getAmountMissing());
            data.put(Constants.destinationTitle, this.getDestinationTitle());
            data.put(Constants.destination, this.getDestination());
            data.put(Constants.source, this.getSource());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.templateId, this.getTemplateId());
        campos.put(Constants.data, data);
        if(!this.getTo().isEmpty()) campos.put(Constants.to, this.getTo());
        return campos;
    }

    public String getAmountSentTitle() {
        return amountSentTitle;
    }


    public int getAmountSent() {
        return amountSent;
    }

    public void setAmountSent(int amountSent) {
        this.amountSent = amountSent;
    }

    public String getAmountReceivedTitle() {
        return amountReceivedTitle;
    }


    public int getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(int amountReceived) {
        this.amountReceived = amountReceived;
    }

    public String getMissingTitle() {
        return missingTitle;
    }


    public int getAmountMissing() {
        return amountMissing;
    }

    public void setAmountMissing(int amountMissing) {
        this.amountMissing = amountMissing;
    }

    public String getDestinationTitle() {
        return destinationTitle;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public JSONArray getHeaders() {
        return headers;
    }

    public boolean validar() throws Error{

        return true;
    }

}

