package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class GetProductosInventariosConsolidados {
    private ConsolidatedInventory consolidatedInventories;
    private ProductHasZone products[];

    public GetProductosInventariosConsolidados() {
    }

    public ConsolidatedInventory getConsolidatedInventories() {
        return consolidatedInventories;
    }

    public void setConsolidatedInventories(ConsolidatedInventory consolidatedInventories) {
        this.consolidatedInventories = consolidatedInventories;
    }

    public ProductHasZone[] getProductosZonas() {
        return products;
    }

    public void setProductosZonas(ProductHasZone[] productosZonas) {
        this.products = productosZonas;
    }
}
