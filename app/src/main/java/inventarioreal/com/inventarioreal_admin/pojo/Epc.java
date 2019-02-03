package inventarioreal.com.inventarioreal_admin.pojo;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Epc {
	private long id;
	private int state;
	private String epc;
	private Compania companias_id;
	private int count;
	private String createdAt;
	private String modifiedAt;

	public Epc() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Compania getCompanias_id() {
		return companias_id;
	}

	public void setCompanias_id(Compania companias_id) {
		this.companias_id = companias_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	@Override
	public String toString() {
		return "Epc [id=" + id + ", epc=" + epc + ", count=" + count + "]";
	}

	public HashMap<String,String> toHashMap(){
		HashMap<String,String> aux = new HashMap<String, String>();
		aux.put(Constants.name, getEpc());
		aux.put(Constants.id, getId()+"");

		return aux;
	}


	
	

}
