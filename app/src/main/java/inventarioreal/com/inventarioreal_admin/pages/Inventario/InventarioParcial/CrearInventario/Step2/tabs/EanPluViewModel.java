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
        productosZona.add(productosZonas);
        productosZonasLiveData.setValue(productosZona);
    }
    public LiveData<LinkedList<ProductosZonas>> getProductosZona(){
        if(productosZonasLiveData ==null){
            productosZonasLiveData = new MutableLiveData<>();
            productosZona = new LinkedList<>();
            //Add Header
//            productosZona.insert(new ProductosZonas());
            productosZonasLiveData.setValue(productosZona);
        }
        return productosZonasLiveData;
    }
}
