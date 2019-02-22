package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.util.Log;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Epc {
	private static final String TAG = "Epc";
	private long id;
	private int state;
	private String epc;
	private Compania companias_id;
	private String createdAt;
	private String updatedAt;

	//Atributo especial que se usa en las listas
	private int count;

	public Epc() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public void setId(String id) {
		try {
			this.id = Long.parseLong(id);
		} catch (NumberFormatException e) {
			this.id=0;
		}
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	public void setState(String state) {
		try {
			this.state = Integer.parseInt(state);
		} catch (NumberFormatException e) {
			this.state=-1;
		}
	}

	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}

	public Compania getCompanias_id() {
		return companias_id;
	}

	public void setCompanias_id(Compania companias_id) {
		this.companias_id = companias_id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Epc [id=" + id + ", epc=" + epc +  "]";
	}

	public HashMap<String,String> toHashMap(){
		HashMap<String,String> aux = new HashMap<String, String>();
		aux.put(Constants.name, getEpc());
		aux.put(Constants.id, getId()+"");

		return aux;
	}
	public ContentValues getContentValues(){
		ContentValues values = new ContentValues();
		values.put(Constants.id,getId());
		values.put(Constants.state,getEpc());
		values.put(Constants.epc,getEpc());
		values.put(Constants.companias_id,getCompanias_id().getId());
		values.put(Constants.createdAt,getCreatedAt());
		values.put(Constants.updatedAt,getUpdatedAt());

		return values;
	}

	public void setFromHashMap(HashMap<String, String> data){
		try {
			if(data!=null)
			{
				setId(data.get(Constants.id));
				setState(data.get(Constants.state));
				setEpc(data.get(Constants.epc));
	//		setCompanias_id(data.get(Constants.state));
				setCreatedAt(data.get(Constants.createdAt));
				setUpdatedAt(data.get(Constants.updatedAt));
			}else{
				setId(0);
			}
		} catch (Exception e) {
			Log.e(TAG,e.getMessage());
			setId(0);
		}
	}


	
	

}
