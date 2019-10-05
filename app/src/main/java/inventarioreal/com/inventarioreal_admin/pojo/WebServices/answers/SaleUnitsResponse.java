package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import java.util.ArrayList;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;

public class SaleUnitsResponse {
    private ArrayList<ProductHasZone> arraySaleUnits = new ArrayList<>();
    private ArrayList<ProductHasZone> arrayReturnedUnits = new ArrayList<>();


    public SaleUnitsResponse() {
    }

    public SaleUnitsResponse(ArrayList<ProductHasZone> arraySaleUnits, ArrayList<ProductHasZone> arrayReturnedUnits) {
        this.arraySaleUnits = arraySaleUnits;
        this.arrayReturnedUnits = arrayReturnedUnits;
    }

    public ArrayList<ProductHasZone> getArraySaleUnits() {
        return arraySaleUnits;
    }

    public void setArraySaleUnits(ArrayList<ProductHasZone> arraySaleUnits) {
        this.arraySaleUnits = arraySaleUnits;
    }

    public ArrayList<ProductHasZone> getArrayReturnedUnits() {
        return arrayReturnedUnits;
    }

    public void setArrayReturnedUnits(ArrayList<ProductHasZone> arrayReturnedUnits) {
        this.arrayReturnedUnits = arrayReturnedUnits;
    }
}
