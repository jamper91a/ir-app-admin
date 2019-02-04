package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import java.util.ArrayList;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.Zona;

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
