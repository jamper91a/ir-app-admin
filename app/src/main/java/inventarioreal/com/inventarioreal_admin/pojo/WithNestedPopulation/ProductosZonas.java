package inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation;

import android.content.ContentValues;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ProductosZonas {

    private long id;
    private Producto productos_id;
    private Zona zonas_id;
    private String fecha_ingreso;
    private String fecha_venta;
    private String fecha_devolucion;
    private String devolucion_observaciones;
    private Devoluciones devoluciones_id;
    private String log_usuarios;
    private Ventas ventas_id;
    private Epc epcs_id;
    private String createdAt;
    private String updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Producto getProductos_id() {
        return productos_id;
    }

    public void setProductos_id(Producto productos_id) {
        this.productos_id = productos_id;
    }

    public Zona getZonas_id() {
        return zonas_id;
    }

    public void setZonas_id(Zona zonas_id) {
        this.zonas_id = zonas_id;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public String getDevolucion_observaciones() {
        return devolucion_observaciones;
    }

    public void setDevolucion_observaciones(String devolucion_observaciones) {
        this.devolucion_observaciones = devolucion_observaciones;
    }

    public Devoluciones getDevoluciones_id() {
        return devoluciones_id;
    }

    public void setDevoluciones_id(Devoluciones devoluciones_id) {
        this.devoluciones_id = devoluciones_id;
    }

    public String getLog_usuarios() {
        return log_usuarios;
    }

    public void setLog_usuarios(String log_usuarios) {
        this.log_usuarios = log_usuarios;
    }

    public Ventas getVentas_id() {
        return ventas_id;
    }

    public void setVentas_id(Ventas ventas_id) {
        this.ventas_id = ventas_id;
    }

    public Epc getEpcs_id() {
        return epcs_id;
    }

    public void setEpcs_id(Epc epcs_id) {
        this.epcs_id = epcs_id;
    }

    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(Constants.id, id);
        values.put(Constants.productos_id, productos_id.getId());
        values.put(Constants.zonas_id, zonas_id.getId());
        values.put(Constants.fecha_ingreso, fecha_ingreso);
        values.put(Constants.fecha_venta, fecha_venta);
        values.put(Constants.fecha_devolucion, fecha_devolucion);
        values.put(Constants.devolucion_observaciones, devolucion_observaciones);
        values.put(Constants.devoluciones_id, devoluciones_id.getId());
        values.put(Constants.logs_usuarios, log_usuarios);
        values.put(Constants.ventas_id, ventas_id.getId());
        values.put(Constants.epcs_id, epcs_id.getId());
        return values;
    }
}
