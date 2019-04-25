package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonasHasTransferencias;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transferencias;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class CrearTransferenciaRequest {
    private Transferencias transferencia;
    private List<ProductosZonasHasTransferencias> pzt;
    private Gson gson = new Gson();

    public CrearTransferenciaRequest(Transferencias transferencia, List<ProductosZonasHasTransferencias> pzt) {
        this.transferencia = transferencia;
        this.pzt = pzt;
    }

    public HashMap<String, String> getCampos(){

        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.transferencia, this.getTransferencia());
        campos.put(Constants.productos_zona_has_transferencias, this.getProductosZonaHasTransfenrencias());
        return campos;
    }


    private String getTransferencia(){
        JsonObject object = new JsonObject();
        object.addProperty(Constants.local_origen_id, transferencia.getLocal_origen_id().getId());
        object.addProperty(Constants.local_destino_id, transferencia.getLocal_destino_id().getId());
        object.addProperty(Constants.mensaje, transferencia.getMensaje());
        return gson.toJson(object);
    }
    private String getProductosZonaHasTransfenrencias(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (ProductosZonasHasTransferencias aux: pzt
        ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.estado, false);
            object.addProperty(Constants.transferencias_id, 0);
            object.addProperty(Constants.productos_zona_id, aux.getProductos_zona_id().getId());
            array.add(object);

        }
        return gson.toJson(array);
    }

}

