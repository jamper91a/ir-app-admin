package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

public class ReportsHasProductsZone extends InventarioRealPojo {

    public ProductHasZone product;
    public Report report;
    public Employee homologatorEmployee;
    public boolean state;


    public ReportsHasProductsZone() {
    }

    public ReportsHasProductsZone(long id) {
        super(id);
    }

    public ProductHasZone getProduct() {
        return product;
    }

    public void setProduct(ProductHasZone product) {
        this.product = product;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Employee getHomologatorEmployee() {
        return homologatorEmployee;
    }

    public void setHomologatorEmployee(Employee homologatorEmployee) {
        this.homologatorEmployee = homologatorEmployee;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
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
