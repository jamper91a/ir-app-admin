package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ProductosZonasHasTransferencias extends InventarioRealPojo {

    public ProductosZonas productos_zona_id;
    public Transferencias transferencias_id;
    public boolean estado;

    public ProductosZonas getProductos_zona_id() {
        return productos_zona_id;
    }

    public void setProductos_zona_id(ProductosZonas productos_zona_id) {
        this.productos_zona_id = productos_zona_id;
    }

    public Transferencias getTransferencias_id() {
        return transferencias_id;
    }

    public void setTransferencias_id(Transferencias transferencias_id) {
        this.transferencias_id = transferencias_id;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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
