package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

public class Ventas extends InventarioRealPojo {
    public Users users_id;
    public ProductosZonas productos_zonas_id;

    public Users getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Users users_id) {
        this.users_id = users_id;
    }

    public ProductosZonas getProductos_zonas_id() {
        return productos_zonas_id;
    }

    public void setProductos_zonas_id(ProductosZonas productos_zonas_id) {
        this.productos_zonas_id = productos_zonas_id;
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
