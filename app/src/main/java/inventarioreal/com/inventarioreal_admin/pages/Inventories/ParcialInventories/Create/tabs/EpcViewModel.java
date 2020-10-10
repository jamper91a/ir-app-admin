package inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;

public class EpcViewModel extends ViewModel {

    MutableLiveData<LinkedList<ProductHasZone>> allProductsZonasLiveData = null;
    MutableLiveData<Inventory> inventario = null;
    MutableLiveData<Transfer> transfer = null;
    private LinkedList<ProductHasZone> allProducts= null;
    private MutableLiveData<String> date= new MutableLiveData<>();



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

    public LiveData<Inventory> getInventario() {
        if(this.inventario == null)
            this.inventario = new MutableLiveData<>();
        return inventario;
    }

    public void setInventario(Inventory inventario) {
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
//        if(inventario!=null)
//        {
//            inventario.setValue(new Inventory());
//        }
    }

    public void setDate(String date) { this.date.setValue(date);}

    public LiveData<String> getDate(){ return this.date;}

    public MutableLiveData<Transfer> getTransfer() {
        if(this.transfer == null)
            this.transfer= new MutableLiveData<>();
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        if(this.transfer == null)
            this.transfer= new MutableLiveData<>();
        this.transfer.setValue(transfer);
    }

}
