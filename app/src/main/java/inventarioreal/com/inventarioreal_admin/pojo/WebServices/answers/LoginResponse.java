package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation.Empleado;

public class LoginResponse {
    private Empleado empleado;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(Empleado empleado, String token) {
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
