package inventarioreal.com.inventarioreal_admin.pages.Inventario.Intents;

import android.util.Log;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestInventariorCrear2 implements IntentRequests {
    private static final String TAG = "RequestInventarioCrear2";
    public Zone zone;
    public String power;
    public String date;
    public Inventory inventory;
    public boolean union=false;

    public RequestInventariorCrear2() {
    }

    public RequestInventariorCrear2(Zone zone, String power, String date) {
        this.zone = zone;
        this.power = power;
        this.date = date;
    }

    public RequestInventariorCrear2(Inventory inventory) {
        this.inventory = inventory;
        this.zone = inventory.getZone();
        this.date = inventory.getDate();
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean validar() {
        try {
            if(this.date.isEmpty() && this.power.isEmpty() && this.zone.id<=0)
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean isUnion() {
        return union;
    }

    public void setUnion(boolean union) {
        this.union = union;
    }
}
