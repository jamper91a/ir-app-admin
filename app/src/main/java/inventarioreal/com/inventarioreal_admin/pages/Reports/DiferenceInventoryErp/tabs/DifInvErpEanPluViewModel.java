package inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenceInventoryErp.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.DiferenceInventoryErp;

public class DifInvErpEanPluViewModel extends ViewModel {

    MutableLiveData<LinkedList<DiferenceInventoryErp>> productsLiveData = null;
    private LinkedList<DiferenceInventoryErp> products = null;



    public void addProduct(DiferenceInventoryErp product){
        if(products ==null)
            products = new LinkedList<>();
        products.add(product);
        productsLiveData.setValue(products);
    }

    public LiveData<LinkedList<DiferenceInventoryErp>> getProducts(){
        if(productsLiveData ==null){
            productsLiveData = new MutableLiveData<>();
            products = new LinkedList<>();
            productsLiveData.setValue(products);
        }
        return productsLiveData;
    }

}
