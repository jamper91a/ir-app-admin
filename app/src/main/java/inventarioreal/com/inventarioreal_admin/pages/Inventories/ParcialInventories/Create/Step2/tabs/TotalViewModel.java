package inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;

public class TotalViewModel extends ViewModel {

    private MutableLiveData<Integer> amount= new MutableLiveData<>();
    private MutableLiveData<Inventory> inventory= new MutableLiveData<>();

    public void setAmount(Integer a) {
        amount.setValue(a);
    }

    public void setInventory(Inventory inventory) {
        this.inventory.setValue(inventory);
    }

    public LiveData<Integer> getAmount(){
        return this.amount;
    }

    public MutableLiveData<Inventory> getInventory() {
        return inventory;
    }
}
