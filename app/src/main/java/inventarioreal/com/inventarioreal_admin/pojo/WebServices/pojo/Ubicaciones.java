package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Ubicaciones extends InventarioRealPojo {

    private String nombre;
    private Epcs epcs_id;
    private Zonas zonas_id;

    public Ubicaciones() {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Epcs getEpcs_id() {
        return epcs_id;
    }

    public void setEpcs_id(Epcs epcs_id) {
        this.epcs_id = epcs_id;
    }

    public Zonas getZonas_id() {
        return zonas_id;
    }

    public void setZonas_id(Zonas zonas_id) {
        this.zonas_id = zonas_id;
    }
}
