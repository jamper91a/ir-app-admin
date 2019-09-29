package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Epc extends InventarioRealPojo {
	private int state;
    private Company company;
    private String epc;
    //Extra field to determinated if there is any error with this product such as does not belong to
    //the same comapny that the current use
    private boolean error=false;

	//Atributo especial que se usa en las listas
	private int count;

	public Epc() {
	}

    public Epc(long id) {
        super(id);
    }

    @Override
    public void fromHashMap(Class myClass, HashMap<String, String> data) {

    }

    @Override
    public void toHashMap(Class myClass) {

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ContentValues getContentValues(){
		ContentValues values = new ContentValues();
		values.put(Constants.column_id,getId());
		values.put(Constants.column_state,state);
		values.put(Constants.column_epc,epc);
		values.put(Constants.column_company, company.getId());
		values.put(Constants.createdAt,getCreatedAt());
		values.put(Constants.updatedAt,getUpdatedAt());

		return values;
	}

    @Override
    public void fromCursor(Cursor c) {

        this.setId(c.getLong(c.getColumnIndexOrThrow(Constants.column_id)));
        this.state = c.getInt(c.getColumnIndexOrThrow(Constants.column_state));
        this.epc = c.getString(c.getColumnIndexOrThrow(Constants.column_epc));
        this.company = new Company(
                c.getLong(
                        c.getColumnIndexOrThrow(Constants.column_company)
                )
        );
        this.setCreatedAt(c.getString(c.getColumnIndexOrThrow(Constants.createdAt)));
        this.setUpdatedAt(c.getString(c.getColumnIndexOrThrow(Constants.updatedAt)));
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Epcs{" +
                "epc='" + epc + '\'' +
                '}';
    }
}
