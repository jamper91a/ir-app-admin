package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

public class Empleados extends InventarioRealPojo {

    private Companias companias_id;
    private Users users_id;
    private Locales locales_id;

    public Empleados() {
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

    public Companias getCompanias_id() {
        return companias_id;
    }

    public void setCompanias_id(Companias companias_id) {
        this.companias_id = companias_id;
    }

    public Users getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Users users_id) {
        this.users_id = users_id;
    }

    public Locales getLocales_id() {
        return locales_id;
    }

    public void setLocales_id(Locales locales_id) {
        this.locales_id = locales_id;
    }
    //    @Override
//    public ContentValues getContentValues() {
//        return null;
//    }

}
