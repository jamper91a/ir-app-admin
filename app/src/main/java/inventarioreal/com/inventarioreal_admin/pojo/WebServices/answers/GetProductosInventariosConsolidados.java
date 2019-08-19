package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class GetProductosInventariosConsolidados {
    private ConsolidatedInventory inventariosConsolidados;
    private ProductHasZone productosZona[];

    public GetProductosInventariosConsolidados() {
    }

    public ConsolidatedInventory getInventariosConsolidados() {
        return inventariosConsolidados;
    }

    public void setInventariosConsolidados(ConsolidatedInventory inventariosConsolidados) {
        this.inventariosConsolidados = inventariosConsolidados;
    }

    public ProductHasZone[] getProductosZonas() {
        return productosZona;
    }

    public void setProductosZonas(ProductHasZone[] productosZonas) {
        this.productosZona = productosZonas;
    }
}
