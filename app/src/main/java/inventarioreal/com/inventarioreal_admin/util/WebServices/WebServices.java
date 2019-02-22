package inventarioreal.com.inventarioreal_admin.util.WebServices;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation.Producto;
import inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.AddMercanciaResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.SyncResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.ZonasListarResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.AddMercanciaRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.SyncRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation.Zona;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import jamper91.com.easyway.Util.Administrador;
import jamper91.com.easyway.Util.CallWebServiceJson;
import jamper91.com.easyway.Util.ResponseListener;

public class WebServices {
    private static Gson gson= new Gson();
    private static String TAG="WebServices";


    /**
     * Se encarga de colocar los headers a la solicitud, autenticacion en este caso
     * @param admin
     * @return
     */
    private static HashMap<String, String> getHeaders(Administrador admin){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(Constants.authorization, "Bearer  "+admin.obtener_preferencia(Constants.token));
        return  headers;
    }

    private static void errorWebService(String s, ResultWebServiceInterface result){
        try {
            JSONObject error = new JSONObject(s);
            result.fail(new ResultWebServiceFail(error));
        } catch (JSONException e) {
            result.fail(new ResultWebServiceFail(s));
        }
    }

    private static void executeObtener(Activity activity, CallWebServiceJson callWebServiceJson){
        callWebServiceJson.setMessage(activity.getString(R.string.consultando));
        callWebServiceJson.execute();
    }

    private static void executeEnviar(Activity activity, CallWebServiceJson callWebServiceJson){
        callWebServiceJson.setMessage(activity.getString(R.string.enviando_informacion));
        callWebServiceJson.execute();
    }

    /**
     * Llamado a servicio web de login
     * @param username Nombre de usuario
     * @param password Contrasena
     * @param activity Acitivty desde el cual se llama
     * @param result El resultado que tendra
     */
    public static void login(String username, String password, final Activity activity, final Administrador admin,final ResultWebServiceInterface result){

        final String url=Constants.url+Constants.ws_login;


        HashMap<String, String> campos = new HashMap<>();
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
                                    LoginResponse data=gson.fromJson(jsonObject.getJSONObject("data").toString(), LoginResponse.class);
                                    admin.escribir_preferencia(Constants.user, gson.toJson(data));
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
                    /**
                     * Puede ser un error dado en formato string, o puede ser un json en
                     * formato string.
                     * Trato de converti el string a JSON, si funciona, instancio en JSON, si no
                     * instancio con el string
                     */
                    @Override
                    public void onErrorResponse(String s) {
                        errorWebService(s, result);
                    }
                },
                admin
        );
        callWebService.setMessage(activity.getString(R.string.ingresando));
        callWebService.execute();
    }

    /**
     * Servicio web para consultarun producto por codigo
     * @param code Codigo a consultar
     * @param activity Actividad desde el cual se llama
     * @param admin
     * @param result Callback
     */
    public static void getProductByEanPlu(String code, final Activity activity, final Administrador admin,final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_getProductByEanPLu;


        try {

            HashMap<String, String> campos = new HashMap<>();
            campos.put(Constants.codigo, code);

            CallWebServiceJson callWebServiceJson = new CallWebServiceJson(
                    activity,
                    url,
                    campos,
                    getHeaders(admin),
                    jamper91.com.easyway.Util.Constants.REQUEST_POST,
                    new ResponseListener() {
                        @Override
                        public void onResponse(String s) {
                            //result.ok(new ResultWebServiceOk());
                        }

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                Producto producto = gson.fromJson(jsonObject.getJSONObject("data").toString(),Producto.class);
                                result.ok(new ResultWebServiceOk(producto));
                            } catch (JSONException e) {
                                result.fail(new ResultWebServiceFail(e));
                            } catch (Exception e) {
                                result.fail(new ResultWebServiceFail(e.getMessage()));
                        }
                        }

                        @Override
                        public void onErrorResponse(String s) {
                            errorWebService(s, result);
                        }
                    },
                    admin
            );
            executeObtener(activity, callWebServiceJson);
        } catch (Exception e){
            result.fail(new ResultWebServiceFail(e.getMessage()));
        }


    }

    public static void addMercancia(long products_id, long zonas_id, List<Epc> epcs, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_addMercancia;

        AddMercanciaRequest request = new AddMercanciaRequest(products_id, zonas_id, epcs);

        CallWebServiceJson callWebServiceJson = new CallWebServiceJson(
                activity,
                url,
                request.getCampos(),
                getHeaders(admin),
                jamper91.com.easyway.Util.Constants.REQUEST_POST,
                new ResponseListener() {
                    @Override
                    public void onResponse(String s) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            AddMercanciaResponse response = gson.fromJson(jsonObject.getJSONObject("data").toString(),AddMercanciaResponse.class);
                            result.ok(new ResultWebServiceOk(response));
                        } catch (JSONException e) {
                            result.fail(new ResultWebServiceFail(e));
                        } catch (Exception e) {
                            result.fail(new ResultWebServiceFail(e.getMessage()));
                        }
                    }

                    @Override
                    public void onErrorResponse(String s) {
                        result.fail(new ResultWebServiceFail(s));
                    }
                },
                admin
        );
        executeEnviar(activity, callWebServiceJson);
    }

    public static void listarZonas(final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.was_listarzonas;
        CallWebServiceJson callWebServiceJson = new CallWebServiceJson(
                activity,
                url,
                new HashMap<String, String>(),
                getHeaders(admin),
                jamper91.com.easyway.Util.Constants.REQUEST_GET,
                new ResponseListener() {
                    @Override
                    public void onResponse(String s) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Log.d(TAG, jsonObject.getJSONArray("data").toString());
                            Zona[] zonas = gson.fromJson(jsonObject.getJSONArray("data").toString(),Zona[].class);
                            ZonasListarResponse response = new ZonasListarResponse(zonas);
                            result.ok(new ResultWebServiceOk(response));
                        } catch (JSONException e) {
                            result.fail(new ResultWebServiceFail(e));
                        } catch (Exception e) {
                            result.fail(new ResultWebServiceFail(e.getMessage()));
                        }
                    }

                    @Override
                    public void onErrorResponse(String s) {
                        result.fail(new ResultWebServiceFail(s));
                    }
                },
                admin
        );
        executeEnviar(activity, callWebServiceJson);
    }

    public static void sync(final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final DataBase db = DataBase.getInstance(activity);
        final String url=Constants.url+Constants.ws_sync;
        String last_update= admin.obtener_preferencia(Constants.last_updated);
        if(last_update.isEmpty()) {
            admin.escribir_preferencia(Constants.last_updated,admin.getCurrentDateAndTime());
        }
        SyncRequest request= new SyncRequest(last_update);

        CallWebServiceJson callWebServiceJson = new CallWebServiceJson(
                activity,
                url,
                request.getCampos(),
                getHeaders(admin),
                jamper91.com.easyway.Util.Constants.REQUEST_POST,
                new ResponseListener() {
                    @Override
                    public void onResponse(String s) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            SyncResponse response = gson.fromJson(jsonObject.getJSONObject("data").toString(),SyncResponse.class);

                            try {
                                if (response.getEpcs()!=null) {
                                    for (Epc epc: response.getEpcs()) {
                                        db.add(Constants.table_epcs, epc.getContentValues());
                                    }
                                }
                                if (response.getProductos()!=null) {
                                    for (Producto pro: response.getProductos()) {
                                        db.add(Constants.table_productos, pro.getContentValues());
                                    }
                                }
                                if (response.getProductos_zona()!=null) {
                                    for (ProductosZonas productosZona: response.getProductos_zona()) {
                                        db.add(Constants.table_productos_zonas, productosZona.getContentValues());
                                    }
                                }
                                result.ok(new ResultWebServiceOk(response));

                            } catch (Exception e) {
                                admin.toast(e.getMessage());
                                result.fail(new ResultWebServiceFail(e.getMessage()));
                            }

                        } catch (JSONException e) {
                            result.fail(new ResultWebServiceFail(e));
                        } catch (Exception e) {
                            result.fail(new ResultWebServiceFail(e.getMessage()));
                        }
                    }

                    @Override
                    public void onErrorResponse(String s) {
                        result.fail(new ResultWebServiceFail(s));
                    }
                },
                admin
        );
        executeObtener(activity, callWebServiceJson);
    }
}
