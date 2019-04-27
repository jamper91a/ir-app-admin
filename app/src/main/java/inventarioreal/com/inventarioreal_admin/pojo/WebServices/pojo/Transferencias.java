package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

public class Transferencias extends InventarioRealPojo {

    private Users creador_id;
    private String manifiesto;
    private Locales local_origen_id;
    private Locales local_destino_id;
    private boolean estado;
    private String mensaje;
    private ProductosZonasHasTransferencias[] productos;

    public Transferencias() {
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

    public Users getCreador_id() {
        return creador_id;
    }

    public void setCreador_id(Users creador_id) {
        this.creador_id = creador_id;
    }

    public String getManifiesto() {
        return manifiesto;
    }

    public void setManifiesto(String manifiesto) {
        this.manifiesto = manifiesto;
    }

    public Locales getLocal_origen_id() {
        return local_origen_id;
    }

    public void setLocal_origen_id(Locales local_origen_id) {
        this.local_origen_id = local_origen_id;
    }

    public Locales getLocal_destino_id() {
        return local_destino_id;
    }

    public void setLocal_destino_id(Locales local_destino_id) {
        this.local_destino_id = local_destino_id;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ProductosZonasHasTransferencias[] getProductos() {
        return productos;
    }

    public void setProductos(ProductosZonasHasTransferencias productos[]) {
        this.productos = productos;
    }
}
