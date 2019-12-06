package inventarioreal.com.inventarioreal_admin.pages.Reports.RotationUnits;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestRUStep2 implements IntentRequests {
    private Date firstDate=null;
    private Date secondDate=null;
    private transient Context context = null;

    public RequestRUStep2(Context c) {
        this.context = c;
    }

    public RequestRUStep2(Context c, Date firstDate, Date secondDate) {
        this.context = c;
        this.firstDate = firstDate;
        this.secondDate = secondDate;
    }

    public Date getFirstDate() {
        return firstDate;
    }
    public String getFirstDateToString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(getFirstDate());
        return date;
    }

    public void setFirstDate(String firstDate) {
        Date date= null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(firstDate);
        } catch (ParseException e) {
            date = null;
        }
        this.firstDate = date;
    }

    public Date getSecondDate() {
        return secondDate;
    }

    public String getSecondDateToString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(getSecondDate());
        return date;
    }

    public void setSecondDate(String secondDate) {
        Date date= null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(secondDate);
        } catch (ParseException e) {
            date = null;
        }
        this.secondDate = date;
    }

    @Override
    public boolean validar() throws Error{
        try {
            if(firstDate==null || secondDate==null)
            {
                throw new Error(this.context.getString(R.string.error_se_deben_seleccionar_todos_los_datos));

            }
            try {
                if(secondDate.before(firstDate) ||
                        secondDate.equals(firstDate))
                    throw new Error(this.context.getString(R.string.error_fecha_final_superior));
            } catch (Exception e) {

                throw e;
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
