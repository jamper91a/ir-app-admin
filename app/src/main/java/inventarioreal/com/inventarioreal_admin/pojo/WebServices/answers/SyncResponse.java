package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epcs;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Productos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;

public class SyncResponse {
    private Epcs[] epcs;
    private Productos[] productos;
    private ProductosZonas[] productos_zona;
    private Zonas[] zonas;

    public SyncResponse() {
    }

    public Epcs[] getEpcs() {
        return epcs;
    }

    public void setEpcs(Epcs[] epcs) {
        this.epcs = epcs;
    }

    public Productos[] getProductos() {
        return productos;
    }

    public void setProductos(Productos[] productos) {
        this.productos = productos;
    }

    public ProductosZonas[] getProductos_zona() {
        return productos_zona;
    }

    public void setProductos_zona(ProductosZonas[] productos_zona) {
        this.productos_zona = productos_zona;
    }

    public Zonas[] getZonas() {
        return zonas;
    }

    public void setZonas(Zonas[] zonas) {
        this.zonas = zonas;
    }
}
