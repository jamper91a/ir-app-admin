package inventarioreal.com.inventarioreal_admin.pages.Reportes.InventarioTotal.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class InvTotalEanPluViewModel extends ViewModel {

    MutableLiveData<LinkedList<ProductHasZone>> productosZonasLiveData = null;
    MutableLiveData<Inventory[]> inventario = null;
    private LinkedList<ProductHasZone> productosZona = null;



    public void addProductoZona(ProductHasZone productosZonas){
        if(productosZona==null)
            productosZona = new LinkedList<>();
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

    public LiveData<Inventory[]> getInventario() {
        return inventario;
    }

    public void setInventario(Inventory[] inventario) {
        this.inventario.setValue(inventario);
    }

}
