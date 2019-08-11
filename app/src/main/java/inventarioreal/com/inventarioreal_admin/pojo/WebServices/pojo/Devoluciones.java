package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Devoluciones extends InventarioRealPojo {
    public String name;
    public Integer tipo;

    public Devoluciones() {
    }

    public Devoluciones(long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTIpo(Integer tipo) {
        this.tipo = tipo;
    }

    @Override
    public void fromHashMap(Class myClass, HashMap<String, String> data) {

    }

    @Override
    public void toHashMap(Class myClass) {

    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Constants.id, id);
        values.put(Constants.name, name);
        values.put(Constants.tipo, tipo);
        values.put(Constants.createdAt, createdAt);
        values.put(Constants.updatedAt, updatedAt);
        return values;
    }


    @Override
    public void fromCursor(Cursor c) {
        this.id = c.getLong(c.getColumnIndexOrThrow(Constants.id));
        this.name = c.getString(c.getColumnIndexOrThrow(Constants.name));
        this.tipo = c.getInt(c.getColumnIndexOrThrow(Constants.tipo));
        this.createdAt = c.getString(c.getColumnIndexOrThrow(Constants.createdAt));
        this.updatedAt = c.getString(c.getColumnIndexOrThrow(Constants.updatedAt));
    }
    //    @Override
//    public ContentValues getContentValues() {
//        return null;
//    }
    @Override
    public String toString() {
        return this.name;
    }
}
