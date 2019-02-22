package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation.Zona;

public class ZonasListarResponse {
    private Zona[] zonas;

    public ZonasListarResponse() {
    }

    public Zona[] getZonas() {
        return zonas;
    }

    public void setZonas(Zona[] zonas) {
        this.zonas = zonas;
    }

    public ZonasListarResponse(Zona[] zonas) {
        this.zonas = zonas;
    }
}
