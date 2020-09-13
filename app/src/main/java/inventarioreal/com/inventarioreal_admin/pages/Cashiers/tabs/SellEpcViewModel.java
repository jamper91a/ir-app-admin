package inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class SellEpcViewModel extends ViewModel {

    MutableLiveData<LinkedList<ProductHasZone>> allProductsZonasLiveData = null;
    MutableLiveData<Inventory[]> inventario = null;
    private LinkedList<ProductHasZone> allProducts= null;
    private MutableLiveData<String> date= new MutableLiveData<>();


    public void addAllProductoZona(ProductHasZone product){
        getAllProducts();

        allProducts.add(product);
        allProductsZonasLiveData.setValue(allProducts);

    }

    public LiveData<LinkedList<ProductHasZone>> getAllProducts(){
        if(allProductsZonasLiveData ==null){
            allProductsZonasLiveData = new MutableLiveData<>();
            allProducts = new LinkedList<>();
            allProductsZonasLiveData.setValue(allProducts);
        }
        return allProductsZonasLiveData;
    }

    public LiveData<Inventory[]> getInventario() {
        if(inventario == null) {
            inventario = new MediatorLiveData<>();
        }
        return inventario;
    }

    public void setInventario(Inventory[] inventario) {
        this.inventario.setValue(inventario);
    }

    public LiveData<String> getDate(){
        return this.date;
    }
    public void setDate(String a) {
        date.setValue(a);
    }
    public void clean(){
        if(allProductsZonasLiveData ==null){
            allProductsZonasLiveData = new MutableLiveData<>();
            allProducts = new LinkedList<>();
            allProductsZonasLiveData.setValue(allProducts);
        }else{
            allProducts = new LinkedList<>();
            allProductsZonasLiveData.setValue(allProducts);
        }
        if(inventario!=null)
        {
            inventario.setValue(new Inventory[0]);
        }
    }

}
