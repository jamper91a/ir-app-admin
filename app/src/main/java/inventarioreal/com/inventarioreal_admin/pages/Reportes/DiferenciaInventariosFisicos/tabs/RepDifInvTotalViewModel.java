package inventarioreal.com.inventarioreal_admin.pages.Reportes.DiferenciaInventariosFisicos.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class RepDifInvTotalViewModel extends ViewModel {



    private MutableLiveData<Integer> amount= new MutableLiveData<>();
    private MutableLiveData<String> date= new MutableLiveData<>();

    public void setAmount(Integer a) {
        amount.setValue(a);
    }
    public void setDate(String a) {
        date.setValue(a);
    }

    public LiveData<Integer> getAmount(){
        return this.amount;
    }
    public LiveData<String> getDate(){
        return this.date;
    }
}
