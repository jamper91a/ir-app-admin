package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosProductos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class AdjuntarInventarioRequest {
    private Inventarios inventarios;
    private List<InventariosProductos> inventario_productos;

    public AdjuntarInventarioRequest(Inventarios inventarios, List<InventariosProductos> inventario_productos) {
        this.inventarios = inventarios;
        this.inventario_productos = inventario_productos;
    }


    public HashMap<String, String> getCampos(){

        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.inventario, this.getInventario());
        campos.put(Constants.inventario_productos, this.getInventarioProductos());
        return campos;
    }


    private String getInventario(){
        Gson gson = new Gson();
        return gson.toJson(inventarios);
    }

    private String getInventarioProductos(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (InventariosProductos iv: inventario_productos
        ) {
            JsonObject object = new JsonObject();
            object.addProperty(Constants.zonas_id, iv.getZonas_id().getId());
            object.addProperty(Constants.productos_zona_id, iv.getProductoz_zona_id().getId());
            object.addProperty(Constants.productos_epcs_id, iv.getProductos_epcs_id().getId());
            array.add(object);

        }
        return gson.toJson(array);
    }

}

