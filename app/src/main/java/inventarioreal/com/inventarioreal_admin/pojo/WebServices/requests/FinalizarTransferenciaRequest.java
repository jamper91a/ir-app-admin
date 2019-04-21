package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosProductos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonasHasTransferencias;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class FinalizarTransferenciaRequest {
    private List<ProductosZonasHasTransferencias> pzt;

    public FinalizarTransferenciaRequest(List<ProductosZonasHasTransferencias> pzt) {
        this.pzt = pzt;
    }

    public HashMap<String, String> getCampos(){

        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.productos_zona_has_transferencias, this.getProductosZonaHasTransfenrencias());
        return campos;
    }



    private String getProductosZonaHasTransfenrencias(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (ProductosZonasHasTransferencias aux: pzt
        ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.id, aux.getId());
            object.addProperty(Constants.transferencias_id, aux.getTransferencias_id().getId());
            object.addProperty(Constants.productos_zona_id, aux.getProductos_zona_id().getId());
            array.add(object);

        }
        return gson.toJson(array);
    }

}

