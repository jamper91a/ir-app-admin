package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import java.util.ArrayList;
import java.util.Arrays;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Devolution;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Report;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;

public class GetReportByIdResponse {
    private ProductHasZone[] productsHasZones;
    private Report report;

    public GetReportByIdResponse() {
    }

    public ProductHasZone[] getProductsHasZones() {
        return productsHasZones;
    }
    public ArrayList<ProductHasZone> getProductsHasZonesAsArray(){
        ArrayList<ProductHasZone> array = new ArrayList<ProductHasZone>(Arrays.asList(productsHasZones));
        return array;
    }

    public void setProductsHasZones(ProductHasZone[] productsHasZones) {
        this.productsHasZones = productsHasZones;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
