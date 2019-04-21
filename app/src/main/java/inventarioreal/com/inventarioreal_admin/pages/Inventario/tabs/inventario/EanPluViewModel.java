package inventarioreal.com.inventarioreal_admin.pages.Inventario.tabs.inventario;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonasHasTransferencias;

public class EanPluViewModel extends ViewModel {

    MutableLiveData<LinkedList<ProductosZonas>> productosZonasLiveData = null;
    private LinkedList<ProductosZonas> productosZonaHasTransferencia = null;



    public void addProductoZona(ProductosZonas productosZonas){
        //Valido si ya se ha agregado el producto
        int count=0;
        if(productosZonas.getProductos_id()!=null){
            for (ProductosZonas pz: productosZonaHasTransferencia
            ) {
                if(pz.getProductos_id().getId() == productosZonas.getProductos_id().getId()){
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
    public LiveData<LinkedList<ProductosZonas>> getProductosZona(){
        if(productosZonasLiveData ==null){
            productosZonasLiveData = new MutableLiveData<>();
            productosZonaHasTransferencia = new LinkedList<>();
            productosZonasLiveData.setValue(productosZonaHasTransferencia);
        }
        return productosZonasLiveData;
    }
}
