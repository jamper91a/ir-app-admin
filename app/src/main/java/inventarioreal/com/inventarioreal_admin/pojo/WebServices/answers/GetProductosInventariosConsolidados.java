package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.InventariosConsolidados;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Productos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;

public class GetProductosInventariosConsolidados {
    private InventariosConsolidados inventariosConsolidados;
    private ProductosZonas productosZona[];

    public GetProductosInventariosConsolidados() {
    }

    public InventariosConsolidados getInventariosConsolidados() {
        return inventariosConsolidados;
    }

    public void setInventariosConsolidados(InventariosConsolidados inventariosConsolidados) {
        this.inventariosConsolidados = inventariosConsolidados;
    }

    public ProductosZonas[] getProductosZonas() {
        return productosZona;
    }

    public void setProductosZonas(ProductosZonas[] productosZonas) {
        this.productosZona = productosZonas;
    }
}
