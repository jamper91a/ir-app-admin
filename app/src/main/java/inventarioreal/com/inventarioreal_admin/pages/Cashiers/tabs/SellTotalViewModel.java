package inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SellTotalViewModel extends ViewModel {

    private MutableLiveData<Integer> amount= new MutableLiveData<>();

    public void setAmount(Integer a) {
        amount.setValue(a);
    }

    public LiveData<Integer> getAmount(){
        return this.amount;
    }
}
