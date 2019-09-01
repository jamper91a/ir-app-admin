package inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class SellEanPluViewModel extends ViewModel {
    MutableLiveData<LinkedList<ProductHasZone>> productsLiveData = null;
    private LinkedList<ProductHasZone> products = null;



    public void addProductoZonaHasTransferencia(ProductHasZone product){
        //Valido si ya se ha agregado el producto
        int count=0;
        if(product.getProduct()!=null){
//            for (ProductosZonasHasTransferencias pzt: products
//                 ) {
//                if(pzt.getProduct().getProduct().getId() == productosZonasHasTransferencia.getProduct().getProduct().getId()){
//                    pzt.getProduct().setTotal(pzt.getProduct().getTotal()+1);
//                    products.set(count,pzt);
//                    transferenciaLiveData.setValue(products);
//                    return;
//                }
//                count+=1;
//            }
            products.add(product);
            productsLiveData.setValue(products);
        }

    }
    public LiveData<LinkedList<ProductHasZone>> getProducts(){
        if(productsLiveData ==null){
            productsLiveData = new MutableLiveData<>();
            products = new LinkedList<>();
            productsLiveData.setValue(products);
        }
        return productsLiveData;
    }
}
