package inventarioreal.com.inventarioreal_admin.util;

import inventarioreal.com.inventarioreal_admin.BuildConfig;

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

    public static final String url = BuildConfig.API_URL;
//    public static final String url = "http://192.168.1.70:1337/";
    public static final String user = "user";
    public static final String token = "token";
    public static final String authorization = "Authorization";
    public static final String last_updated = "last_updated";



    public static final String employee = "employee";
    //region Web services
    public static final String ws_attachInventory ="inventory/attach";
    public static final String ws_consolidateInventory ="inventory/consolidate";
    public static final String ws_createInventory = "inventory/create";
    public static final String ws_listInventories = "inventory/list";
    public static final String ws_listProductsByInventory ="inventory/list-products-by-inventory";
    public static final String ws_listConsolidatedInventories ="ci/listByCollaborative";
    public static final String ws_listAllConsolidatedInventories ="ci/listAll";
    public static final String ws_listProductByConsolidatedInventory ="ci/listProducts";
    public static final String ws_listLastConsolidatedInventory ="ci/lastInventory";
    public static final String ws_addCommodity = "product/add-commodity";
    public static final String ws_findProductByEanPlu = "product/find-one";
    public static final String ws_findProductByEpc = "product/find-by-epc";
    public static final String ws_findProductInShopByEanPlu ="product/find-products-in-local-by-id";
    public static final String ws_findProductInShopByEpc ="product/find-products-in-local-by-epc";
    public static final String ws_diferenceBetweenInventories="report/difference-between-inventories";
    public static final String ws_createTransfer ="transfer/create-transfer";
    public static final String ws_finishTransfer ="transfer/finish-transfer";
    public static final String ws_findTransfersByShop ="transfer/get-transfers-by-shop";
    public static final String ws_findTransfersByType ="transfer/get-transfers-by-type";
    public static final String ws_returnProduct ="devolution/returnProducts";
    public static final String ws_saveReport="report/save-report";
    public static final String ws_login = "user/login";
    public static final String ws_sync = "user/sync";
    public static final String ws_sellCommodity= "sells/create-sell";
    public static final String ws_getReportsByType ="report/get-reports-by-type";
    public static final String ws_getReportById = "report/get-reports-by-id";
    public static final String ws_homologateUnits = "report/homologate-units";
    public static final String ws_saleUnits="report/sale-units";
    public static final String ws_rotationUnits="report/rotation-units";
    public static final String ws_devolutionsByType="report/devolutions-by-type";
    public static final String ws_rotationProyected="report/rotation-proyected-by-ean-plu";
    public static final String ws_diferenceInventoryErp="report/difference-with-inventory-erp";
    public static final String ws_createUser="user/create-employee";
    public static final String ws_findEmployeeByUsername = "user/find-employee-by-username";
    public static final String ws_modifyUser = "user/modify-employee-by-username";
    public static final String ws_changeStateUser="user/change-employee-state";
    public static final String ws_getEmployeesByCompany = "user/list-employees-by-company";
    public static final String ws_createPdf="pdf/createPdf";
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
    public static final String group="group";
    public static final String company = "company";
    public static final String active = "active";
    public static final String templateId = "templateId";
    public static final String data = "data";
    public static final String title = "title";
    public static final String rows = "rows";
    public static final String shops = "shops";

    public static final String total = "total";
    public static final String to = "to";
    public static final String page = "page";
    public static final String header = "header";
    public static String col1 = "col1";
    public static String col2 = "col2";
    public static String col3 = "col3";
    public static String col4 = "col4";
    public static String amountSentTitle = "amountSentTitle";
    public static String amountSent = "amountSent";
    public static String amountReceivedTitle = "amountReceivedTitle";
    public static String amountReceived = "amountReceived";
    public static String missingTitle = "missingTitle";
    public static String amountMissing = "amountMissing";
    public static String destinationTitle = "destinationTitle";
    public static String destination = "destination";
    public static String source = "source";
    public static  String sent = "sent";
    public static  String received = "received";
    public static  String ean = "ean";
    public static  String description = "description";

}
