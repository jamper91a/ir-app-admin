package inventarioreal.com.inventarioreal_admin.pages.Reports.RotationUnits.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class RUEanPluViewModel extends ViewModel {
    MutableLiveData<LinkedList<ProductHasZone>> productosZonasLiveData = null;
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
                    if(productosZonas.getSell()!=null && productosZonas.getSell().getId()>1)
                        pz.setVendidas(pz.getVendidas()+1);
                    productosZona.set(count,pz);
                    productosZonasLiveData.setValue(productosZona);
                    return;
                }
                count+=1;
            }
            if(productosZonas.getSell()!=null && productosZonas.getSell().getId()>1)
                productosZonas.setVendidas(productosZonas.getVendidas()+1);
            productosZona.add(productosZonas);
            productosZonasLiveData.setValue(productosZona);
        }

    }

    public void removeProductoZona(ProductHasZone productosZonas){
        if(productosZona==null)
            productosZona = new LinkedList<>();
        //Valido si ya se ha agregado el producto
        int count=0;
        if(productosZonas.getProduct()!=null){
            for (ProductHasZone pz: productosZona
            ) {
                if(pz.getProduct().getId() == productosZonas.getProduct().getId()){
                    pz.setTotal(pz.getTotal()-1);
                    productosZona.set(count,pz);
                    productosZonasLiveData.setValue(productosZona);
                    if(pz.getTotal() == 0){
                        productosZona.remove(pz);
                    }
                    return;
                }
                count+=1;
            }
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

    public void clean(){
        if(productosZonasLiveData ==null){
            productosZonasLiveData = new MutableLiveData<>();

        }
        productosZona = new LinkedList<>();
        productosZonasLiveData.setValue(productosZona);
    }
}
