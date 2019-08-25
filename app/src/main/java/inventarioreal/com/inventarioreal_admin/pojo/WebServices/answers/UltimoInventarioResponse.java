package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Employee;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;

public class UltimoInventarioResponse extends InventarioRealPojo {
    private String name;
    private Employee employee;
    private int total_products;
    private Inventory[] inventories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getTotal_products() {
        return total_products;
    }

    public void setTotal_products(int total_products) {
        this.total_products = total_products;
    }

    public Inventory[] getInventories() {
        return inventories;
    }

    public void setInventories(Inventory[] inventories) {
        this.inventories = inventories;
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
