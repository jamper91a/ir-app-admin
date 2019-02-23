package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epcs;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Productos;

public class AddMercanciaResponse {
    private Productos productos;
    private List<Epcs> epcs;

    public AddMercanciaResponse() {
    }

    public AddMercanciaResponse(Productos productos, List<Epcs> epcs) {
        this.productos = productos;
        this.epcs = epcs;
    }

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

    public List<Epcs> getEpcs() {
        return epcs;
    }

    public void setEpcs(List<Epcs> epcs) {
        this.epcs = epcs;
    }
}
