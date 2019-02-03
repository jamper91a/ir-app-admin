package inventarioreal.com.inventarioreal_admin.util.WebServices;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultWebServiceFail {

    private String message=null;
    private JSONException jsonException=null;
    private JSONObject jsonObject= null;

    public ResultWebServiceFail() {
    }

    public ResultWebServiceFail(JSONException error) {
        this.jsonException = error;
    }

    public ResultWebServiceFail(String message) {
        this.message = message;
    }

    public ResultWebServiceFail(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getError(){
        if(message!=null){
            return message;
        }else if(jsonException!=null){
            return jsonException.getMessage();
        }else if(jsonObject!=null){
            try {
                return jsonObject.getString("code");
            } catch (JSONException e) {
                Log.e("getError",e.getMessage());
                return "No error";
            }
        }
        else{
            return "No error";
        }
    }

    public Object getObjectError(){
        if(message!=null){
            return message;
        }else if(jsonException!=null){
            return jsonException.getMessage();
        }else if(jsonObject!=null){
            return jsonObject;
        }
        else{
            return null;
        }
    }
}
