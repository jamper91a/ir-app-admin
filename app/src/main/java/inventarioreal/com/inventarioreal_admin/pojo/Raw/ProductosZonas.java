package inventarioreal.com.inventarioreal_admin.pojo.Raw;

import android.content.ContentValues;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ProductosZonas {

    private long id;
    private long productos_id;
    private long zonas_id;
    private String fecha_ingreso;
    private String fecha_venta;
    private String fecha_devolucion;
    private String devolucion_observaciones;
    private long devoluciones_id;
    private String log_usuarios;
    private long ventas_id;
    private long epcs_id;
    private String createdAt;
    private String updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductos_id() {
        return productos_id;
    }

    public void setProductos_id(long productos_id) {
        this.productos_id = productos_id;
    }

    public long getZonas_id() {
        return zonas_id;
    }

    public void setZonas_id(long zonas_id) {
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

    public long getDevoluciones_id() {
        return devoluciones_id;
    }

    public void setDevoluciones_id(long devoluciones_id) {
        this.devoluciones_id = devoluciones_id;
    }

    public String getLog_usuarios() {
        return log_usuarios;
    }

    public void setLog_usuarios(String log_usuarios) {
        this.log_usuarios = log_usuarios;
    }

    public long getVentas_id() {
        return ventas_id;
    }

    public void setVentas_id(long ventas_id) {
        this.ventas_id = ventas_id;
    }

    public long getEpcs_id() {
        return epcs_id;
    }

    public void setEpcs_id(long epcs_id) {
        this.epcs_id = epcs_id;
    }

    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(Constants.id, id);
        values.put(Constants.productos_id, productos_id);
        values.put(Constants.zonas_id, zonas_id);
        values.put(Constants.fecha_ingreso, fecha_ingreso);
        values.put(Constants.fecha_venta, fecha_venta);
        values.put(Constants.fecha_devolucion, fecha_devolucion);
        values.put(Constants.devolucion_observaciones, devolucion_observaciones);
        values.put(Constants.devoluciones_id, devoluciones_id);
        values.put(Constants.logs_usuarios, log_usuarios);
        values.put(Constants.ventas_id, ventas_id);
        values.put(Constants.epcs_id, epcs_id);
        return values;
    }
}
