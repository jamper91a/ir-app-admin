package inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.VisualizarPorZona.Step2.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;

public class TotalViewModel extends ViewModel {

    private MutableLiveData<Inventarios> amount= new MutableLiveData<>();

    public void stInventario(Inventarios a) {
        amount.setValue(a);
    }

    public LiveData<Inventarios> getInventario(){
        return this.amount;
    }
}
