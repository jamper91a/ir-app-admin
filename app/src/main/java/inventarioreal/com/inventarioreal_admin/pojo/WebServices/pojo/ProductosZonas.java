package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ProductosZonas extends InventarioRealPojo {

    public Productos productos_id;
    public Zonas zonas_id;
    public String fecha_ingreso;
    public String fecha_venta;
    public String fecha_devolucion;
    public String devolucion_observaciones;
    public Devoluciones devoluciones_id;
    public String log_usuarios;
    public Ventas ventas_id;
    public Epcs epcs_id;

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
        values.put(Constants.logs_usuarios, log_usuarios);
        values.put(Constants.ventas_id, ventas_id.id);
        values.put(Constants.epcs_id, epcs_id.id);
        values.put(Constants.createdAt, createdAt);
        values.put(Constants.updatedAt, updatedAt);
        return values;
    }

}
