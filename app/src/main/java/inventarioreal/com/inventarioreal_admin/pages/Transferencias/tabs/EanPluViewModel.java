package inventarioreal.com.inventarioreal_admin.pages.Transferencias.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonasHasTransferencias;

public class EanPluViewModel extends ViewModel {
    MutableLiveData<LinkedList<ProductosZonasHasTransferencias>> productosZonasHasTransferenciasLiveData = null;
    private LinkedList<ProductosZonasHasTransferencias> productosZonaHasTransferencias = null;



    public void addProductoZonaHasTransferencia(ProductosZonasHasTransferencias productosZonasHasTransferencia){
        //Valido si ya se ha agregado el producto
        int count=0;
        if(productosZonasHasTransferencia.getProductos_zona_id().getProductos_id()!=null){
//            for (ProductosZonasHasTransferencias pzt: productosZonaHasTransferencias
//                 ) {
//                if(pzt.getProductos_zona_id().getProductos_id().getId() == productosZonasHasTransferencia.getProductos_zona_id().getProductos_id().getId()){
//                    pzt.getProductos_zona_id().setTotal(pzt.getProductos_zona_id().getTotal()+1);
//                    productosZonaHasTransferencias.set(count,pzt);
//                    productosZonasHasTransferenciasLiveData.setValue(productosZonaHasTransferencias);
//                    return;
//                }
//                count+=1;
//            }
            productosZonaHasTransferencias.add(productosZonasHasTransferencia);
            productosZonasHasTransferenciasLiveData.setValue(productosZonaHasTransferencias);
        }

    }
    public LiveData<LinkedList<ProductosZonasHasTransferencias>> getProductosZonaHasTransferencia(){
        if(productosZonasHasTransferenciasLiveData ==null){
            productosZonasHasTransferenciasLiveData = new MutableLiveData<>();
            productosZonaHasTransferencias = new LinkedList<>();
            productosZonasHasTransferenciasLiveData.setValue(productosZonaHasTransferencias);
        }
        return productosZonasHasTransferenciasLiveData;
    }
}
