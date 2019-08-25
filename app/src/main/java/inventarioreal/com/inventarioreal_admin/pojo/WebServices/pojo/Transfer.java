package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Transfer extends InventarioRealPojo {

    private boolean state;
    private String manifest;
    private String message;
    private Employee employee;
    private Shop shopSource;
    private Shop shopDestination;
    private TransfersHasZonesProduct[] products;

    public Transfer() {
    }

    public Transfer(long id) {
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
        ContentValues values = new ContentValues();
        values.put(Constants.column_id, id);
        values.put(Constants.column_state, state);
        values.put(Constants.column_manifest, manifest);
        values.put(Constants.column_message, message);
        values.put(Constants.column_employee, employee.id);
        values.put(Constants.column_shopSource, shopSource.id);
        values.put(Constants.column_shopshopDestination, shopDestination.id);
        values.put(Constants.createdAt, createdAt);
        values.put(Constants.updatedAt, updatedAt);
        return values;
    }

    @Override
    public void fromCursor(Cursor c) {
        this.id = c.getLong(c.getColumnIndexOrThrow(Constants.column_id));
        setState(c.getInt(c.getColumnIndexOrThrow(Constants.column_state)));
        this.manifest = c.getString(c.getColumnIndexOrThrow(Constants.column_manifest));
        this.message = c.getString(c.getColumnIndexOrThrow(Constants.column_message));
        this.employee = new Employee(c.getLong(c.getColumnIndexOrThrow(Constants.column_employee)));
        this.shopSource = new Shop(c.getLong(c.getColumnIndexOrThrow(Constants.column_shopSource)));
        this.shopDestination = new Shop(c.getLong(c.getColumnIndexOrThrow(Constants.column_shopshopDestination)));
        this.createdAt = c.getString(c.getColumnIndexOrThrow(Constants.createdAt));
        this.updatedAt = c.getString(c.getColumnIndexOrThrow(Constants.updatedAt));
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getManifest() {
        return manifest;
    }

    public void setManifest(String manifest) {
        this.manifest = manifest;
    }

    public Shop getShopSource() {
        return shopSource;
    }

    public void setShopSource(Shop shopSource) {
        this.shopSource = shopSource;
    }

    public Shop getShopDestination() {
        return shopDestination;
    }

    public void setShopDestination(Shop shopDestination) {
        this.shopDestination = shopDestination;
    }

    public boolean getState() {
        return state;
    }

    public void setState(int state) {
        if(state==1)
            this.state = true;
        else
            this.state=false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransfersHasZonesProduct[] getProducts() {
        return products;
    }

    public void setProducts(TransfersHasZonesProduct products[]) {
        this.products = products;
    }
}
