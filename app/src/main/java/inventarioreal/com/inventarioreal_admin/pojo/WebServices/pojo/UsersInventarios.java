package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

public class UsersInventarios extends InventarioRealPojo {

    public Inventarios inventarios_id;
    public Empleados empleados_id;

    public Inventarios getInventarios_id() {
        return inventarios_id;
    }

    public void setInventarios_id(Inventarios inventarios_id) {
        this.inventarios_id = inventarios_id;
    }

    public Empleados getEmpleados_id() {
        return empleados_id;
    }

    public void setEmpleados_id(Empleados empleados_id) {
        this.empleados_id = empleados_id;
    }

    public UsersInventarios() {
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
