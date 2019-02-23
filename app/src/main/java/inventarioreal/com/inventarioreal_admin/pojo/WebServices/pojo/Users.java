package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

public class Users extends InventarioRealPojo {

    public String username;
    public String username_rfdi;
    public String password_rfdi;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername_rfdi() {
        return username_rfdi;
    }

    public void setUsername_rfdi(String username_rfdi) {
        this.username_rfdi = username_rfdi;
    }

    public String getPassword_rfdi() {
        return password_rfdi;
    }

    public void setPassword_rfdi(String password_rfdi) {
        this.password_rfdi = password_rfdi;
    }

    public Groups getGroups_id() {
        return groups_id;
    }

    public void setGroups_id(Groups groups_id) {
        this.groups_id = groups_id;
    }

    public Groups groups_id;

    public Users() {
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

}
