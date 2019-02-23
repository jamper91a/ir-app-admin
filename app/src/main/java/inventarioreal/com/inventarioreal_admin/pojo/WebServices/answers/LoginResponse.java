package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Empleados;

public class LoginResponse {
    private Empleados empleado;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(Empleados empleado, String token) {
        this.empleado = empleado;
        this.token = token;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
