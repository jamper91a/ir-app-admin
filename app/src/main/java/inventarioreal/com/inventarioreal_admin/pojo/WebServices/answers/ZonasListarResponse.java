package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;

public class ZonasListarResponse {
    private Zonas[] zonas;

    public ZonasListarResponse() {
    }

    public Zonas[] getZonas() {
        return zonas;
    }

    public void setZonas(Zonas[] zonas) {
        this.zonas = zonas;
    }

    public ZonasListarResponse(Zonas[] zonas) {
        this.zonas = zonas;
    }
}
