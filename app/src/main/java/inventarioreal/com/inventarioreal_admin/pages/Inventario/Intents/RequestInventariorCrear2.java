package inventarioreal.com.inventarioreal_admin.pages.Inventario.Intents;

import android.util.Log;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;
import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestInventariorCrear2 implements IntentRequests {
    private static final String TAG = "RequestInventarioCrear2";
    public Zonas zona_id;
    public String power;
    public String fecha;
    public Inventarios inventario;

    public RequestInventariorCrear2() {
    }

    public RequestInventariorCrear2(Zonas zona_id, String power, String fecha) {
        this.zona_id = zona_id;
        this.power = power;
        this.fecha = fecha;
    }

    public RequestInventariorCrear2(Inventarios inventario) {
        this.inventario = inventario;
        this.zona_id = inventario.zonas_id;
        this.fecha = inventario.fecha;
    }

    public Zonas getZona_id() {
        return zona_id;
    }

    public void setZona_id(Zonas zona_id) {
        this.zona_id = zona_id;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean validar() {
        try {
            if(this.fecha.isEmpty() && this.power.isEmpty() && this.zona_id.id<=0)
            {
                return false;
            }else{
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    @Override
    public HashMap<String, String> getAtributos() {
        return null;
    }

    public Inventarios getInventario() {
        return inventario;
    }

    public void setInventario(Inventarios inventario) {
        this.inventario = inventario;
    }
}
