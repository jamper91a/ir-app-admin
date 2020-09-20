package inventarioreal.com.inventarioreal_admin.util.WebServices;

import org.json.JSONObject;

public class ResultSocketOk {

    private Object body;
    private JSONObject headers;
    private Integer statusCode;

    public ResultSocketOk() {
    }

    public ResultSocketOk(Object body) {
        this.body = body;
    }

    public ResultSocketOk(Object body, JSONObject headers) {
        this.body = body;
        this.headers = headers;
    }

    public ResultSocketOk(Object body, JSONObject headers, Integer statusCode) {
        this.body = body;
        this.headers = headers;
        this.statusCode = statusCode;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public JSONObject getHeaders() {
        return headers;
    }

    public void setHeaders(JSONObject headers) {
        this.headers = headers;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
