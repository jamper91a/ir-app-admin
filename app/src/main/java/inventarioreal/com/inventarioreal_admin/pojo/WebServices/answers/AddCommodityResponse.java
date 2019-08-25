package inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers;

import java.util.List;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;

public class AddCommodityResponse {
    private Product product;
    private List<Epc> epcs;



    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Epc> getEpcs() {
        return epcs;
    }

    public void setEpcs(List<Epc> epcs) {
        this.epcs = epcs;
    }
}
