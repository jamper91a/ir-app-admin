package inventarioreal.com.inventarioreal_admin.pages.Inventario.InventarioParcial.CrearInventario.Step2.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epcs;

public class EanPluViewModel extends ViewModel {
    MutableLiveData<LinkedList<Epcs>> epcsLiveData = null;
    private LinkedList<Epcs> epcs = null;

    public void init(){
    }

    public void addEpc(Epcs epc){
        epcs.add(epc);
        epcsLiveData.setValue(epcs);
    }
    public LiveData<LinkedList<Epcs>> getEpcs(){
        if(epcsLiveData==null){
            epcsLiveData = new MutableLiveData<>();
            epcs = new LinkedList<>();
            epcsLiveData.setValue(epcs);
        }
        return epcsLiveData;
    }
}
