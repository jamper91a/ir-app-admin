package inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventariosConsolidados;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;

public class TotalConsolidadoViewModel extends ViewModel {

    private MutableLiveData<ConsolidatedInventory> amount= new MutableLiveData<>();

    public void setInventario(ConsolidatedInventory a) {
        amount.setValue(a);
    }

    public LiveData<ConsolidatedInventory> getInventario(){
        return this.amount;
    }
}
