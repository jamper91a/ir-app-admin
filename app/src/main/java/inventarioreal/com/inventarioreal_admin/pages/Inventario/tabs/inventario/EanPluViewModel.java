package inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventario;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class EanPluViewModel extends ViewModel {

    MutableLiveData<LinkedList<ProductHasZone>> productosZonasLiveData = null;
    private LinkedList<ProductHasZone> productosZonaHasTransferencia = null;



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
}
