package inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventariosConsolidados;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosConsolidados;

public class TotalConsolidadoViewModel extends ViewModel {

    private MutableLiveData<InventariosConsolidados> amount= new MutableLiveData<>();

    public void stInventario(InventariosConsolidados a) {
        amount.setValue(a);
    }

    public LiveData<InventariosConsolidados> getInventario(){
        return this.amount;
    }
}
