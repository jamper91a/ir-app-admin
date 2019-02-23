package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class InventariosProductos extends InventarioRealPojo {

    public Inventarios inventarios_id;
    public Epcs productos_epcs_id;
    public Zonas zonas_id;
    public ProductosZonas productoz_zona_id;


    public Inventarios getInventarios_id() {
        return inventarios_id;
    }

    public void setInventarios_id(Inventarios inventarios_id) {
        this.inventarios_id = inventarios_id;
    }

    public Epcs getProductos_epcs_id() {
        return productos_epcs_id;
    }

    public void setProductos_epcs_id(Epcs productos_epcs_id) {
        this.productos_epcs_id = productos_epcs_id;
    }

    public Zonas getZonas_id() {
        return zonas_id;
    }

    public void setZonas_id(Zonas zonas_id) {
        this.zonas_id = zonas_id;
    }

    public ProductosZonas getProductoz_zona_id() {
        return productoz_zona_id;
    }

    public void setProductoz_zona_id(ProductosZonas productoz_zona_id) {
        this.productoz_zona_id = productoz_zona_id;
    }

    //
    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Constants.id, id);
        values.put(Constants.inventarios_id, inventarios_id.id);
        values.put(Constants.productos_epcs_id, productos_epcs_id.id);
        values.put(Constants.zonas_id, zonas_id.id);
        values.put(Constants.productos_zona_id, productoz_zona_id.id);
        values.put(Constants.createdAt, createdAt);
        values.put(Constants.updatedAt, updatedAt);
        return values;
    }

    @Override
    public void fromHashMap(Class myClass, HashMap<String, String> data) {

    }

    @Override
    public void toHashMap(Class myClass) {

    }

    @Override
    public void fromCursor(Cursor c) {

    }
}
