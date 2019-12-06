package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class DiferenceInventoryErp {
    private int id;
    private int total;
    private int erp;
    private String size;
    private String ean;
    private String plu;
    private String plu2;
    private String plu3;
    private String description;
    private String imagen;
    private Product producto;

    public DiferenceInventoryErp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getErp() {
        return erp;
    }

    public void setErp(int erp) {
        this.erp = erp;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagen() {
        if(imagen==null)
            return "";
        else{
            imagen = imagen.replaceAll(" ","%20");
            return Constants.url+imagen;
        }
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Product getProducto() {
        return producto;
    }

    public void setProducto(Product producto) {
        this.producto = producto;
    }
}
