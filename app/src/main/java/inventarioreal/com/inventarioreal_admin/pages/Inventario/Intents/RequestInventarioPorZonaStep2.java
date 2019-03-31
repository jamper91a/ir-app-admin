package inventarioreal.com.inventarioreal_admin.pages.Inventario.Intents;

import android.util.Log;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosConsolidados;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;
import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestInventarioPorZonaStep2 implements IntentRequests {
    private static final String TAG = "RequestInventarioPorZonaStep2";
    public Inventarios inventarios=null;
    public InventariosConsolidados inventariosConsolidados=null;

    public RequestInventarioPorZonaStep2() {
    }

    public RequestInventarioPorZonaStep2(Inventarios inventarios) {
        this.inventarios = inventarios;
    }

    public RequestInventarioPorZonaStep2(InventariosConsolidados inventariosConsolidados) {
        this.inventariosConsolidados = inventariosConsolidados;
    }

    public Inventarios getInventarios() {
        return inventarios;
    }

    public void setInventarios(Inventarios inventarios) {
        this.inventarios = inventarios;
    }

    public InventariosConsolidados getInventariosConsolidados() {
        return inventariosConsolidados;
    }

    public void setInventariosConsolidados(InventariosConsolidados inventariosConsolidados) {
        this.inventariosConsolidados = inventariosConsolidados;
    }

    @Override
    public boolean validar() {
        return false;
    }

    @Override
    public HashMap<String, String> getAtributos() {
        return null;
    }
}
