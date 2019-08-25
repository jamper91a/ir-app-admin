package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

public class EmployeesInventory extends InventarioRealPojo {

    private Inventory inventory;
    private Employee employee;

    public EmployeesInventory(long id) {
        super(id);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeesInventory() {
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
