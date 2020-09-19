package inventarioreal.com.inventarioreal_admin.util;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;
import jamper91.com.easyway.Util.Administrador;

public class SocketHelper {

    private Administrador admin;
    private LoginResponse empleado;
    private HashMap<String,  Emitter.Listener >listeners = new HashMap<>();
    public SocketHelper() {
    }

    public SocketHelper(Administrador admin) {
        this.admin = admin;
        Gson gson = new Gson();
        empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
    }

    //    private HashMap<String,Emitter.Listener> listeners = new  HashMap<>();
    private Socket mSocket;
    {
        try {
            IO.Options options = new IO.Options();
            options.transports = new String[] { WebSocket.NAME};
            mSocket = IO.socket(Constants.url + "?__sails_io_sdk_version=1.0.0", options);
        } catch (URISyntaxException e) {}
    }

    private void setHeaders(){
//        mSocket.io().on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    Transport transport = (Transport)args[0];
//
//                    transport.on(Transport.EVENT_REQUEST_HEADERS, new Emitter.Listener() {
//                        @Override
//                        public void call(Object... args) {
//                            @SuppressWarnings("unchecked")
//                            Map<String, List<String>> headers = (Map<String, List<String>>)args[0];
//                            // modify request headers
////                            headers.put("Cookie", Arrays.asList("foo=1;"));
//                            headers.put(Constants.authorization, Arrays.asList("Bearer  "+admin.obtener_preferencia(Constants.token)));
//                            System.out.println("HeY");
//                        }
//                    });
//
//                    transport.on(Transport.EVENT_RESPONSE_HEADERS, new Emitter.Listener() {
//                        @Override
//                        public void call(Object... args) {
////                            @SuppressWarnings("unchecked")
////                            Map<String, List<String>> headers = (Map<String, List<String>>)args[0];
////                            // access response headers
////                            String cookie = headers.get("Set-Cookie").get(0);
//                        }
//                    });
//                }
//            });
    }

    public void connect(){
        mSocket.connect();
    }

    private void post(String url, JSONObject body){
        /**
         * Example body
         * JSONObject jsonObjectBody = new JSONObject();
         * try {
         *     jsonObjectBody.put("myId", "11" );
         * } catch (JSONException e) {
         *     e.printStackTrace();
         * }
         */
        JSONObject jsonObjectRequest = new JSONObject();
        try {
            jsonObjectRequest.put("url", url +"?auth_token="+admin.obtener_preferencia(Constants.token));
            jsonObjectRequest.put("data", body);
            mSocket.emit("post", jsonObjectRequest, new Ack() {
                @Override
                public void call(Object... args) {
                    if(args[0]!= null) {
                        JSONObject response = (JSONObject) args[0];
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    public void subs(){
        post("/sockets/subs", new JSONObject());
    }

    public void listenForEpcCode(Emitter.Listener listener) {
        mSocket.on("getEpcByEpc", listener);
        listeners.put("getEpcByEpc", listener);
    }
    public void findEpcByEpcCode(String epcCode) {
        JSONObject body = new JSONObject();

        try {
            body.put("epc", epcCode);
            body.put("userId", empleado.getEmployee().getUser().getId());
            mSocket.emit("getEpcByEpc", body, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.d("Speaking", "Speaking ...");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void disconnect(){
        mSocket.disconnect();
        for(String event: listeners.keySet()) {
            mSocket.off(event,  listeners.get(event));
        }
    }
}
