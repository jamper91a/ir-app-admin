package inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventario;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class EanPluViewModel extends ViewModel {

    MutableLiveData<LinkedList<ProductHasZone>> productosZonasLiveData = null;
    private LinkedList<ProductHasZone> productosZonaHasTransferencia = null;
    MutableLiveData<Inventory> inventario = null;



    public void addProductoZona(ProductHasZone productosZonas){
        //Valido si ya se ha agregado el producto
        int count=0;
        if(productosZonas.getProduct()!=null){
            for (ProductHasZone pz: productosZonaHasTransferencia
            ) {
                if(pz.getProduct().getId() == productosZonas.getProduct().getId()){
                    pz.setTotal(pz.getTotal()+1);
                    productosZonaHasTransferencia.set(count,pz);
                    productosZonasLiveData.setValue(productosZonaHasTransferencia);
                    return;
                }
                count+=1;
            }
            productosZonaHasTransferencia.add(productosZonas);
            productosZonasLiveData.setValue(productosZonaHasTransferencia);
        }

    }
    public LiveData<LinkedList<ProductHasZone>> getProductosZona(){
        if(productosZonasLiveData ==null){
            productosZonasLiveData = new MutableLiveData<>();
            productosZonaHasTransferencia = new LinkedList<>();
            productosZonasLiveData.setValue(productosZonaHasTransferencia);
        }
        return productosZonasLiveData;
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

}
