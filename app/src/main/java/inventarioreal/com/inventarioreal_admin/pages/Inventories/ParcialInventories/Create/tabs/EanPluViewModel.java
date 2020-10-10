package inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;

public class EanPluViewModel extends ViewModel {
    MutableLiveData<LinkedList<ProductHasZone>> productosZonasLiveData = null;
    MutableLiveData<Inventory> inventario = null;
    MutableLiveData<Transfer> transfer = null;
    private LinkedList<ProductHasZone> productosZona = null;



    public void addProductoZona(ProductHasZone productosZonas){
        //Valido si ya se ha agregado el producto
        int count=0;
        if(productosZonas.getProduct()!=null){
            for (ProductHasZone pz: productosZona
            ) {
                if(pz.getProduct().getId() == productosZonas.getProduct().getId()){
                    pz.setTotal(pz.getTotal()+1);
                    productosZona.set(count,pz);
                    productosZonasLiveData.setValue(productosZona);
                    return;
                }
                count+=1;
            }
            productosZona.add(productosZonas);
            productosZonasLiveData.setValue(productosZona);
        }

    }
    public LiveData<LinkedList<ProductHasZone>> getProductosZona(){
        if(productosZonasLiveData ==null){
            productosZonasLiveData = new MutableLiveData<>();
            productosZona = new LinkedList<>();
            productosZonasLiveData.setValue(productosZona);
        }
        return productosZonasLiveData;
    }

    public LiveData<Inventory> getInventario() {
        if(this.inventario == null)
            this.inventario = new MutableLiveData<>();
        return inventario;
    }

    public void setInventory(Inventory inventario) {
        if(this.inventario == null)
            this.inventario = new MutableLiveData<>();
        this.inventario.setValue(inventario);
    }

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

    public void clean(){
        productosZona = new LinkedList<>();
        productosZonasLiveData.setValue(productosZona);
    }
}
