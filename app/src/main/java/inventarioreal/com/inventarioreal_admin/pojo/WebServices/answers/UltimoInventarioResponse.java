package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Employee;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;

public class UltimoInventarioResponse extends InventarioRealPojo {
    private String name;
    private Employee empleados_id;
    private int productos;
    private Inventory[] inventarios;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getEmpleados_id() {
        return empleados_id;
    }

    public void setEmpleados_id(Employee empleados_id) {
        this.empleados_id = empleados_id;
    }

    public int getProductos() {
        return productos;
    }

    public void setProductos(int productos) {
        this.productos = productos;
    }

    public Inventory[] getInventarios() {
        return inventarios;
    }

    public void setInventarios(Inventory[] inventarios) {
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
