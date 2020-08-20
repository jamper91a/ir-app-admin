package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class CreatePdfRequest implements WebServiceRequest  {
    private String templateId = "";
    private String title="";
    private String logo="";
    private String header="";
    private String to="";
    private JSONArray rows;

    public CreatePdfRequest() {
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public JSONArray getRows() {
        return rows;
    }

    public void setRows(JSONArray rows) {
        this.rows = rows;
    }

    public JSONObject getData(){
        JSONObject data = new JSONObject();
        try {
            data.put(Constants.title, getTitle());
            data.put(Constants.rows, getRows());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public HashMap<String, Object> getCampos() {
        return null;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }
}

