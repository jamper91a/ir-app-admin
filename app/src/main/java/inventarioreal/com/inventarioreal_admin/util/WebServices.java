package inventarioreal.com.inventarioreal_admin.util;

import android.app.Activity;
import android.content.ContentValues;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.LoginResponseWebService;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceOk;
import jamper91.com.easyway.Util.Administrador;
import jamper91.com.easyway.Util.CallWebServiceJson;
import jamper91.com.easyway.Util.ResponseListener;

public class WebServices {
    private static Gson gson= new Gson();


    /**
     * Llamado a servicio web de login
     * @param username Nombre de usuario
     * @param password Contrasena
     * @param activity Acitivty desde el cual se llama
     * @param result El resultado que tendra
     */
    public static void login(String username, String password, final Activity activity, final Administrador admin,final ResultWebServiceInterface result){

        final String url=Constants.url+Constants.ws_login;


        HashMap<String, String> campos = new HashMap<String, String>();
        campos.put(Constants.username, username);
        campos.put(Constants.password, password);
        CallWebServiceJson callWebService = new CallWebServiceJson(
                activity,
                url,
                campos,
                new HashMap<String, String>(),
                jamper91.com.easyway.Util.Constants.REQUEST_POST,
                new ResponseListener() {

                    @Override
                    public void onResponse(String s) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getString("code").equals("OK")){
                                //Almaceno la información del usuario
                                try {
                                    LoginResponseWebService data=gson.fromJson(jsonObject.getJSONObject("data").toString(), LoginResponseWebService.class);
                                    admin.escribir_preferencia(Constants.empleado, gson.toJson(data.getEmpleado()));
                                    admin.escribir_preferencia(Constants.token, data.getToken());
                                    result.ok(new ResultWebServiceOk(data));
                                } catch (JsonSyntaxException e) {
                                    result.fail(new ResultWebServiceFail(e.getMessage()));
                                } catch (JSONException e) {
                                    result.fail(new ResultWebServiceFail(e));
                                } catch (Exception e){
                                    result.fail(new ResultWebServiceFail(e.getMessage()));
                                }


                            }
                        } catch (JSONException e) {
                            result.fail(new ResultWebServiceFail(e));
                        }
                    }

                    @Override
                    public void onErrorResponse(String s) {
                        /**
                         * Puede ser un error dado en formato string, o puede ser un json en
                         * formato string.
                         * Trato de converti el string a JSON, si funciona, instancio en JSON, si no
                         * instancio con el string
                         */
                        try {
                            JSONObject error = new JSONObject(s);
                            result.fail(new ResultWebServiceFail(error));
                        } catch (JSONException e) {
                            result.fail(new ResultWebServiceFail(s));
                        }

                    }
                },
                admin
        );
        callWebService.setMessage(activity.getString(R.string.sincronizando));
        callWebService.execute();
    }
}
