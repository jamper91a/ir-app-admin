package inventarioreal.com.inventarioreal_admin.pages.Transferencias.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.TransferenciaDetails;

public class TransferenciaDetailsEanPluViewModel extends ViewModel {

    private MutableLiveData<TransferenciaDetails> transferencia= new MutableLiveData<>();

    public void setTransferencia(TransferenciaDetails t) {
        transferencia.setValue(t);
    }

    public LiveData<TransferenciaDetails> getTransferencia(){
        return this.transferencia;
    }
}
