package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Productos;

public class ProductosTransferenciaDetail {
    private int enviados;
    private int recibidos;
    private Productos producto;

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

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }
}
