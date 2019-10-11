package inventarioreal.com.inventarioreal_admin.pages.Reports.RotationProyected;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestRPStep2 implements IntentRequests {
    private Product product=null;
    private int days = 0;
    private int total = 0;

    public RequestRPStep2() {
    }

    public RequestRPStep2(Product product, int days) {
        this.product = product;
        this.days = days;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public boolean validar() throws Error{
        try {
            if(product==null || days==0 )
            {
                throw new Error("Se deben seleccionar el producto y los dias");
            }
            return true;
        } catch (Error error) {
            throw error;
        }
    }

    @Override
    public HashMap<String, String> getAtributos() {
        return null;
    }
}
