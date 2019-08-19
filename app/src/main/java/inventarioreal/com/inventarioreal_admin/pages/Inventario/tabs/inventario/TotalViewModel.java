package inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventario;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;

public class TotalViewModel extends ViewModel {

    private MutableLiveData<Inventory> amount= new MutableLiveData<>();

    public void stInventario(Inventory a) {
        amount.setValue(a);
    }

    public LiveData<Inventory> getInventario(){
        return this.amount;
    }
}
