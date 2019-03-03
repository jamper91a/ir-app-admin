package inventarioreal.com.inventarioreal_admin.pages.Inventario.InventarioParcial.CrearInventario.Step2.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;

public class EanPluViewModel extends ViewModel {
    MutableLiveData<LinkedList<ProductosZonas>> productosZonasLiveData = null;
    private LinkedList<ProductosZonas> productosZona = null;



    public void addProductoZona(ProductosZonas productosZonas){
        //Valido si ya se ha agregado el producto
        int count=0;
        if(productosZonas.getProductos_id()!=null){
            for (ProductosZonas pz: productosZona
                 ) {
                if(pz.getProductos_id().getId() == productosZonas.getProductos_id().getId()){
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
    public LiveData<LinkedList<ProductosZonas>> getProductosZona(){
        if(productosZonasLiveData ==null){
            productosZonasLiveData = new MutableLiveData<>();
            productosZona = new LinkedList<>();
            productosZonasLiveData.setValue(productosZona);
        }
        return productosZonasLiveData;
    }
}
