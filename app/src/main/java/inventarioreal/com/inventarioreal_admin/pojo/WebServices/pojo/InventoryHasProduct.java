package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class InventoryHasProduct extends InventarioRealPojo {

    public Inventory inventory;
    public Epc epc;
    public Zone zone;
    public ProductHasZone product;

    public InventoryHasProduct() {
    }

    public InventoryHasProduct(long id) {
        super(id);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Epc getEpc() {
        return epc;
    }

    public void setEpc(Epc epc) {
        this.epc = epc;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public ProductHasZone getProduct() {
        return product;
    }

    public void setProduct(ProductHasZone product) {
        this.product = product;
    }

    //
    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Constants.column_id, id);
        values.put(Constants.column_inventory, inventory.id);
        values.put(Constants.column_epc_id, epc.id);
        values.put(Constants.column_zone, zone.id);
        values.put(Constants.column_productHasZone, product.id);
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
