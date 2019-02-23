package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

import static android.database.Cursor.FIELD_TYPE_BLOB;
import static android.database.Cursor.FIELD_TYPE_FLOAT;
import static android.database.Cursor.FIELD_TYPE_INTEGER;
import static android.database.Cursor.FIELD_TYPE_NULL;
import static android.database.Cursor.FIELD_TYPE_STRING;

public class Zonas extends InventarioRealPojo {

    private String name;
    private Locales locales_id;

    public Zonas() {
    }

    public Zonas(long id) {
        super(id);
    }


    @Override
    public void fromHashMap(Class myClass, HashMap<String, String> data) {

    }

    @Override
    public void toHashMap(Class myClass) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locales getLocales_id() {
        return locales_id;
    }

    public void setLocales_id(Locales locales_id) {
        this.locales_id = locales_id;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Constants.id, id);
        values.put(Constants.name, name);
        values.put(Constants.locales_id, locales_id.id);
        values.put(Constants.createdAt, createdAt);
        values.put(Constants.updatedAt, updatedAt);
        return values;
    }

    @Override
    public void fromCursor(Cursor c) {
        this.id = c.getLong(c.getColumnIndexOrThrow(Constants.id));
        this.name = c.getString(c.getColumnIndexOrThrow(Constants.name));
        this.locales_id = new Locales(c.getLong(c.getColumnIndexOrThrow(Constants.locales_id)));
        this.createdAt = c.getString(c.getColumnIndexOrThrow(Constants.createdAt));
        this.updatedAt = c.getString(c.getColumnIndexOrThrow(Constants.updatedAt));

    }

    @Override
    public String toString() {
        return this.name;
    }
}
