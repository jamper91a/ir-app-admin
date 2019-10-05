package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

public class Report extends InventarioRealPojo {
    private int type;
    private int amount;
    private int units_sell;
    private int units_returned;
    private String firstDate;
    private String secondDate;
    private ConsolidatedInventory firstInventory;
    private ConsolidatedInventory secondInventory;
    private ReportsHasProductsZone[] products;

    public Report() {
    }

    public Report(long id) {
        super(id);
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUnitsSell() {
        return units_sell;
    }

    public void setUnitsSell(int units_sell) {
        this.units_sell = units_sell;
    }

    public int getUnitsReturned() {
        return units_returned;
    }

    public void setUnitsReturned(int units_returned) {
        this.units_returned = units_returned;
    }

    public ConsolidatedInventory getFirstInventory() {
        return firstInventory;
    }

    public void setFirstInventory(ConsolidatedInventory firstInventory) {
        this.firstInventory = firstInventory;
    }

    public ConsolidatedInventory getSecondInventory() {
        return secondInventory;
    }

    public void setSecondInventory(ConsolidatedInventory secondInventory) {
        this.secondInventory = secondInventory;
    }

    public ReportsHasProductsZone[] getProducts() {
        return products;
    }

    public void setProducts(ReportsHasProductsZone[] products) {
        this.products = products;
    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    public String getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(String secondDate) {
        this.secondDate = secondDate;
    }
}
