package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Inventarios extends InventarioRealPojo {

    public String fecha;
    public boolean parcial;
    public boolean colaborativo;
    public Zonas zonas_id;
    public InventarioConsolidados inventarios_consolidados_id;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isParcial() {
        return parcial;
    }

    public void setParcial(boolean parcial) {
        this.parcial = parcial;
    }

    public boolean isColaborativo() {
        return colaborativo;
    }

    public void setColaborativo(boolean colaborativo) {
        this.colaborativo = colaborativo;
    }

    public Zonas getZonas_id() {
        return zonas_id;
    }

    public void setZonas_id(Zonas zonas_id) {
        this.zonas_id = zonas_id;
    }

    public InventarioConsolidados getInventarios_consolidados_id() {
        return inventarios_consolidados_id;
    }

    public void setInventarios_consolidados_id(InventarioConsolidados inventarios_consolidados_id) {
        this.inventarios_consolidados_id = inventarios_consolidados_id;
    }

    //
//
    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Constants.id, id);
        values.put(Constants.fecha, fecha);
        values.put(Constants.parcial, parcial);
        values.put(Constants.colaborativo, colaborativo);
        values.put(Constants.zonas_id, zonas_id.id);
        values.put(Constants.inventarios_consolidados_id, inventarios_consolidados_id.id);
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