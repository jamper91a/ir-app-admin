package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;

public class ZonasListarResponse {
    private Zone[] zonas;

    public ZonasListarResponse() {
    }

    public Zone[] getZonas() {
        return zonas;
    }

    public void setZonas(Zone[] zonas) {
        this.zonas = zonas;
    }

    public ZonasListarResponse(Zone[] zonas) {
        this.zonas = zonas;
    }
}
