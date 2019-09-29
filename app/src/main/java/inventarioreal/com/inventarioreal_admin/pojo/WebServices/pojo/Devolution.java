package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Devolution extends InventarioRealPojo {
    private String name;
    private Integer type;

    public Devolution() {
    }

    public Devolution(long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer tipo) {
        this.type = tipo;
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
        values.put(Constants.column_id, getId());
        values.put(Constants.column_name, name);
        values.put(Constants.column_type, type);
        values.put(Constants.createdAt, getCreatedAt());
        values.put(Constants.updatedAt, getUpdatedAt());
        return values;
    }


    @Override
    public void fromCursor(Cursor c) {
        this.setId(c.getLong(c.getColumnIndexOrThrow(Constants.column_id)));
        this.name = c.getString(c.getColumnIndexOrThrow(Constants.column_name));
        this.type = c.getInt(c.getColumnIndexOrThrow(Constants.column_type));
        this.setCreatedAt(c.getString(c.getColumnIndexOrThrow(Constants.createdAt)));
        this.setUpdatedAt(c.getString(c.getColumnIndexOrThrow(Constants.updatedAt)));
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
