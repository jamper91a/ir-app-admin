package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;

public class TransfersHasZonesProduct extends InventarioRealPojo {

    public ProductHasZone product;
    public Transfer transfer;
    public boolean state;


    public TransfersHasZonesProduct() {
    }

    public TransfersHasZonesProduct(long id) {
        super(id);
    }

    public ProductHasZone getProduct() {
        return product;
    }

    public void setProduct(ProductHasZone product) {
        this.product = product;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }



    public boolean getState() {
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
