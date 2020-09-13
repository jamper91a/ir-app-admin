package inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;

public class SellTotalViewModel extends ViewModel {

    private MutableLiveData<Integer> amount= new MutableLiveData<>();
    private MutableLiveData<String> date= new MutableLiveData<>();
    public void setAmount(Integer a) {
        amount.setValue(a);
    }

    public LiveData<Integer> getAmount(){
        return this.amount;
    }

    public LiveData<String> getDate(){
        return this.date;
    }
    public void setDate(String a) {
        date.setValue(a);
    }
}
