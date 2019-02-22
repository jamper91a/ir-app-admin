package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation.Producto;

public class AddMercanciaResponse {
    private Producto producto;
    private List<Epc> epcs;

    public AddMercanciaResponse() {
    }

    public AddMercanciaResponse(Producto producto, List<Epc> epcs) {
        this.producto = producto;
        this.epcs = epcs;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Epc> getEpcs() {
        return epcs;
    }

    public void setEpcs(List<Epc> epcs) {
        this.epcs = epcs;
    }
}
