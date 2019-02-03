package inventarioreal.com.inventarioreal_admin.pojo;

public class ProductosZona {

    private long id;
    private Producto productos_id;
    private Zona zonas_id;
    private String fecha_ingreso;
    private String fecha_venta;
    private String fecha_devolucion;
    private String devolucion_observaciones;
    private Devoluciones devoluciones_id;
    private String log_usuarios;
    private Ventas ventas_id;
    private Epc ecps_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Producto getProductos_id() {
        return productos_id;
    }

    public void setProductos_id(Producto productos_id) {
        this.productos_id = productos_id;
    }

    public Zona getZonas_id() {
        return zonas_id;
    }

    public void setZonas_id(Zona zonas_id) {
        this.zonas_id = zonas_id;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public String getDevolucion_observaciones() {
        return devolucion_observaciones;
    }

    public void setDevolucion_observaciones(String devolucion_observaciones) {
        this.devolucion_observaciones = devolucion_observaciones;
    }

    public Devoluciones getDevoluciones_id() {
        return devoluciones_id;
    }

    public void setDevoluciones_id(Devoluciones devoluciones_id) {
        this.devoluciones_id = devoluciones_id;
    }

    public String getLog_usuarios() {
        return log_usuarios;
    }

    public void setLog_usuarios(String log_usuarios) {
        this.log_usuarios = log_usuarios;
    }

    public Ventas getVentas_id() {
        return ventas_id;
    }

    public void setVentas_id(Ventas ventas_id) {
        this.ventas_id = ventas_id;
    }

    public Epc getEcps_id() {
        return ecps_id;
    }

    public void setEcps_id(Epc ecps_id) {
        this.ecps_id = ecps_id;
    }
}
