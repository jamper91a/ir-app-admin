package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Zone extends InventarioRealPojo {

    private String name;
    private Shop shop;

    public Zone() {
    }

    public Zone(long id) {
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Constants.column_id, id);
        values.put(Constants.column_name, name);
        values.put(Constants.column_shop, shop.id);
        values.put(Constants.createdAt, createdAt);
        values.put(Constants.updatedAt, updatedAt);
        return values;
    }

    @Override
    public void fromCursor(Cursor c) {
        this.id = c.getLong(c.getColumnIndexOrThrow(Constants.column_id));
        this.name = c.getString(c.getColumnIndexOrThrow(Constants.column_name));
        this.shop = new Shop(c.getLong(c.getColumnIndexOrThrow(Constants.column_shop)));
        this.createdAt = c.getString(c.getColumnIndexOrThrow(Constants.createdAt));
        this.updatedAt = c.getString(c.getColumnIndexOrThrow(Constants.updatedAt));

    }

    @Override
    public String toString() {
        return this.name;
    }
}
