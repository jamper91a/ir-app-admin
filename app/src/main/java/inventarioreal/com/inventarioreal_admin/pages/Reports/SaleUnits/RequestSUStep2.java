package inventarioreal.com.inventarioreal_admin.pages.Reports.SaleUnits;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;
import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestSUStep2 implements IntentRequests {
    public ConsolidatedInventory inventarioInicial=null;
    public ConsolidatedInventory inventarioFinal=null;

    public RequestSUStep2() {
    }

    public RequestSUStep2(ConsolidatedInventory inventarioInicial, ConsolidatedInventory inventarioFinal) {
        this.inventarioInicial = inventarioInicial;
        this.inventarioFinal = inventarioFinal;
    }

    public ConsolidatedInventory getInventarioInicial() {
        return inventarioInicial;
    }

    public void setInventarioInicial(ConsolidatedInventory inventarioInicial) {
        this.inventarioInicial = inventarioInicial;
    }

    public ConsolidatedInventory getInventarioFinal() {
        return inventarioFinal;
    }

    public void setInventarioFinal(ConsolidatedInventory inventarioFinal) {
        this.inventarioFinal = inventarioFinal;
    }

    @Override
    public boolean validar() throws Error{
        try {
            if(inventarioInicial==null || inventarioInicial==null)
            {
                throw new Error("Se deben seleccionar los dos inventarios");
            }
            if(inventarioInicial.getId()== inventarioFinal.getId()){
                throw new Error("Se deben seleccionar inventory diferentes");
            }
            try {
                if(inventarioFinal.getDateCreatedAt().before(inventarioInicial.getDateCreatedAt()) ||
                         inventarioFinal.getDateCreatedAt().equals(inventarioInicial.getDateCreatedAt()))
                    throw new Error("El inventory final debe ser posterior al inicial");
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
