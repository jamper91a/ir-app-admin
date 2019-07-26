package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Locales;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Users;

public class TransferenciaDetails {

    private int enviados;
    private int recibidos;
    private int faltantes;
    private String fecha;
    private Locales destino;
    private Locales origen;
    private Users generador;
    private String mensaje;
    private String manifiestoElectronico;
    private ProductosTransferenciaDetail[] productos;

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

    public Locales getDestino() {
        return destino;
    }

    public void setDestino(Locales destino) {
        this.destino = destino;
    }

    public Locales getOrigen() {
        return origen;
    }

    public void setOrigen(Locales origen) {
        this.origen = origen;
    }

    public Users getGenerador() {
        return generador;
    }

    public void setGenerador(Users generador) {
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
