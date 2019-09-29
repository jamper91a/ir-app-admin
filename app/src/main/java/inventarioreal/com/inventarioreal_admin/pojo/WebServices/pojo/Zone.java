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
        values.put(Constants.column_id, getId());
        values.put(Constants.column_name, name);
        values.put(Constants.column_shop, shop.getId());
        values.put(Constants.createdAt,getCreatedAt());
		values.put(Constants.updatedAt,getUpdatedAt());
        return values;
    }

    @Override
    public void fromCursor(Cursor c) {
        this.setId(c.getLong(c.getColumnIndexOrThrow(Constants.column_id)));
        this.name = c.getString(c.getColumnIndexOrThrow(Constants.column_name));
        this.shop = new Shop(c.getLong(c.getColumnIndexOrThrow(Constants.column_shop)));
        this.setCreatedAt(c.getString(c.getColumnIndexOrThrow(Constants.createdAt)));
        this.setUpdatedAt(c.getString(c.getColumnIndexOrThrow(Constants.updatedAt)));

    }

    @Override
    public String toString() {
        return this.name;
    }
}
