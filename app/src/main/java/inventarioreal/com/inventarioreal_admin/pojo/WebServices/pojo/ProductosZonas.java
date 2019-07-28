package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ProductosZonas extends InventarioRealPojo {

    private Productos productos_id;
    private Zonas zonas_id;
    private String fecha_ingreso;
    private String fecha_venta;
    private String fecha_devolucion;
    private String devolucion_observaciones;
    private Devoluciones devoluciones_id;
    private String logs_usuarios;
    private Ventas ventas_id;
    private Epcs epcs_id;

    //Extra field to get the Total of items
    private int total=1;

    public ProductosZonas() {
    }



    @Override
    public void fromHashMap(Class myClass, HashMap<String, String> data) {

    }

    @Override
    public void toHashMap(Class myClass) {

    }


    @Override
    public void fromCursor(Cursor c) {

        this.id = c.getLong(c.getColumnIndexOrThrow(Constants.id));
        this.createdAt = c.getString(c.getColumnIndexOrThrow(Constants.createdAt));
        this.updatedAt = c.getString(c.getColumnIndexOrThrow(Constants.updatedAt));
        this.productos_id = new Productos(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.productos_id)
                )
        );
        this.zonas_id = new Zonas(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.zonas_id)
                )
        );
        this.fecha_ingreso = c.getString(c.getColumnIndex(Constants.fecha_ingreso));
        this.fecha_venta = c.getString(c.getColumnIndex(Constants.fecha_venta));
        this.fecha_devolucion = c.getString(c.getColumnIndex(Constants.fecha_devolucion));
        this.devolucion_observaciones = c.getString(c.getColumnIndex(Constants.devolucion_observaciones));
        this.devoluciones_id = new Devoluciones(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.devoluciones_id)
                )
        );
        this.logs_usuarios = c.getString(c.getColumnIndex(Constants.logs_usuarios));
        this.ventas_id = new Ventas(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.ventas_id)
                )
        );
        this.epcs_id = new Epcs(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.epcs_id)
                )
        );

    }

    public Productos getProductos_id() {
        return productos_id;
    }

    public void setProductos_id(Productos productos_id) {
        this.productos_id = productos_id;
    }

    public Zonas getZonas_id() {
        return zonas_id;
    }

    public void setZonas_id(Zonas zonas_id) {
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
        return logs_usuarios;
    }

    public void setLog_usuarios(String log_usuarios) {
        this.logs_usuarios = log_usuarios;
    }

    public Ventas getVentas_id() {
        return ventas_id;
    }

    public void setVentas_id(Ventas ventas_id) {
        this.ventas_id = ventas_id;
    }

    public Epcs getEpcs_id() {
        return epcs_id;
    }

    public void setEpcs_id(Epcs epcs_id) {
        this.epcs_id = epcs_id;
    }

    @Override
    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(Constants.id, id);
        values.put(Constants.productos_id, productos_id.id);
        values.put(Constants.zonas_id, zonas_id.id);
        values.put(Constants.fecha_ingreso, fecha_ingreso);
        values.put(Constants.fecha_venta, fecha_venta);
        values.put(Constants.fecha_devolucion, fecha_devolucion);
        values.put(Constants.devolucion_observaciones, devolucion_observaciones);
        values.put(Constants.devoluciones_id, devoluciones_id.id);
        values.put(Constants.logs_usuarios, logs_usuarios);
        values.put(Constants.ventas_id, ventas_id.id);
        values.put(Constants.epcs_id, epcs_id.id);
        values.put(Constants.createdAt, createdAt);
        values.put(Constants.updatedAt, updatedAt);
        return values;
    }

    public String getLogs_usuarios() {
        return logs_usuarios;
    }

    public void setLogs_usuarios(String logs_usuarios) {
        this.logs_usuarios = logs_usuarios;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
