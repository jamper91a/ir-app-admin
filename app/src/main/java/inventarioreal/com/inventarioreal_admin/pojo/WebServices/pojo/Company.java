package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

public class Company extends InventarioRealPojo {
    private String name;
    private Employee[] employees;
    private Epc[] ecps;
    private Product[] products;
    private Shop[] shops;

    public Company() {
    }
    public Company(Long id) {
        super(id);
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public void setEmployees(Employee[] employees) {
        this.employees = employees;
    }

    public Epc[] getEcps() {
        return ecps;
    }

    public void setEcps(Epc[] ecps) {
        this.ecps = ecps;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public Shop[] getShops() {
        return shops;
    }

    public void setShops(Shop[] shops) {
        this.shops = shops;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    @Override
//    public ContentValues getContentValues(Class Companias) {
//        return null;
//    }
}
