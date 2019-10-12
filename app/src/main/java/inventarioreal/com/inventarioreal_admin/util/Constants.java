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
    public static final String table_products = "products";
    public static final String table_productsHasZones = "products_has_zones";
    public static final String table_zones = "zones";
    public static final String table_devolutions = "devolutions";
    public static final String table_shops = "shops";
    //endregion
    //region Columnas bd interna
    public static final String column_admissionDate = "admission_date";
    public static final String column_amount = "amount";
    public static final String column_branch = "branch";
    public static final String column_category = "category";
    public static final String column_color = "color";
    public static final String column_collaborative = "collaborative";
    public static final String column_company = "company_id";
    public static final String column_consolidatedInventory = "consolidated_inventory_id";
    public static final String column_costPrice = "cost_price";
    public static final String column_date = "date";
    public static final String column_description = "description";
    public static final String column_devolution = "devolution_id";
    public static final String column_ean = "ean";
    public static final String column_employee = "employee_id";
    public static final String column_epc = "epc";
    public static final String column_epc_id = "epc_id";
    public static final String column_firstInventory = "inventory_first_id";
    public static final String column_gender = "gender";
    public static final String column_homologatorEmployee = "homologator_employee_id";
    public static final String column_id = "id";
    public static final String column_inventory = "inventory_id";
    public static final String column_imagen = "imagen";
    public static final String column_logsUsers = "logs_users";
    public static final String column_manifest = "manifest";
    public static final String column_message = "message";
    public static final String column_name = "name";
    public static final String column_notesReturn = "notes_return";
    public static final String column_parcial = "parcial";
    public static final String column_plu = "plu";
    public static final String column_plu2 = "plu2";
    public static final String column_plu3 = "plu3";
    public static final String column_product = "product_id";
    public static final String column_productHasZone = "products_has_zone_id";
    public static final String column_returnDate = "return_date";
    public static final String column_report = "report_id";
    public static final String column_sell = "sell_id";
    public static final String column_secondInventory = "inventory_second_id";
    public static final String column_sellDate = "sell_date";
    public static final String column_sellPrice = "sell_price";
    public static final String column_shop = "shop_id";
    public static final String column_shopshopDestination = "shop_destination_id";
    public static final String column_shopSource = "shop_source_id";
    public static final String column_size = "size";
    public static final String column_supplier = "supplier_id";
    public static final String column_state = "state";
    public static final String column_totalProducts = "total_products";
    public static final String column_type = "type";
    public static final String column_unitsReturned = "units_returned";
    public static final String column_unitsSell = "units_sell";
    public static final String column_user = "user_id";
    public static final String column_zone = "zone_id";



    public static final String createdAt = "createdAt";
    public static final String updatedAt = "updatedAt";

    //endregion


//    public static final String url = "http://coexnort.servehttp.com:8023/";
    public static final String url = "http://192.168.1.3:1337/";
    public static final String user = "user";
    public static final String token = "token";
    public static final String authorization = "Authorization";
    public static final String last_updated = "last_updated";



    public static final String employee = "employee";
    //region Web services
    public static final String ws_attachInventory ="inventarios/adjuntar";
    public static final String ws_consolidateInventory ="inventarios/consolidar";
    public static final String ws_createInventory = "inventarios/crear";
    public static final String ws_listInventories = "inventarios/listarInventarios";
    public static final String ws_listProductsByInventory ="inventarios/listarProductosInventario";
    public static final String ws_listConsolidatedInventories ="inventariosConsolidados/listar";
    public static final String ws_listAllConsolidatedInventories ="inventariosConsolidados/listarTodos";
    public static final String ws_listProductByConsolidatedInventory ="inventariosConsolidados/listarProductos";
    public static final String ws_listLastConsolidatedInventory ="inventariosConsolidados/ultimoInventario";
    public static final String ws_addCommodity = "productos/addMercancia";
    public static final String ws_findProduct = "productos/findOne";
    public static final String ws_findProductInShopByEanPlu ="productos/findProductInLocalByEanPlu";
    public static final String ws_diferenceBetweenInventories="reportes/diferenceBetweenInventories";
    public static final String ws_createTransfer ="transferencias/crear";
    public static final String ws_finishTransfer ="transferencias/finishTransfer";
    public static final String ws_findTransfersByShop ="transferencias/listTransfersByShop";
    public static final String ws_findTransfersByType ="transferencias/listTransfersByType";
    public static final String ws_returnProduct ="devoluciones/devolverProductos";
    public static final String ws_saveReport="reportes/guardarReporte";
    public static final String ws_login = "login";
    public static final String ws_sync = "sync";
    public static final String ws_sellCommodity= "sells/create";
    public static final String ws_getReportsByType ="reportes/getReportsByType";
    public static final String ws_getReportById = "reportes/getReportById";
    public static final String ws_homologateUnits = "reportes/homologateUnits";
    public static final String ws_saleUnits="reportes/saleUnits";
    public static final String ws_rotationUnits="reportes/rotationUnits";
    public static final String ws_devolutionsByType="reportes/devolutionsByType";
    public static final String ws_rotationProyected="reportes/rotationProyectedByEanPlu";
    public static final String ws_diferenceInventoryErp="reportes/diferenceWithInventoryErp";
    //endregion





    public static final String zone="zone";
    public static final String product="product";
    public static final String epc="epc";
    public static final String devolution="devolution";
    public static final String sell="sell";
    public static final String products = "products";
    //region Parameter web services
    public static final String username ="username";
    public static final String password="password";
    public static final String code="code";
    public static final String inventory="inventory";
    public static final String parcial="parcial";
    public static final String collaborative="collaborative";
    public static final String consolidatedInventory="consolidatedInventory";
    public static final String notes_return="notes_return";
    public static final String inventories="inventories";
    public static final String name="name";
    public static final String type="type";
    public static final String shop="shop";
    public static final String transfer="transfer";
    public static final String shopSource="shopSource";
    public static final String shopDestination="shopDestination";
    public static final String message="message";
    public static final String state="state";
    public static final String firstInventory="firstInventory";
    public static final String secondInventory="secondInventory";
    public static final String id="id";
    public static final String inventario_inicial="inventario_inicial";
    public static final String inventario_final="inventario_final";
    public static final String transferencias_ingreso="entrada";
    public static final String transferencias_salida="salida";
    public static final String tipo_no_consolidado = "no_consolidado";
    public static final String tipo_all = "all";
    public static final String content_type = "Content-Type";
    public static final String report="report";
    public static final int inventory_diference_between_inventories = 1;
    public static final int inventory_sell_units = 2;
    public static final String type_report_1="diferenceBetweenInventories";
    public static final String firstDate="firstDate";
    public static final String secondDate="secondDate";
    public static final String days="days";
    public static final String product_id="product_id";


}
