package inventarioreal.com.inventarioreal_admin.util.WebServices;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.AddMercanciaResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.GetProductosInventariosConsolidados;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.SyncResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Devoluciones;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epcs;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosConsolidados;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosProductos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Locales;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Productos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonasHasTransferencias;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transferencias;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.AddMercanciaRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.AdjuntarInventarioRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CrearInventarioColaborativoRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CrearInventarioRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CrearTransferenciaRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.DevolverInventarioRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.FinalizarTransferenciaRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.SyncRequest;
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
        headers.put(Constants.content_type, "application/json");
        headers.put("Content-Type", "application/json");
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

    private static void post(final String url, final HashMap<String, String> campos, final int mensaje, final Activity activity, final Administrador admin, final ResponseListener result){
        CallWebServiceJson callWebServiceJson = new CallWebServiceJson(
                activity,
                url,
                campos,
                getHeaders(admin),
                jamper91.com.easyway.Util.Constants.REQUEST_POST,
                result,
                admin
        );
        callWebServiceJson.setMessage(activity.getString(mensaje));
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
                                Productos productos = gson.fromJson(jsonObject.getJSONObject("data").toString(), Productos.class);
                                result.ok(new ResultWebServiceOk(productos));
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

    public static void addMercancia(long productos_id, LinkedList<ProductosZonas> productosZonas, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_addMercancia;

        AddMercanciaRequest request = new AddMercanciaRequest(productos_id, productosZonas);

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
                                if (response.getEpcs()!=null && response.getEpcs().length>0) {
                                    for (Epcs epc: response.getEpcs()) {
                                        db.insert(Constants.table_epcs, epc.getContentValues());
                                    }
                                }
                                if (response.getProductos()!=null && response.getProductos().length>0) {
                                    for (Productos pro: response.getProductos()) {
                                        db.insert(Constants.table_productos, pro.getContentValues());
                                    }
                                }
                                if (response.getProductos_zona()!=null && response.getProductos_zona().length>0) {
                                    for (ProductosZonas productosZona: response.getProductos_zona()) {
                                        db.insert(Constants.table_productos_zonas, productosZona.getContentValues());
                                    }
                                }
                                if (response.getZonas()!=null && response.getZonas().length>0) {
                                    for (Zonas zona: response.getZonas()) {
                                        db.insert(Constants.table_zonas, zona.getContentValues());
                                    }
                                }
                                if (response.getLocales()!=null && response.getLocales().length>0) {
                                    Log.d("Locales", response.getLocales().toString());
                                    for (Locales local: response.getLocales()) {
                                        db.insert(Constants.table_locales, local.getContentValues());
                                    }
                                }

                                if (response.getDevoluciones()!=null && response.getDevoluciones().length>0) {
                                    Log.d("Devoluciones", response.getDevoluciones().toString());
                                    for (Devoluciones devoluciones: response.getDevoluciones()) {
                                        db.insert(Constants.table_devoluciones, devoluciones.getContentValues());
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

    public static void crearInventario(long zonas_id, List<InventariosProductos> inventario_productos, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_crearInventario;
        CrearInventarioRequest request = new CrearInventarioRequest(zonas_id, inventario_productos);

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
                            result.ok(new ResultWebServiceOk(null));
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

    /**
     * Encargado de la devolucion de productos
     * @param productosZonas incluye el id de devolucion
     * @param activity
     * @param admin
     * @param result
     */
    public static void devolucionProductos(List<ProductosZonas> productosZonas, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_devolverProductos;
        DevolverInventarioRequest request = new DevolverInventarioRequest(productosZonas);

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
                            result.ok(new ResultWebServiceOk(null));
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

    public static void adjuntarInventario(Inventarios inventarios, List<InventariosProductos> inventario_productos, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_adjuntarInvenario;

        AdjuntarInventarioRequest request = new AdjuntarInventarioRequest(inventarios, inventario_productos);

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
                            result.ok(new ResultWebServiceOk(null));
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

    public static void crearInventarioColaborativo(long zonas_id, List<InventariosProductos> inventario_productos, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_crearInventario;

        CrearInventarioColaborativoRequest request = new CrearInventarioColaborativoRequest(zonas_id, inventario_productos);

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
                            result.ok(new ResultWebServiceOk(null));
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

    /**
     *
     * @param tipo Tipo de inventario: Constants.tipo_consolidado o Constants.tipo_no_consolidado
     * @param colaborativo true or false
     */
    public static void listarInventario(String tipo, boolean colaborativo,final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_listarInventarios;
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.tipo, tipo);
        campos.put(Constants.colaborativo, colaborativo ? "1" : "0");

        CallWebServiceJson callWebServiceJson = new CallWebServiceJson(
                activity,
                url,
                campos,
                getHeaders(admin),
                jamper91.com.easyway.Util.Constants.REQUEST_POST,
                new ResponseListener() {
                    @Override
                    public void onResponse(String s) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            //Obtengo la respuesta y la completo, debido a que el servicio web no me
                            // trae informacion de las zonas
                            try {
                                Inventarios[] inventarios = gson.fromJson(jsonObject.getJSONArray("data").toString(),Inventarios[].class);

                                try {
                                    if (inventarios!=null && inventarios.length>0) {
                                        final DataBase db = DataBase.getInstance(activity);
                                        for (Inventarios inventario: inventarios) {
                                            Zonas zona=(Zonas)db.findById(Constants.table_zonas, inventario.getZonas_id().getId()+"", Zonas.class);
                                            if(zona!=null){
                                                inventario.setZonas_id(zona);
                                            }
                                        }
                                        ArrayList<Inventarios> arrayInventarios = new ArrayList<Inventarios>(Arrays.asList(inventarios));
                                        result.ok(new ResultWebServiceOk(arrayInventarios));
                                    }else{
                                        result.fail(new ResultWebServiceFail("No hay inventarios"));
                                    }

                                } catch (Exception e) {
                                    admin.toast(e.getMessage());
                                    result.fail(new ResultWebServiceFail(e.getMessage()));
                                }

                            } catch (JSONException e) {
                                result.fail(new ResultWebServiceFail(e));
                            } catch (Exception e) {
                                result.fail(new ResultWebServiceFail(e.getMessage()));
                            }
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
    public static void listarInventarioConsolidados(boolean colaborativo, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_listarInventariosConsolidados;
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.colaborativo, colaborativo ? "1" : "0");
        post(url, campos, R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //Obtengo la respuesta y la completo, debido a que el servicio web no me
                    // trae informacion de las zonas
                    try {
                        InventariosConsolidados[] inventariosConsolidados = gson.fromJson(jsonObject.getJSONArray("data").toString(),InventariosConsolidados[].class);

                        try {
                            if (inventariosConsolidados!=null && inventariosConsolidados.length>0) {
                                ArrayList<InventariosConsolidados> arrayInventarios = new ArrayList<InventariosConsolidados>(Arrays.asList(inventariosConsolidados));
                                result.ok(new ResultWebServiceOk(arrayInventarios));
                            }else{
                                result.fail(new ResultWebServiceFail("No hay inventarios"));
                            }

                        } catch (Exception e) {
                            admin.toast(e.getMessage());
                            result.fail(new ResultWebServiceFail(e.getMessage()));
                        }

                    } catch (JSONException e) {
                        result.fail(new ResultWebServiceFail(e));
                    } catch (Exception e) {
                        result.fail(new ResultWebServiceFail(e.getMessage()));
                    }
                } catch (Exception e) {
                    result.fail(new ResultWebServiceFail(e.getMessage()));
                }
            }

            @Override
            public void onErrorResponse(String s) {

            }
        });
    }

    public static void consolidarInventarios(final ArrayList<Long> inventariosAConsolidar, final String name, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_consolidarInventarios;
        JsonArray array = new JsonArray();
        for (Long iac: inventariosAConsolidar)  array.add(iac);
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.inventarios_id, gson.toJson(array));
        campos.put(Constants.name, name);

        CallWebServiceJson callWebServiceJson = new CallWebServiceJson(
                activity,
                url,
                campos,
                getHeaders(admin),
                jamper91.com.easyway.Util.Constants.REQUEST_POST,
                new ResponseListener() {
                    @Override
                    public void onResponse(String s) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getString("code").equals("OK")){
                                result.ok(new ResultWebServiceOk(null));
                            }
                        } catch (JSONException e) {
                            result.fail(new ResultWebServiceFail(e));
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

    public static void getProductsByInventario(final long inventario_id, final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){
        final String url=Constants.url+Constants.ws_getProductsByInventory;
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.inventarios_id, inventario_id+"");
        post(url, campos, R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Inventarios inventario = gson.fromJson(jsonObject.getJSONObject("data").toString(),Inventarios.class);
                    result.ok(new ResultWebServiceOk(inventario));
                } catch (Exception e) {
                    admin.toast(e.getMessage());
                    result.fail(new ResultWebServiceFail(e.getMessage()));
                }
            }

            @Override
            public void onErrorResponse(String s) {
                result.fail(new ResultWebServiceFail(s));
            }
        });
    }

    public static void getProductsByInventariConsolidado(final long inventario_id, final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){
        final String url=Constants.url+Constants.ws_getProductsByInventoryColaborativo;
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.inventarios_consolidados_id, inventario_id+"");
        post(url, campos, R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    GetProductosInventariosConsolidados aux = gson.fromJson(jsonObject.getJSONObject("data").toString(),GetProductosInventariosConsolidados.class);
                    result.ok(new ResultWebServiceOk(aux));
                } catch (Exception e) {
                    admin.toast(e.getMessage());
                    result.fail(new ResultWebServiceFail(e.getMessage()));
                }
            }

            @Override
            public void onErrorResponse(String s) {
                result.fail(new ResultWebServiceFail(s));
            }
        });
    }

    public static void getTransferencias(final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){

        final String url=Constants.url+Constants.ws_obtenerTransferencias;
        LoginResponse loginResponse = gson.fromJson(admin.obtener_preferencia(Constants.empleado), LoginResponse.class);
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.local_id, loginResponse.getEmpleado().getLocales_id().getId()+"");
        post(url, campos, R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String data = jsonObject.getJSONArray("data").toString();
                    Transferencias[] aux = gson.fromJson(data,Transferencias[].class);
                    if (aux!=null && aux.length>0) {
//                        ArrayList<Transferencias> arrayInventarios = new ArrayList<>(Arrays.asList(aux));
                        result.ok(new ResultWebServiceOk(aux));
                    }else{
                        result.fail(new ResultWebServiceFail("No hay transferencias"));
                    }
                } catch (JSONException e) {
                    admin.toast(e.getMessage());
                    result.fail(new ResultWebServiceFail(e.getMessage()));
                } catch (Exception e) {
                    admin.toast(e.getMessage());
                    result.fail(new ResultWebServiceFail(e.getMessage()));
                }
            }

            @Override
            public void onErrorResponse(String s) {
                result.fail(new ResultWebServiceFail(s));
            }
        });
    }

    public static void getTransferenciasByTipo(final String tipo, final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){

        final String url=Constants.url+Constants.ws_obtenerTransferencia;
        LoginResponse loginResponse = gson.fromJson(admin.obtener_preferencia(Constants.empleado), LoginResponse.class);
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.local_id, loginResponse.getEmpleado().getLocales_id().getId()+"");
        campos.put(Constants.tipo, tipo);
        post(url, campos, R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Transferencias[] aux = gson.fromJson(jsonObject.getJSONArray("data").toString(),Transferencias[].class);
                    if (aux!=null && aux.length>0) {
                        ArrayList<Transferencias> arrayInventarios = new ArrayList<>(Arrays.asList(aux));
                        result.ok(new ResultWebServiceOk(arrayInventarios));
                    }else{
                        result.fail(new ResultWebServiceFail("No hay transferencias"));
                    }
                } catch (Exception e) {
                    admin.toast(e.getMessage());
                    result.fail(new ResultWebServiceFail(e.getMessage()));
                }
            }

            @Override
            public void onErrorResponse(String s) {
                result.fail(new ResultWebServiceFail(s));
            }
        });
    }

    public static void finalizarTransferencia(final LinkedList<ProductosZonasHasTransferencias> pzt,final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){
        final String url=Constants.url+Constants.ws_finalizarTransferencia;
        FinalizarTransferenciaRequest request = new FinalizarTransferenciaRequest(pzt);
        post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    result.ok(new ResultWebServiceOk());
                } catch (Exception e) {
                    admin.toast(e.getMessage());
                    result.fail(new ResultWebServiceFail(e.getMessage()));
                }
            }

            @Override
            public void onErrorResponse(String s) {
                result.fail(new ResultWebServiceFail(s));
            }
        });
    }

    public static void crearTransferencia(final Transferencias transferencia,final LinkedList<ProductosZonasHasTransferencias> productos, final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){
        final String url=Constants.url+Constants.ws_crearTransferencia;
        CrearTransferenciaRequest request = new CrearTransferenciaRequest(transferencia, productos);
        post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    result.ok(new ResultWebServiceOk());
                } catch (Exception e) {
                    admin.toast(e.getMessage());
                    result.fail(new ResultWebServiceFail(e.getMessage()));
                }
            }

            @Override
            public void onErrorResponse(String s) {
                result.fail(new ResultWebServiceFail(s));
            }
        });
    }



}
