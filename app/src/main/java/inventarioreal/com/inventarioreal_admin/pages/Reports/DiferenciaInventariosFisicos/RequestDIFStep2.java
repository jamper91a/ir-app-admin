package inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos;

import android.content.Context;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;
import inventarioreal.com.inventarioreal_admin.util.IntentRequests;


public class RequestDIFStep2 implements IntentRequests {
    private static final String TAG = "RequestDIFStep2";
    private ConsolidatedInventory inventarioInicial=null;
    private ConsolidatedInventory inventarioFinal=null;
    private transient Context context= null;
    public RequestDIFStep2() {
    }

    public RequestDIFStep2(Context context, ConsolidatedInventory inventarioInicial, ConsolidatedInventory inventarioFinal) {
        this.context = context;
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
                throw new Error(this.context.getString(R.string.error_se_deben_seleccionar_dos_inventarios));
            }
            if(inventarioInicial.getId()== inventarioFinal.getId()){
                throw new Error(this.context.getString(R.string.error_se_deben_seleccionar_inventario_diferentes));
            }
            try {
                if(inventarioFinal.getDateCreatedAt().before(inventarioInicial.getDateCreatedAt()) ||
                         inventarioFinal.getDateCreatedAt().equals(inventarioInicial.getDateCreatedAt()))
                throw new Error(this.context.getString(R.string.error_inventario_final_posterior_inicial));
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
