package inventarioreal.com.inventarioreal_admin.util;

/**
 * Created by @jvillafane on 05/10/2016.
 */
public class Constants {
    //region Fuentes
    public static final String font_montserratBold = "montserratBold";
    public static final String font_montserratRegular = "montserratRegular";
    //endregion

    //region Otros
    public static final String tag = "InvenrotyRfdi";
    //endregion
    //region Parametros
    public static final String parameters = "parameters";
    //endregion

    //region Tables
    public static final String table_epcs = "epcs";
    public static final String table_productos = "productos";
    //endregion
    //region Columnas
    public static final String id = "id";
    public static final String name = "name";
    public static final String state = "state";
    public static final String epc = "epc";
    public static final String companias_id = "companias_id";
    public static final String productos_id = "productos_id";
    public static final String zonas_id = "zonas_id";
    public static final String fecha_ingreso = "fecha_ingreso";
    public static final String fecha_venta = "fecha_venta";
    public static final String fecha_devolucion = "fecha_devolucion";
    public static final String devolucion_observaciones = "devolucion_observaciones";
    public static final String devoluciones_id = "devoluciones_id";
    public static final String logs_usuarios = "logs_usuarios";
    public static final String ventas_id = "ventas_id";
    public static final String epcs_id = "epcs_id";

    public static final String createdAt = "createdAt";
    public static final String updatedAt = "updatedAt";

    public static String password = "password";
    public static String username = "username";
    //endregion


        public static final String url="http://192.168.1.66:1337/";
//    public static final String url = "http://coexnort.servehttp.com:8023/";
    public static final String user = "user";
    public static final String token = "token";
    public static final String authorization = "Authorization";
    public static final String last_updated = "last_updated";
    public static final String device_product_readed = "1";
    public static final String device_product_unreaded = "0";
    public static final String products = "products";
    public static final String start = "start";
    public static final String end = "end";
    public static final String price = "price";
    public static final String vendidas = "vendidas";
    public static final String no_aparece = "no_aparece";

    public static final String ingresados = "ingresados";
    public static final String vendidos = "vendidos";
    public static final String empleado = "empleado";
    public static final String codigo = "codigo";
    //Region Web services
    public static final String ws_login = "login";
    public static final String ws_getProductByEanPLu = "productos";
    public static final String where = "where";
    public static String ws_addMercancia = "productos/addMercancia";
    public static String productos_zona = "productos_zona";
    public static String products_id = "productos_id";
    public static String was_listarzonas = "zonas/listar";
    //endregion
    public static String ean = "ean";
    public static String plu = "plu";
    public static String plu2 = "plu2";
    public static String plu3 = "plu3";
    public static String marca = "marca";
    public static String genero = "genero";
    public static String color = "color";
    public static String talla = "talla";
    public static String categoria = "categoria";
    public static String descripcion = "descripcion";
    public static String cantidad = "cantidad";
    public static String imagen = "imagen";
    public static String precio_costo = "precio_costo";
    public static String precio_venta = "precio_venta";
    public static String ws_sync = "sync";
    public static String table_productos_zonas = "productos_zonas";
    public static String ecps_id = "ecps_id";
    public static String table_zonas = "zonas";
    public static String locales_id = "locales_id";
    public static String fecha = "fecha";
    public static String parcial = "parcial";
    public static String colaborativo = "colaborativo";
    public static String productos_zona_id = "productos_zona_id";
    public static String inventarios_id = "inventarios_id";
    public static String productos_epcs_id = "productos_epcs_id";
    public static String inventarios_consolidados_id = "inventarios_consolidados_id";
    public static String inventario = "inventario";
    public static String inventario_productos = "inventario_productos";
    public static String log_usuarios = "log_usuarios";
    public static String ws_crearInventario = "inventarios/crear";
    public static String content_type = "Content-Type";
    public static String ws_listarInventarios = "inventarios/listarInventarios";
    public static String tipo = "tipo";
    public static String tipo_no_consolidado = "no_consolidado";
    public static String tipo_consolidado = "consolidado";
    public static String tipo_all = "all";
    public static String ws_consolidarInventarios="inventarios/consolidar";
    public static String ws_getProductsByInventory="inventarios/listarProductosInventario";
    public static String ws_listarInventariosConsolidados="inventariosConsolidados/listar";
    public static String ws_adjuntarInvenario="inventarios/adjuntar";
    public static String ws_getProductsByInventoryColaborativo="inventariosConsolidados/listarProductos";
    public static String ws_obtenerTransferencias="transferencias/obtenerTransferencias";
    public static String local_id="local_id";
}
