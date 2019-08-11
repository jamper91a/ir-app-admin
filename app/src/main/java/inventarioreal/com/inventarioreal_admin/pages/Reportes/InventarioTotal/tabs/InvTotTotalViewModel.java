package inventarioreal.com.inventarioreal_admin.pages.Reportes.InventarioTotal.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.UltimoInventarioResponse;

public class InvTotTotalViewModel extends ViewModel {

    private MutableLiveData<UltimoInventarioResponse> amount= new MutableLiveData<>();

    public void setInventario(UltimoInventarioResponse a) {
        amount.setValue(a);
    }

    public LiveData<UltimoInventarioResponse> getInventario(){
        return this.amount;
    }
}
