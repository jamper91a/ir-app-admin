package inventarioreal.com.inventarioreal_admin.util.WebServices;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.AddCommodityResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.GetProductsByConsolidatedInventoryResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.GetReportByIdResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.SaleUnitsResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.SyncResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.UltimoInventarioResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Devolution;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventoryHasProduct;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Report;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ReportsHasProductsZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Sell;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.AddCommodityRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.AttachInventoryRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.ConsolidateInventoriesRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CreateCollaborativeInventoryRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CreateInventoryRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CreateSellRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CreateTransferRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.GetDiferenceBetweenInventoriesRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.GetProductInShopByEanPluRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.GetProductsByConsolidatedInventoryRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.GetProductsByInventoryRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.GetReportByIdRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.GetReportsByTypeRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.GetTransfersByTypeRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.GetTransfersRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.HomologateUnitsRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.ListConsolidatedInventoriesRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.ListInventoriesRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.ReturnProductRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.FinishTransferRequet;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.GetProductByEanPluRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.LoginRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.RotationUnitsReportRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.SaleUnitsReportRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.SaveReportRequest;
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
        LoginRequest request = new LoginRequest(username, password);
        post(url, request.getCampos(), R.string.ingresando, activity, admin, new ResponseListener() {

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
        });
    }

    /**
     * Servicio web para consultarun producto por codigo
     * @param code Codigo a consultar
     * @param activity Actividad desde el cual se llama
     * @param admin
     * @param result Callback
     */
    public static void getProductByEanPlu(String code, final Activity activity, final Administrador admin,final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_findProduct;
        try {
            GetProductByEanPluRequest request = new GetProductByEanPluRequest(code);
            post(
                    url,
                    request.getCampos(),
                    R.string.consultando,
                    activity,
                    admin,
                    new ResponseListener() {
                        @Override
                        public void onResponse(String s) {
                            //result.ok(new ResultWebServiceOk());
                        }

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                Product productos = gson.fromJson(jsonObject.getJSONObject("data").toString(), Product.class);
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
                    }
            );
        } catch (Exception e){
            result.fail(new ResultWebServiceFail(e.getMessage()));
        }


    }

    public static void addCommodity(long productos_id, LinkedList<ProductHasZone> productosZonas, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_addCommodity;

        AddCommodityRequest request = new AddCommodityRequest(productos_id, productosZonas);

        post(
                url,
                request.getCampos(),
                R.string.enviado,
                activity,
                admin,
                new ResponseListener() {
                    @Override
                    public void onResponse(String s) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            AddCommodityResponse response = gson.fromJson(jsonObject.getJSONObject("data").toString(), AddCommodityResponse.class);
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
                }
        );
    }

    public static void sync(final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final DataBase db = DataBase.getInstance(activity);
        try {
            final String url=Constants.url+Constants.ws_sync;
            String last_update= admin.obtener_preferencia(Constants.last_updated);
            if(last_update.isEmpty()) {
                admin.escribir_preferencia(Constants.last_updated,admin.getCurrentDateAndTime());
            }
            SyncRequest request= new SyncRequest(last_update);
            post(
                    url,
                    request.getCampos(),
                    R.string.consultando,
                    activity,
                    admin,
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
                                        for (Epc epc: response.getEpcs()) {
                                            db.insert(Constants.table_epcs, epc.getContentValues());
                                        }
                                    }
                                    if (response.getProducts()!=null && response.getProducts().length>0) {
                                        for (Product pro: response.getProducts()) {
                                            db.insert(Constants.table_products, pro.getContentValues());
                                        }
                                    }
                                    if (response.getProductsHasZones()!=null && response.getProductsHasZones().length>0) {
                                        for (ProductHasZone productosZona: response.getProductsHasZones()) {
                                            db.insert(Constants.table_productsHasZones, productosZona.getContentValues());
                                        }
                                    }
                                    if (response.getZones()!=null && response.getZones().length>0) {
                                        for (Zone zona: response.getZones()) {
                                            db.insert(Constants.table_zones, zona.getContentValues());
                                        }
                                    }
                                    if (response.getShops()!=null && response.getShops().length>0) {
                                        for (Shop local: response.getShops()) {
                                            db.insert(Constants.table_shops, local.getContentValues());
                                        }
                                    }

                                    if (response.getDevolutions()!=null && response.getDevolutions().length>0) {
                                        for (Devolution devoluciones: response.getDevolutions()) {
                                            db.insert(Constants.table_devolutions, devoluciones.getContentValues());
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
                    }
            );
        } catch (Exception e){
            result.fail(new ResultWebServiceFail(e.getMessage()));
        }
    }

    public static void createInventory(long zone, String message, List<InventoryHasProduct> products, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        try {
            final String url=Constants.url+Constants.ws_createInventory;
            CreateInventoryRequest request = new CreateInventoryRequest(zone, message, products);
            post(
                    url,
                    request.getCampos(),
                    R.string.enviado,
                    activity,
                    admin,
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
                    }
            );
        } catch (Exception e) {
            result.fail(new ResultWebServiceFail(e.getMessage()));
        }
    }

    /**
     * Encargado de la devolucion de productos
     * @param products incluye el id de devolucion
     * @param activity
     * @param admin
     * @param result
     */
    public static void returnProducts(List<ProductHasZone> products, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_returnProduct;
        ReturnProductRequest request = new ReturnProductRequest(products);

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

    public static void attachInventory(Inventory inventory, String message, List<InventoryHasProduct> products, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        try {
            final String url=Constants.url+Constants.ws_attachInventory;

            AttachInventoryRequest request = new AttachInventoryRequest(inventory, message, products);
            post(
                    url,
                    request.getCampos(),
                    R.string.enviado,
                    activity,
                    admin,
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
                    }
            );
        } catch (Exception e) {
            result.fail(new ResultWebServiceFail(e.getMessage()));
        }
    }

    public static void createCollaborativeInventory(long zone, String message, List<InventoryHasProduct> products, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_createInventory;

        CreateCollaborativeInventoryRequest request = new CreateCollaborativeInventoryRequest(zone, message, products);

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
     * @param type Tipo de inventory: Constants.tipo_consolidado o Constants.tipo_no_consolidado
     * @param collaborative true or false
     */
    public static void listInventories(String type, boolean collaborative, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_listInventories;
        ListInventoriesRequest request = new ListInventoriesRequest(type, collaborative);

        post(
                url,
                request.getCampos(),
                R.string.consultando,
                activity,
                admin,
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
                                Inventory[] inventories = gson.fromJson(jsonObject.getJSONArray("data").toString(), Inventory[].class);

                                try {
                                    if (inventories!=null && inventories.length>0) {
                                        final DataBase db = DataBase.getInstance(activity);
                                        for (Inventory inventario: inventories) {
                                            Zone zona=(Zone)db.findById(Constants.table_zones, inventario.getZone().getId()+"", Zone.class);
                                            if(zona!=null){
                                                inventario.setZone(zona);
                                            }
                                        }
                                        ArrayList<Inventory> arrayInventarios = new ArrayList<Inventory>(Arrays.asList(inventories));
                                        result.ok(new ResultWebServiceOk(arrayInventarios));
                                    }else{
                                        result.fail(new ResultWebServiceFail("No hay inventory"));
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
                }
        );

    }

    public static void listConsolidatedInventories(boolean collaborative, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_listConsolidatedInventories;
        ListConsolidatedInventoriesRequest request = new ListConsolidatedInventoriesRequest(collaborative);
        post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //Obtengo la respuesta y la completo, debido a que el servicio web no me
                    // trae informacion de las zonas
                    try {
                        ConsolidatedInventory[] inventariosConsolidados = gson.fromJson(jsonObject.getJSONArray("data").toString(), ConsolidatedInventory[].class);

                        try {
                            if (inventariosConsolidados!=null && inventariosConsolidados.length>0) {
                                ArrayList<ConsolidatedInventory> arrayInventarios = new ArrayList<ConsolidatedInventory>(Arrays.asList(inventariosConsolidados));
                                result.ok(new ResultWebServiceOk(arrayInventarios));
                            }else{
                                result.fail(new ResultWebServiceFail("No hay inventory"));
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

    public static void listAllConsolidatedInventories(final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_listAllConsolidatedInventories;
        post(url, new HashMap<String, String>(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //Obtengo la respuesta y la completo, debido a que el servicio web no me
                    // trae informacion de las zonas
                    try {
                        ConsolidatedInventory[] inventariosConsolidados = gson.fromJson(jsonObject.getJSONArray("data").toString(), ConsolidatedInventory[].class);

                        try {
                            if (inventariosConsolidados!=null && inventariosConsolidados.length>0) {
                                ArrayList<ConsolidatedInventory> arrayInventarios = new ArrayList<ConsolidatedInventory>(Arrays.asList(inventariosConsolidados));
                                result.ok(new ResultWebServiceOk(arrayInventarios));
                            }else{
                                result.fail(new ResultWebServiceFail("No hay inventory"));
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

    public static void consolidateInventories(final ArrayList<Long> inventories, final String name, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_consolidateInventory;
        ConsolidateInventoriesRequest request = new ConsolidateInventoriesRequest(inventories, name);
        post(url, request.getCampos(), R.string.enviando_informacion, activity, admin, new ResponseListener() {
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
        });

    }

    public static void getProductsByInventory(final long inventory, final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){
        try {
            final String url=Constants.url+Constants.ws_listProductsByInventory;
            GetProductsByInventoryRequest request = new GetProductsByInventoryRequest(inventory);
            post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
                @Override
                public void onResponse(String s) {

                }

                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        Inventory inventario = gson.fromJson(jsonObject.getJSONObject("data").toString(), Inventory.class);
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
        } catch (Exception e) {
            result.fail(new ResultWebServiceFail(e.getMessage()));
        }
    }

    public static void getProductsByConsolidatedInventory(final long consolidatedInventory, final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){
        try {
            final String url=Constants.url+Constants.ws_listProductByConsolidatedInventory;
            GetProductsByConsolidatedInventoryRequest request = new GetProductsByConsolidatedInventoryRequest(consolidatedInventory);
            post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
                @Override
                public void onResponse(String s) {

                }

                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        GetProductsByConsolidatedInventoryResponse aux = gson.fromJson(jsonObject.getJSONObject("data").toString(), GetProductsByConsolidatedInventoryResponse.class);
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
        } catch (Exception e) {
            result.fail(new ResultWebServiceFail(e.getMessage()));
        }
    }

    public static void getTransfers(final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){

        final String url=Constants.url+Constants.ws_findTransfersByShop;
        LoginResponse loginResponse = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        GetTransfersRequest request = new GetTransfersRequest(loginResponse.getEmployee().getShop().getId());
        post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String data = jsonObject.getJSONArray("data").toString();
                    Transfer[] aux = gson.fromJson(data, Transfer[].class);
                    if (aux!=null && aux.length>0) {
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

    public static void getTranfersByType(final String type, final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){

        final String url=Constants.url+Constants.ws_findTransfersByType;
        LoginResponse loginResponse = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        GetTransfersByTypeRequest request = new GetTransfersByTypeRequest(loginResponse.getEmployee().getShop().getId(), type);
        post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Transfer[] aux = gson.fromJson(jsonObject.getJSONArray("data").toString(), Transfer[].class);
                    if (aux!=null && aux.length>0) {
                        ArrayList<Transfer> arrayInventarios = new ArrayList<>(Arrays.asList(aux));
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

    public static void finishTransfer(final LinkedList<TransfersHasZonesProduct> products, final String message, final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){
        final String url=Constants.url+Constants.ws_finishTransfer;
        FinishTransferRequet request = new FinishTransferRequet(products, message);
        post(url, request.getCampos(), R.string.enviando_informacion, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    try {
                        String data = jsonObject.getJSONArray("data").toString();
                        ProductHasZone[] aux = gson.fromJson(data, ProductHasZone[].class);
                        if (aux!=null && aux.length>0) {
                            result.ok(new ResultWebServiceOk(aux));
                        }
                    } catch (JSONException e) {
                        admin.toast(e.getMessage());
                        result.fail(new ResultWebServiceFail(e.getMessage()));
                    } catch (Exception e) {
                        admin.toast(e.getMessage());
                        result.fail(new ResultWebServiceFail(e.getMessage()));
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

    public static void createTransfer(final Transfer tranfer, final LinkedList<TransfersHasZonesProduct> products, final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){
        final String url=Constants.url+Constants.ws_createTransfer;
        CreateTransferRequest request = new CreateTransferRequest(tranfer, products);
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

    public static void getLastConsolidatedInventory(final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){
        final String url=Constants.url+Constants.ws_listLastConsolidatedInventory;
        post(url, new HashMap<String, String>(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    UltimoInventarioResponse aux = gson.fromJson(jsonObject.getJSONObject("data").toString(),UltimoInventarioResponse.class);
                    if (aux!=null) {
                        result.ok(new ResultWebServiceOk(aux));
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

    public static void getProductInShopByEanPlu(final long product, final Activity activity, final Administrador admin, final ResultWebServiceInterface result ){
        final String url=Constants.url+Constants.ws_findProductInShopByEanPlu;
        GetProductInShopByEanPluRequest request = new GetProductInShopByEanPluRequest(product);
        post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    ProductHasZone[] aux = gson.fromJson(jsonObject.getJSONArray("data").toString(), ProductHasZone[].class);
                    if (aux!=null && aux.length>0) {
                        ArrayList<ProductHasZone> arrayProductosZonas = new ArrayList<ProductHasZone>(Arrays.asList(aux));
                        result.ok(new ResultWebServiceOk(arrayProductosZonas));
                    }else{
                        result.fail(new ResultWebServiceFail("No hay productos"));
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

    public static void getDiferenceBetweenInventories(final long firstInventory, final long secondInventory, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_diferenceBetweenInventories;
        GetDiferenceBetweenInventoriesRequest request = new GetDiferenceBetweenInventoriesRequest(firstInventory, secondInventory);
        post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //Obtengo la respuesta y la completo, debido a que el servicio web no me
                    // trae informacion de las zonas
                    try {
                        ProductHasZone[] productosZonas = gson.fromJson(jsonObject.getJSONArray("data").toString(), ProductHasZone[].class);

                        try {
                            if (productosZonas!=null && productosZonas.length>0) {
                                ArrayList<ProductHasZone> arrayInventarios = new ArrayList<>(Arrays.asList(productosZonas));
                                result.ok(new ResultWebServiceOk(arrayInventarios));
                            }else{
                                result.fail(new ResultWebServiceFail("No hay productos"));
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

    public static void saveReport(final Report report, ProductHasZone[] products, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_saveReport;
        SaveReportRequest request = new SaveReportRequest(report, products);
        post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                    result.ok(new ResultWebServiceOk());
            }

            @Override
            public void onErrorResponse(String s) {
                result.fail(new ResultWebServiceFail(s));
            }
        });
    }

    public static void createSell(final Sell sell, final LinkedList<ProductHasZone> products, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_sellCommodity;
        CreateSellRequest request = new CreateSellRequest(sell, products);
        post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    try {
                        ProductHasZone[] products = gson.fromJson(jsonObject.getJSONArray("data").toString(), ProductHasZone[].class);

                        try {
                            if (products!=null && products.length>0) {
                                result.ok(new ResultWebServiceOk(products));
                            }else{
                                result.fail(new ResultWebServiceFail("Error vendiendo los productos"));
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

    public static void getReportsByType(final String type, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_getReportsByType;
        GetReportsByTypeRequest report = new GetReportsByTypeRequest(type);
        post(url, report.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //Obtengo la respuesta y la completo, debido a que el servicio web no me
                    try {
                        Report[] reports = gson.fromJson(jsonObject.getJSONArray("data").toString(), Report[].class);

                        try {
                            if (reports!=null && reports.length>0) {
                                ArrayList<Report> arrayReports = new ArrayList<Report>(Arrays.asList(reports));
                                result.ok(new ResultWebServiceOk(arrayReports));
                            }else{
                                result.fail(new ResultWebServiceFail("No hay reportes"));
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

    public static void getReportById(final long id, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_getReportById;
        GetReportByIdRequest report = new GetReportByIdRequest(id);
        post(url, report.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //Obtengo la respuesta y la completo, debido a que el servicio web no me
                    try {
                        GetReportByIdResponse response = gson.fromJson(jsonObject.getJSONObject("data").toString(), GetReportByIdResponse.class);
                        try {
                            if (response!=null) {
                                result.ok(new ResultWebServiceOk(response));
                            }else{
                                result.fail(new ResultWebServiceFail("No hay informacion"));
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

    public static void homologateUnits(LinkedList<ReportsHasProductsZone> products, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_homologateUnits;
        HomologateUnitsRequest report = new HomologateUnitsRequest(products);
        post(url, report.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //Obtengo la respuesta y la completo, debido a que el servicio web no me
                    try {
                        GetReportByIdResponse response = gson.fromJson(jsonObject.getJSONObject("data").toString(), GetReportByIdResponse.class);
                        try {
                            if (response!=null) {
                                result.ok(new ResultWebServiceOk(response));
                            }else{
                                result.fail(new ResultWebServiceFail("No hay informacion"));
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

    public static void saleUnits(final String firstDate, final String secondDate, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_saleUnits;
        SaleUnitsReportRequest request = new SaleUnitsReportRequest(firstDate, secondDate);
        post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //Obtengo la respuesta y la completo, debido a que el servicio web no me
                    // trae informacion de las zonas
                    try {
                        ProductHasZone[] saleUnits = gson.fromJson(jsonObject.getJSONObject("data").getJSONArray("saleUnits").toString(), ProductHasZone[].class);
                        ProductHasZone[] returnedUnits = gson.fromJson(jsonObject.getJSONObject("data").getJSONArray("returnedUnits").toString(), ProductHasZone[].class);
                        ArrayList<ProductHasZone> arraySaleUnits = new ArrayList<>();
                        ArrayList<ProductHasZone> arrayReturnedUnits = new ArrayList<>();
                        try {
                            if (saleUnits!=null && saleUnits.length>0) {
                                arraySaleUnits = new ArrayList<>(Arrays.asList(saleUnits));
                            }
                            if (returnedUnits!=null && returnedUnits.length>0) {
                                arrayReturnedUnits = new ArrayList<>(Arrays.asList(returnedUnits));
                            }

                            result.ok(new ResultWebServiceOk(new SaleUnitsResponse(arraySaleUnits, arrayReturnedUnits)));

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

    public static void rotationUnits(final String firstDate, final String secondDate, final Activity activity, final Administrador admin, final ResultWebServiceInterface result){
        final String url=Constants.url+Constants.ws_rotationUnits;
        RotationUnitsReportRequest request = new RotationUnitsReportRequest(firstDate, secondDate);
        post(url, request.getCampos(), R.string.consultando, activity, admin, new ResponseListener() {
            @Override
            public void onResponse(String s) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //Obtengo la respuesta y la completo, debido a que el servicio web no me
                    // trae informacion de las zonas
                    try {
                        ProductHasZone[] units = gson.fromJson(jsonObject.getJSONArray("data").toString(), ProductHasZone[].class);
                        ArrayList<ProductHasZone> arrayUnits = new ArrayList<>();
                        try {
                            if (units!=null && units.length>0) {
                                arrayUnits = new ArrayList<>(Arrays.asList(units));
                            }

                            result.ok(new ResultWebServiceOk(arrayUnits));

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

}
