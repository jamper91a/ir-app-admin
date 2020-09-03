package inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioTotal.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.UltimoInventarioResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class InvTotalEpcViewModel extends ViewModel {

    MutableLiveData<LinkedList<ProductHasZone>> allProductsZonasLiveData = null;
    private LinkedList<ProductHasZone> allProducts= null;
    private MutableLiveData<UltimoInventarioResponse> inventario= new MutableLiveData<>();


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

    public LiveData<UltimoInventarioResponse> getInventario(){
        return this.inventario;
    }
    public void setInventario(UltimoInventarioResponse a) {
        this.inventario.setValue(a);
    }

}
