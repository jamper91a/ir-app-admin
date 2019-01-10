package inventarioreal.com.inventarioreal_admin.pojo.WebServices;

import inventarioreal.com.inventarioreal_admin.pojo.Empleado;

public class LoginResponseWebService {
    private Empleado empleado;
    private String token;

    public LoginResponseWebService() {
    }

    public LoginResponseWebService(Empleado empleado, String token) {
        this.empleado = empleado;
        this.token = token;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
