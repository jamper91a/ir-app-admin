package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

class InventarioConsolidados extends InventarioRealPojo {

    public Empleados empleados_id;

    public Empleados getEmpleados_id() {
        return empleados_id;
    }

    public void setEmpleados_id(Empleados empleados_id) {
        this.empleados_id = empleados_id;
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
