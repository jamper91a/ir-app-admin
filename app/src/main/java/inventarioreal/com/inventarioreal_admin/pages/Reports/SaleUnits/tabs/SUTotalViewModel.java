package inventarioreal.com.inventarioreal_admin.pages.Reports.SaleUnits.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SUTotalViewModel extends ViewModel {



    private MutableLiveData<Integer> amountSelled = new MutableLiveData<>();
    private MutableLiveData<Integer> amountReturned = new MutableLiveData<>();
    private MutableLiveData<String> date= new MutableLiveData<>();

    public void setAmountSelled(Integer a) {
        amountSelled.setValue(a);
    }
    public void setAmountReturned(Integer a) {
        amountReturned.setValue(a);
    }

    public void setDate(String a) {
        date.setValue(a);
    }

    public LiveData<Integer> getAmountSelled(){
        return this.amountSelled;
    }
    public LiveData<Integer> getAmountReturned(){
        return this.amountReturned;
    }
    public LiveData<String> getDate(){
        return this.date;
    }
}
