package inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class SellEpcViewModel extends ViewModel {

    MutableLiveData<LinkedList<ProductHasZone>> allProductsZonasLiveData = null;
    MutableLiveData<Inventory[]> inventario = null;
    private LinkedList<ProductHasZone> allProducts= null;



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
        return inventario;
    }

    public void setInventario(Inventory[] inventario) {
        this.inventario.setValue(inventario);
    }

}
