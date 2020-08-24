package inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventariosConsolidados;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class EpcConsolidadoViewModel extends ViewModel {

    MutableLiveData<LinkedList<ProductHasZone>> allProductsZonasLiveData = null;
    MutableLiveData<ConsolidatedInventory> inventario = null;
    private LinkedList<ProductHasZone> allProducts= null;



    public void addProductoZona(ProductHasZone product){
        getAllProducts();

        allProducts.add(product);
        allProductsZonasLiveData.setValue(allProducts);

    }

    public void removeProductoZona(ProductHasZone product){
        getAllProducts();
        for(ProductHasZone pz: allProducts){
            if(pz.getId() == product.getId()){
                allProducts.remove(pz);
            }
        }
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

    public LiveData<ConsolidatedInventory> getInventario() {
        if(this.inventario == null)
            this.inventario = new MutableLiveData<>();
        return inventario;
    }

    public void setInventario(ConsolidatedInventory inventario) {
        if(this.inventario == null)
            this.inventario = new MutableLiveData<>();
        this.inventario.setValue(inventario);
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
            inventario.setValue(new ConsolidatedInventory(0));
        }
    }

}
