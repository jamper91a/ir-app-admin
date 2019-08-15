package inventarioreal.com.inventarioreal_admin.pages.Reportes.DiferenciaInventariosFisicos;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosConsolidados;
import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestDIFStep2 implements IntentRequests {
    private static final String TAG = "RequestDIFStep2";
    public InventariosConsolidados inventarioInicial=null;
    public InventariosConsolidados inventarioFinal=null;

    public RequestDIFStep2() {
    }

    public RequestDIFStep2(InventariosConsolidados inventarioInicial, InventariosConsolidados inventarioFinal) {
        this.inventarioInicial = inventarioInicial;
        this.inventarioFinal = inventarioFinal;
    }

    public InventariosConsolidados getInventarioInicial() {
        return inventarioInicial;
    }

    public void setInventarioInicial(InventariosConsolidados inventarioInicial) {
        this.inventarioInicial = inventarioInicial;
    }

    public InventariosConsolidados getInventarioFinal() {
        return inventarioFinal;
    }

    public void setInventarioFinal(InventariosConsolidados inventarioFinal) {
        this.inventarioFinal = inventarioFinal;
    }

    @Override
    public boolean validar() throws Error{
        try {
            if(inventarioInicial==null || inventarioInicial==null)
            {
                throw new Error("Se deben seleccionar los dos inventarios");
            }
            if(inventarioInicial.id== inventarioFinal.id){
                throw new Error("Se deben seleccionar inventarios diferentes");
            }
            try {
                if(inventarioFinal.getDateCreatedAt().before(inventarioInicial.getDateCreatedAt()) ||
                         inventarioFinal.getDateCreatedAt().equals(inventarioInicial.getDateCreatedAt()))
                    throw new Error("El inventario final debe ser posterior al inicial");
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
