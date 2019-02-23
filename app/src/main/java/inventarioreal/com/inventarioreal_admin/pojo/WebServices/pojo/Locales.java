package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

public class Locales extends InventarioRealPojo {
    public Companias companias_id;
    public String name;

    public Locales() {
    }

    public Locales(long id) {
        super(id);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
