package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Empleados;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;

public class UltimoInventarioResponse extends InventarioRealPojo {
    private String name;
    private Empleados empleados_id;
    private int productos;
    private Inventarios[] inventarios;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Empleados getEmpleados_id() {
        return empleados_id;
    }

    public void setEmpleados_id(Empleados empleados_id) {
        this.empleados_id = empleados_id;
    }

    public int getProductos() {
        return productos;
    }

    public void setProductos(int productos) {
        this.productos = productos;
    }

    public Inventarios[] getInventarios() {
        return inventarios;
    }

    public void setInventarios(Inventarios[] inventarios) {
        this.inventarios = inventarios;
    }

    @Override
    public void fromHashMap(Class myClass, HashMap<String, String> data) {

    }

    @Override
    public void toHashMap(Class myClass) {

    }

    @Override
    public ContentValues getContentValues() {
        return null;
    }

    @Override
    public void fromCursor(Cursor c) {

    }
}
