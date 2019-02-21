package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import inventarioreal.com.inventarioreal_admin.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.Producto;
import inventarioreal.com.inventarioreal_admin.pojo.ProductosZonas;

public class SyncResponse {
    private Epc[] epcs;
    private Producto[] productos;
    private ProductosZonas[] productos_zona;

    public SyncResponse() {
    }

    public Epc[] getEpcs() {
        return epcs;
    }

    public void setEpcs(Epc[] epcs) {
        this.epcs = epcs;
    }

    public Producto[] getProductos() {
        return productos;
    }

    public void setProductos(Producto[] productos) {
        this.productos = productos;
    }

    public ProductosZonas[] getProductos_zona() {
        return productos_zona;
    }

    public void setProductos_zona(ProductosZonas[] productos_zona) {
        this.productos_zona = productos_zona;
    }
}
