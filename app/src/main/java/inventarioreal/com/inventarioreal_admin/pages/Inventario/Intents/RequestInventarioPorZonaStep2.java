package inventarioreal.com.inventarioreal_admin.pages.Inventario.Intents;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;
import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestInventarioPorZonaStep2 implements IntentRequests {
    private static final String TAG = "RequestInventarioPorZonaStep2";
    public Inventory inventory =null;
    public ConsolidatedInventory consolidatedInventory =null;

    public RequestInventarioPorZonaStep2() {
    }

    public RequestInventarioPorZonaStep2(Inventory inventory) {
        this.inventory = inventory;
    }

    public RequestInventarioPorZonaStep2(ConsolidatedInventory consolidatedInventory) {
        this.consolidatedInventory = consolidatedInventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ConsolidatedInventory getConsolidatedInventory() {
        return consolidatedInventory;
    }

    public void setConsolidatedInventory(ConsolidatedInventory consolidatedInventory) {
        this.consolidatedInventory = consolidatedInventory;
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
