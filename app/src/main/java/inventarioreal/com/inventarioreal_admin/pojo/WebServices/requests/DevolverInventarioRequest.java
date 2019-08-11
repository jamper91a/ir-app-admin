package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosProductos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class DevolverInventarioRequest {
    private List<ProductosZonas> productos_zonas;

    public DevolverInventarioRequest(List<ProductosZonas> productos_zonas) {
        this.productos_zonas = productos_zonas;
    }


    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.productos_zona, this.getProductosZonas());
        return campos;
    }

/*
    private String getInventario(){
        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        object.addProperty(Constants.parcial, true);
        object.addProperty(Constants.colaborativo, false);
        object.addProperty(Constants.zonas_id, this.inventarios.getZonas_id().getId());
        object.addProperty(Constants.inventarios_consolidados_id, 1);
        return gson.toJson(object);
    }*/

    private String getProductosZonas(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (ProductosZonas pz: productos_zonas
        ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.zonas_id, pz.getZonas_id().getId());
            object.addProperty(Constants.epcs_id, pz.getEpcs_id().getId());
            object.addProperty(Constants.devoluciones_id, pz.getDevoluciones_id().getId());
            object.addProperty(Constants.devolucion_observaciones, pz.getDevolucion_observaciones());
            object.addProperty(Constants.productos_id, pz.getProductos_id().getId());
            array.add(object);

        }
        return gson.toJson(array);
    }

}

