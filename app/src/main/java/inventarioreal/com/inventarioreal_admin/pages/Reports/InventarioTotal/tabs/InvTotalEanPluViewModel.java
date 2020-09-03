package inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioTotal.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.UltimoInventarioResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class InvTotalEanPluViewModel extends ViewModel {

    MutableLiveData<LinkedList<ProductHasZone>> productsZonasLiveData = null;
    MutableLiveData<LinkedList<ProductHasZone>> allProductsZonasLiveData = null;
    private LinkedList<ProductHasZone> products = null;
    private LinkedList<ProductHasZone> allProducts= null;
    private MutableLiveData<UltimoInventarioResponse> inventario= new MutableLiveData<>();



    public void addProductoZona(ProductHasZone product){
        if(products ==null)
            products = new LinkedList<>();


        //Valido si ya se ha agregado el producto
        int count=0;
        if(product.getProduct()!=null){
            for (ProductHasZone pz: products
            ) {
                if(pz.getProduct().getId() == product.getProduct().getId()){
                    pz.setTotal(pz.getTotal()+1);
                    products.set(count,pz);
                    productsZonasLiveData.setValue(products);
                    return;
                }
                count+=1;
            }
            products.add(product);
            productsZonasLiveData.setValue(products);

        }

    }
    public void addAppProductoZona(ProductHasZone product){
        if(allProducts == null)
            allProducts = new LinkedList<>();

        allProducts.add(product);
        allProductsZonasLiveData.setValue(allProducts);

    }
    public LiveData<LinkedList<ProductHasZone>> getProducts(){
        if(productsZonasLiveData ==null){
            productsZonasLiveData = new MutableLiveData<>();
            products = new LinkedList<>();
            productsZonasLiveData.setValue(products);
        }
        return productsZonasLiveData;
    }
    public LiveData<LinkedList<ProductHasZone>> getAllProducts(){
        if(allProductsZonasLiveData ==null){
            allProductsZonasLiveData = new MutableLiveData<>();
            allProducts = new LinkedList<>();
            allProductsZonasLiveData.setValue(allProducts);
        }
        return allProductsZonasLiveData;
    }

    public LinkedList<ProductHasZone> getStaticProducts(){
        return this.products;
    }

    public LinkedList<ProductHasZone> getStaticAllProducts(){
        return this.allProducts;
    }

    public LiveData<UltimoInventarioResponse> getInventario(){
        return this.inventario;
    }
    public void setInventario(UltimoInventarioResponse a) {
        this.inventario.setValue(a);
    }

}
