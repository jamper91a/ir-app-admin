package inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.Create.Step2.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class EanPluViewModel extends ViewModel {
    MutableLiveData<LinkedList<ProductHasZone>> productosZonasLiveData = null;
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
}
