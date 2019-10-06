package inventarioreal.com.inventarioreal_admin.pages.Reports.Returns;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestREStep2 implements IntentRequests {
    private Date firstDate=null;
    private Date secondDate=null;
    private int type = 0;

    public RequestREStep2() {
    }

    public RequestREStep2(Date firstDate, Date secondDate, int type) {
        this.firstDate = firstDate;
        this.secondDate = secondDate;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean validar() throws Error{
        try {
            if(firstDate==null || secondDate==null || type<=0)
            {
                throw new Error("Se deben seleccionar las dos fecha y el tipo");
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
