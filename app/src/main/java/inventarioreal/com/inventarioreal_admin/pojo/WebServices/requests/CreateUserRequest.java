package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import jamper91.com.easyway.Util.Administrador;

public class CreateUserRequest implements WebServiceRequest {
    private String name = "";
    private String username = "";
    private String password = "";
    private String rpassword = "";
    private long shop = 0;
    private long company = 0;
    private long type = 0;
    private Context context;
    private Administrador admin;

    public CreateUserRequest(Context context, Administrador admin) {
        this.context = context;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRpassword() {
        return rpassword;
    }

    public void setRpassword(String rpassword) {
        this.rpassword = rpassword;
    }

    public long getShop() {
        return shop;
    }

    public void setShop(long shop) {
        this.shop = shop;
    }

    public long getCompany() {
        return company;
    }

    public void setCompany(long company) {
        this.company = company;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = null;
        try {
            campos = new HashMap<>();
            JSONObject user = new JSONObject();
            user.put(Constants.name, this.getName());
            user.put(Constants.username, this.getUsername());
            user.put(Constants.password, this.getPassword());
            user.put(Constants.rpassword, this.getRpassword());
            user.put(Constants.group, this.getType());

            JSONObject employee = new JSONObject();
            employee.put(Constants.company,  this.getCompany());
            employee.put(Constants.shop, this.getShop());


            campos.put(Constants.user, user);
            campos.put(Constants.employee,employee);
            return campos;
        } catch (JSONException e) {
            return null;
        }

    }

    public boolean validar() throws Error{
        if(this.getShop()<=0 || this.getCompany()<=0 || this.getType()<=0){
            throw new Error(this.context.getString(R.string.error_se_deben_seleccionar_todos_los_datos));
        }
        if(this.getUsername().isEmpty() || this.getPassword().isEmpty() || this.getRpassword().isEmpty()){
            throw new Error(this.context.getString(R.string.error_se_deben_seleccionar_todos_los_datos));
        }

        if(!admin.validar_email(this.getUsername())){
            throw new Error(this.context.getString(R.string.error_email_valido));
        }

        if(this.getPassword().equals(this.getRpassword())==false){
            throw new Error(this.context.getString(R.string.error_contrasena_no_coincide));
        }

        return true;
    }

}

