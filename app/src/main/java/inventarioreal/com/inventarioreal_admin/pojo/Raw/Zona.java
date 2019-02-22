package inventarioreal.com.inventarioreal_admin.pojo.Raw;

import android.content.ContentValues;

import inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Zona extends InventarioRealPojo {

    private long id;
    private String name;
    private long locales_id;
    private String createdAt;
    private String updatedAt;

    public Zona() {
    }

    public Zona(long id, String name, long locales_id, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.locales_id = locales_id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLocales_id() {
        return locales_id;
    }

    public void setLocales_id(long locales_id) {
        this.locales_id = locales_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Constants.id, id);
        values.put(Constants.name, name);
        values.put(Constants.locales_id, locales_id);
        values.put(Constants.createdAt, createdAt);
        values.put(Constants.updatedAt, updatedAt);
        return values;
    }
}
