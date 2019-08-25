package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Product extends InventarioRealPojo {

    private String ean;
    private String plu;
    private String plu2;
    private String plu3;
    private String branch;
    private String gender;
    private String color;
    private String size;
    private String category;
    private String description;
    private long amount;
    private String imagen;
    private double cost_price;
    private double sell_price;
    private Company company;
    private Supplier supplier;

    public Product() {
    }

    public Product(long id){
        super(id);
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getPlu() {
        return plu;
    }

    public void setPlu(String plu) {
        this.plu = plu;
    }

    public String getPlu2() {
        return plu2;
    }

    public void setPlu2(String plu2) {
        this.plu2 = plu2;
    }

    public String getPlu3() {
        return plu3;
    }

    public void setPlu3(String plu3) {
        this.plu3 = plu3;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getCost_price() {
        return cost_price;
    }

    public void setCost_price(double cost_price) {
        this.cost_price = cost_price;
    }

    public double getSell_price() {
        return sell_price;
    }

    public void setSell_price(double sell_price) {
        this.sell_price = sell_price;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getImagen() {
        if(imagen==null)
            return "";
        else
            return imagen;
    }



    @Override
    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();

        values.put(Constants.column_id,id);
        values.put(Constants.column_ean,ean);
        values.put(Constants.column_plu,plu);
        values.put(Constants.column_plu2,plu2);
        values.put(Constants.column_plu3,plu3);
        values.put(Constants.column_branch, branch);
        values.put(Constants.column_gender, gender);
        values.put(Constants.column_color,color);
        values.put(Constants.column_size, size);
        values.put(Constants.column_category, category);
        values.put(Constants.column_description, description);
        values.put(Constants.column_amount, amount);
        values.put(Constants.column_imagen,imagen);
        values.put(Constants.column_costPrice, cost_price);
        values.put(Constants.column_sellPrice, sell_price);
        values.put(Constants.column_company,company.id);
        values.put(Constants.column_supplier,supplier.id);
        values.put(Constants.createdAt,createdAt);
        values.put(Constants.updatedAt,updatedAt);

        return values;
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
        this.ean = c.getString(c.getColumnIndexOrThrow(Constants.column_ean));
        this.plu = c.getString(c.getColumnIndexOrThrow(Constants.column_plu));
        this.plu2 = c.getString(c.getColumnIndexOrThrow(Constants.column_plu2));
        this.plu3 = c.getString(c.getColumnIndexOrThrow(Constants.column_plu3));
        this.branch = c.getString(c.getColumnIndexOrThrow(Constants.column_branch));
        this.gender = c.getString(c.getColumnIndexOrThrow(Constants.column_gender));
        this.color = c.getString(c.getColumnIndexOrThrow(Constants.column_color));
        this.size = c.getString(c.getColumnIndexOrThrow(Constants.column_size));
        this.category = c.getString(c.getColumnIndexOrThrow(Constants.column_category));
        this.description = c.getString(c.getColumnIndexOrThrow(Constants.column_description));
        this.amount = c.getLong(c.getColumnIndexOrThrow(Constants.column_amount));
        this.imagen = c.getString(c.getColumnIndexOrThrow(Constants.column_imagen));
        this.cost_price = c.getDouble(c.getColumnIndexOrThrow(Constants.column_costPrice));
        this.sell_price = c.getDouble(c.getColumnIndexOrThrow(Constants.column_sellPrice));
        this.company = new Company(c.getLong(c.getColumnIndexOrThrow(Constants.column_company)));
        this.createdAt = c.getString(c.getColumnIndexOrThrow(Constants.createdAt));
        this.updatedAt = c.getString(c.getColumnIndexOrThrow(Constants.updatedAt));
    }
}
