package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Devolution;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;

public class SyncResponse {
    private Epc[] epcs;
    private Product[] products;
    private ProductHasZone[] productsHasZones;
    private Zone[] zones;
    private Shop[] shops;
    private Devolution[] devolutions;

    public SyncResponse() {
    }

    public Epc[] getEpcs() {
        return epcs;
    }

    public void setEpcs(Epc[] epcs) {
        this.epcs = epcs;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public ProductHasZone[] getProductsHasZones() {
        return productsHasZones;
    }

    public void setProductsHasZones(ProductHasZone[] productsHasZones) {
        this.productsHasZones = productsHasZones;
    }

    public Zone[] getZones() {
        return zones;
    }

    public void setZones(Zone[] zones) {
        this.zones = zones;
    }

    public Shop[] getShops() {
        return shops;
    }

    public void setShops(Shop[] shops) {
        this.shops = shops;
    }


    public Devolution[] getDevolutions() {
        return devolutions;
    }

    public void setDevolutions(Devolution[] devolutions) {
        this.devolutions = devolutions;
    }
}
