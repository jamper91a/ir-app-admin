package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class AddMercanciaRequest {
    private long productos_id;
    private List<ProductosZonaHere> productos_zona;

    public AddMercanciaRequest(long productos_id, long zonas_id, List<Epc> epcs) {
        this.productos_id = productos_id;
        this.productos_zona = new ArrayList<>();
        for (Epc epc :
                epcs) {
            productos_zona.add(new ProductosZonaHere(zonas_id, productos_id, epc.getEpc()));
        }
    }

    public HashMap<String, String> getCampos(){
        Gson gson = new Gson();
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.products_id, productos_id +"");
        campos.put(Constants.productos_zona, gson.toJson(productos_zona));
        return campos;
    }


}

class ProductosZonaHere{
    private long zonas_id;
    private long productos_id;
    private String ecps_id;
    private long devoluciones;
    private long ventas_id;

    public ProductosZonaHere() {
    }

    public ProductosZonaHere(long zonas_id, long productos_id, String ecps_id) {
        this.zonas_id = zonas_id;
        this.productos_id = productos_id;
        this.ecps_id = ecps_id;
        this.ventas_id = 1;
    }

    public ProductosZonaHere(long zonas_id, long productos_id, String ecps_id, long devoluciones, long ventas_id) {
        this.zonas_id = zonas_id;
        this.productos_id = productos_id;
        this.ecps_id = ecps_id;
        this.devoluciones = devoluciones;
        this.ventas_id = ventas_id;
    }
}
