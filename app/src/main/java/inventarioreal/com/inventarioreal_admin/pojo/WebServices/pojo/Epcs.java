package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Epcs extends InventarioRealPojo {
	public static final String TAG = "Epcs";
	public int state;
	public String epc;
	public Companias companias_id;

	//Atributo especial que se usa en las listas
	private int count;

	public Epcs() {
	}

    public Epcs(long id) {
        super(id);
    }

    @Override
    public void fromHashMap(Class myClass, HashMap<String, String> data) {

    }

    @Override
    public void toHashMap(Class myClass) {

    }

    public static String getTAG() {
        return TAG;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public Companias getCompanias_id() {
        return companias_id;
    }

    public void setCompanias_id(Companias companias_id) {
        this.companias_id = companias_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ContentValues getContentValues(){
		ContentValues values = new ContentValues();
		values.put(Constants.id,id);
		values.put(Constants.state,state);
		values.put(Constants.epc,epc);
		values.put(Constants.companias_id,companias_id.id);
		values.put(Constants.createdAt,createdAt);
		values.put(Constants.updatedAt,updatedAt);

		return values;
	}

    @Override
    public void fromCursor(Cursor c) {

        this.id = c.getLong(c.getColumnIndexOrThrow(Constants.id));
        this.state = c.getInt(c.getColumnIndexOrThrow(Constants.id));
        this.epc = c.getString(c.getColumnIndexOrThrow(Constants.id));
        this.companias_id = new Companias(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.companias_id)
                )
        );
        this.createdAt = c.getString(c.getColumnIndexOrThrow(Constants.createdAt));
        this.updatedAt = c.getString(c.getColumnIndexOrThrow(Constants.updatedAt));
    }

    @Override
    public String toString() {
        return "Epcs{" +
                "epc='" + epc + '\'' +
                '}';
    }
}
