package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Inventory extends InventarioRealPojo {

    private String date;
    private int parcial;
    private int collaborative;
    private String message;
    private Zone zone;
    private ConsolidatedInventory consolidatedInventory;
    private ProductHasZone[] products;
    private Employee[] employees;

    public Inventory() {
    }

    public Inventory(long id) {
        super(id);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getParcial() {
        return parcial;
    }

    public void setParcial(int parcial) {
        this.parcial = parcial;
    }

    public int getCollaborative() {
        return collaborative;
    }

    public void setCollaborative(int collaborative) {
        this.collaborative = collaborative;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public ConsolidatedInventory getConsolidatedInventory() {
        return consolidatedInventory;
    }

    public void setConsolidatedInventory(ConsolidatedInventory consolidatedInventory) {
        this.consolidatedInventory = consolidatedInventory;
    }

    public ProductHasZone[] getProducts() {
        return products;
    }

    public void setProducts(ProductHasZone[] products) {
        this.products = products;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public void setEmployees(Employee[] employees) {
        this.employees = employees;
    }


    //
//
    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Constants.column_id, id);
        values.put(Constants.column_date, date);
        values.put(Constants.column_parcial, parcial);
        values.put(Constants.column_collaborative, collaborative);
        values.put(Constants.column_message, message);
        values.put(Constants.column_zone, zone.id);
        values.put(Constants.column_consolidatedInventory, consolidatedInventory.id);
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
