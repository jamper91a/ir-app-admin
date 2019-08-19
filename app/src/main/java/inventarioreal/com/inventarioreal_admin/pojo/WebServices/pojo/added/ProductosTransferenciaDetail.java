package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;

public class ProductosTransferenciaDetail {
    private int enviados;
    private int recibidos;
    private Product producto;

    public ProductosTransferenciaDetail() {
    }

    public int getEnviados() {
        return enviados;
    }

    public void setEnviados(int enviados) {
        this.enviados = enviados;
    }

    public int getRecibidos() {
        return recibidos;
    }

    public void setRecibidos(int recibidos) {
        this.recibidos = recibidos;
    }

    public Product getProducto() {
        return producto;
    }

    public void setProducto(Product producto) {
        this.producto = producto;
    }
}
