package inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;

public class TransEanPluViewModel extends ViewModel {
    MutableLiveData<LinkedList<TransfersHasZonesProduct>> productosZonasHasTransferenciasLiveData = null;
    private LinkedList<TransfersHasZonesProduct> productosZonaHasTransferencias = null;



    public void addProductoZonaHasTransferencia(TransfersHasZonesProduct productosZonasHasTransferencia){
        //Valido si ya se ha agregado el producto
        int count=0;
        if(productosZonasHasTransferencia.getProduct().getProduct()!=null){
//            for (ProductosZonasHasTransferencias pzt: productosZonaHasTransferencias
//                 ) {
//                if(pzt.getProduct().getProduct().getId() == productosZonasHasTransferencia.getProduct().getProduct().getId()){
//                    pzt.getProduct().setTotal(pzt.getProduct().getTotal()+1);
//                    productosZonaHasTransferencias.set(count,pzt);
//                    transferenciaLiveData.setValue(productosZonaHasTransferencias);
//                    return;
//                }
//                count+=1;
//            }
            productosZonaHasTransferencias.add(productosZonasHasTransferencia);
            productosZonasHasTransferenciasLiveData.setValue(productosZonaHasTransferencias);
        }

    }
    public LiveData<LinkedList<TransfersHasZonesProduct>> getProductosZonaHasTransferencia(){
        if(productosZonasHasTransferenciasLiveData ==null){
            productosZonasHasTransferenciasLiveData = new MutableLiveData<>();
            productosZonaHasTransferencias = new LinkedList<>();
            productosZonasHasTransferenciasLiveData.setValue(productosZonaHasTransferencias);
        }
        return productosZonasHasTransferenciasLiveData;
    }

    public void clean(){
        productosZonaHasTransferencias = new LinkedList<>();
        productosZonasHasTransferenciasLiveData.setValue(productosZonaHasTransferencias);
    }
}
