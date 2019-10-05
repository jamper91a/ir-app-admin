package inventarioreal.com.inventarioreal_admin.pages.Reports.SaleUnits;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;
import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestSUStep2 implements IntentRequests {
    public Date firstDate=null;
    public Date secondDate=null;

    public RequestSUStep2() {
    }

    public RequestSUStep2(Date firstDate, Date secondDate) {
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
                throw new Error("Se deben seleccionar las dos fecha");
            }
            try {
                if(secondDate.before(firstDate) ||
                        secondDate.equals(firstDate))
                    throw new Error("La fecha final debe ser posterior a la inicial");
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
