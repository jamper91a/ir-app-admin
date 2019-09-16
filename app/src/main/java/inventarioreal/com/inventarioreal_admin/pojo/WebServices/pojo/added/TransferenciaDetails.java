package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Employee;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;

public class TransferenciaDetails {

    private int enviados;
    private int recibidos;
    private int faltantes;
    private String fecha;
    private Shop destino;
    private Shop origen;
    private Employee generador;
    private String mensaje;
    private String manifiestoElectronico;
    private ProductosTransferenciaDetail[] productos;
    private Transfer transfer;

    public TransferenciaDetails() {
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

    public int getFaltantes() {
        return faltantes;
    }

    public void setFaltantes(int faltantes) {
        this.faltantes = faltantes;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Shop getDestino() {
        return destino;
    }

    public void setDestino(Shop destino) {
        this.destino = destino;
    }

    public Shop getOrigen() {
        return origen;
    }

    public void setOrigen(Shop origen) {
        this.origen = origen;
    }

    public Employee getGenerador() {
        return generador;
    }

    public void setGenerador(Employee generador) {
        this.generador = generador;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getManifiestoElectronico() {
        return manifiestoElectronico;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public void setManifiestoElectronico(String manifiestoElectronico) {
        this.manifiestoElectronico = manifiestoElectronico;
    }

    public ProductosTransferenciaDetail[] getProductos() {
        if(productos==null)
            return new ProductosTransferenciaDetail[0];
        else
            return productos;
    }

    public void setProductos(ProductosTransferenciaDetail[] productos) {
        this.productos = productos;
    }
}
