package inventarioreal.com.inventarioreal_admin.util.WebServiceResult;

import org.json.JSONObject;

public interface ResultWebServiceInterface {

    public void ok( ResultWebServiceOk ok);
    public void fail(ResultWebServiceFail fail);
}
