package inventarioreal.com.inventarioreal_admin.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public abstract class InventarioRealPojo {

    public long id;
    public String createdAt;
    public String updatedAt;
    public InventarioRealPojo() {
    }

    public InventarioRealPojo(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public abstract void fromHashMap(Class myClass, HashMap<String,String> data);
    public abstract void toHashMap(Class myClass);
    public abstract ContentValues getContentValues();
    public abstract void fromCursor(Cursor c);

    public Date getDateCreatedAt(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return sdf.parse(this.createdAt);
        } catch (ParseException e) {
            return null;
        }
    }
    public Date getDateUpdatedAt(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return sdf.parse(this.updatedAt);
        } catch (ParseException e) {
            return null;
        }
    }


}
