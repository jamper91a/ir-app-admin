package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ProductHasZone extends InventarioRealPojo {

    private String admission_date;
    private String sell_date;
    private String return_date;
    private String notes_return;
    private String logs_users;
    private Product product;
    private Zone zone;
    private Devolution devolution;
    private Sell sell;
    private Epc epc;
    private Inventory[] inventories;
    //Extra field to get the Total of items
    private int total=1;

    public ProductHasZone() {
    }

    public ProductHasZone(long id) {
        super(id);
    }

    @Override
    public void fromHashMap(Class myClass, HashMap<String, String> data) {

    }

    @Override
    public void toHashMap(Class myClass) {

    }


    @Override
    public void fromCursor(Cursor c) {
        this.id = c.getLong(c.getColumnIndexOrThrow(Constants.column_id));

        this.admission_date = c.getString(c.getColumnIndex(Constants.column_admissionDate));
        this.sell_date = c.getString(c.getColumnIndex(Constants.column_sellDate));
        this.return_date = c.getString(c.getColumnIndex(Constants.column_returnDate));
        this.notes_return = c.getString(c.getColumnIndex(Constants.column_notesReturn));

        this.product = new Product(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.column_product)
                )
        );
        this.zone = new Zone(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.column_zone)
                )
        );
        this.devolution = new Devolution(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.column_devolution)
                )
        );
        this.logs_users = c.getString(c.getColumnIndex(Constants.column_logsUsers));
        this.sell = new Sell(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.column_sell)
                )
        );
        this.epc = new Epc(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.column_epc_id)
                )
        );
        this.createdAt = c.getString(c.getColumnIndexOrThrow(Constants.createdAt));
        this.updatedAt = c.getString(c.getColumnIndexOrThrow(Constants.updatedAt));

    }


    @Override
    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(Constants.column_id, id);
        values.put(Constants.column_admissionDate, admission_date);
        values.put(Constants.column_sellDate, sell_date);
        values.put(Constants.column_returnDate, return_date);
        values.put(Constants.column_notesReturn, notes_return);
        values.put(Constants.column_logsUsers, logs_users);
        values.put(Constants.column_product, product.id);
        values.put(Constants.column_zone, zone.id);
        values.put(Constants.column_devolution, devolution.id);
        values.put(Constants.column_sell, sell.id);
        values.put(Constants.column_epc_id, epc.id);
        values.put(Constants.createdAt, createdAt);
        values.put(Constants.updatedAt, updatedAt);
        return values;
    }

    public String getAdmission_date() {
        return admission_date;
    }

    public void setAdmission_date(String admission_date) {
        this.admission_date = admission_date;
    }

    public String getSell_date() {
        return sell_date;
    }

    public void setSell_date(String sell_date) {
        this.sell_date = sell_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getNotes_return() {
        return notes_return;
    }

    public void setNotes_return(String notes_return) {
        this.notes_return = notes_return;
    }

    public String getLogs_users() {
        return logs_users;
    }

    public void setLogs_users(String logs_users) {
        this.logs_users = logs_users;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Devolution getDevolution() {
        return devolution;
    }

    public void setDevolution(Devolution devolution) {
        this.devolution = devolution;
    }

    public Sell getSell() {
        return sell;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }

    public Epc getEpc() {
        return epc;
    }

    public void setEpc(Epc epc) {
        this.epc = epc;
    }

    public Inventory[] getInventories() {
        return inventories;
    }

    public void setInventories(Inventory[] inventories) {
        this.inventories = inventories;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
