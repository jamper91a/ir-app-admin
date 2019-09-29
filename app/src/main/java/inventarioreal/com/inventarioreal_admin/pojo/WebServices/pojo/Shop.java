package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Shop extends InventarioRealPojo {
    private Company company;
    private String name;

    public Shop() {
    }

    public Shop(long id) {
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
        ContentValues values = new ContentValues();
        values.put(Constants.column_id, getId());
        values.put(Constants.column_company, company.getId());
        values.put(Constants.column_name, name);
        values.put(Constants.createdAt,getCreatedAt());
		values.put(Constants.updatedAt,getUpdatedAt());
        return values;
    }

    @Override
    public void fromCursor(Cursor c) {
        this.setId(c.getLong(c.getColumnIndexOrThrow(Constants.column_id)));
        this.name = c.getString(c.getColumnIndexOrThrow(Constants.column_name));
        this.company = new Company(c.getLong(c.getColumnIndexOrThrow(Constants.column_company)));
        this.setCreatedAt(c.getString(c.getColumnIndexOrThrow(Constants.createdAt)));
        this.setUpdatedAt(c.getString(c.getColumnIndexOrThrow(Constants.updatedAt)));
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
