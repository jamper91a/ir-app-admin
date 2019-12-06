package inventarioreal.com.inventarioreal_admin.pages.Reports.RotationProyected;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestRPStep2 implements IntentRequests {
    private Product product=null;
    private int days = 0;
    private int total = 0;
    private transient Context context = null;

    public RequestRPStep2(Context c) {
        this.context = c;
    }

    public RequestRPStep2(Context c,Product product, int days) {
        this.context = c;
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public boolean validar() throws Error{
        try {
            if(product==null || days==0 )
            {
                throw new Error(this.context.getString(R.string.error_se_deben_seleccionar_todos_los_datos));
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
