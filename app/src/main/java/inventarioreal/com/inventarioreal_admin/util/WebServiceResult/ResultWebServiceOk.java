package inventarioreal.com.inventarioreal_admin.util.WebServiceResult;

import org.json.JSONObject;

public class ResultWebServiceOk {

    private Object data;
    private JSONObject ok;

    public ResultWebServiceOk() {
    }

    public ResultWebServiceOk(Object data) {
        this.data = data;
    }

    public ResultWebServiceOk(JSONObject ok) {
        this.ok = ok;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JSONObject getOk() {
        return ok;
    }

    public void setOk(JSONObject ok) {
        this.ok = ok;
    }
}
