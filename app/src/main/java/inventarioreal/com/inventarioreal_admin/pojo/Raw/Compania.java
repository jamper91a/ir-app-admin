package inventarioreal.com.inventarioreal_admin.pojo.Raw;

import android.content.ContentValues;

import inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation.InventarioRealPojo;

public class Compania extends InventarioRealPojo {
    private String createdAt;
    private String updatedAt;
    private String id;
    private String name;

    public Compania() {
    }



    public Compania(String createdAt, String updatedAt, String id, String name) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
        this.name = name;
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
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ContentValues getContentValues() {
        return null;
    }
}
