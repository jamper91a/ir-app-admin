package inventarioreal.com.inventarioreal_admin.pages.Search.EpcLastLocation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterInventarioEanPlu;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Reports.HomeReportes;
import inventarioreal.com.inventarioreal_admin.pages.Search.EpcSonar.SearchEpcSonar;
import inventarioreal.com.inventarioreal_admin.pages.Search.HomeSearch;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class SearchEpcLastLocation extends CicloActivity {
    private Product product = null;
    private ArrayList<ProductHasZone> products = new ArrayList<>();
    private ListAdapterInventarioEanPlu adapter = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_search_list_locations);
        // toolbar
        getSupportActionBar().setTitle(R.string.busqueda_items_epc_ultima_ubicacion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.titleIcn),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.titleTxt),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEanPlu),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.icn2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBus),Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.lnl1),Techniques.FadeInLeft, null, false));
        addElemento(new Animacion(findViewById(R.id.lblDes1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes3),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.img1),Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.lnl1a),Techniques.FadeInLeft, null, false));
        addElemento(new Animacion(findViewById(R.id.lbl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSi),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnNo),Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.lnl2),Techniques.FadeInLeft,null, false));
        addElemento(new Animacion(findViewById(R.id.btnEnv),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnVerOtro),Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.lst1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtTags),Techniques.FadeInLeft));

        getElemento(R.id.titleTxt).setText(getString(R.string.busqueda_items_epc_ultima_ubicacion));

        adapter = new ListAdapterInventarioEanPlu(this, admin, products, new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
            }

            @Override
            public void onLongItemClick(Object item) {
            }

            @Override
            public void onItemClick(int view, Object item) {
            }
        });
        RecyclerView lst1 = (RecyclerView)getElemento(R.id.lst1).getElemento();
        lst1.setLayoutManager(new LinearLayoutManager(this));
        lst1.setAdapter(adapter);

    }

    @Override
    public void getData() {
        ((EditText)getElemento(R.id.edtEanPlu).getElemento()).setHint(R.string.epc);
//        getElemento(R.id.edtEanPlu).setText(getString(R.string.epc));
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnBus, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getElemento(R.id.edtEanPlu).getElemento().isEnabled()){
                    getElemento(R.id.edtEanPlu).getElemento().setEnabled(false);

                    WebServices.getProductByEpc(
                            getElemento(R.id.edtEanPlu).getText(),
                            SearchEpcLastLocation.this,
                            admin,
                            new ResultWebServiceInterface() {
                                @Override
                                public void ok(ResultWebServiceOk ok) {
                                    Product productoConsultado = (Product) ok.getData();
                                    mostrarInformacionProducto(productoConsultado);
                                    getElemento(R.id.btnBus).setText(getString(R.string.buscar_otro));
                                    getElemento(R.id.btnBus).getElemento().setVisibility(View.GONE);
                                }

                                @Override
                                public void fail(ResultWebServiceFail fail) {
                                    getElemento(R.id.edtEanPlu).getElemento().setEnabled(true);
                                    int stringId = admin.getStringResourceIdByName(fail.getError());
                                    if(stringId >0) {
                                        admin.toast((getString(stringId)));
                                    } else {
                                        admin.toast(fail.getError());
                                    }
                                    getElemento(R.id.lnl1).getElemento().setVisibility(View.GONE);
                                    getElemento(R.id.lnl1a).getElemento().setVisibility(View.GONE);
                                    getElemento(R.id.lnl2).getElemento().setVisibility(View.GONE);
                                }
                            });
                }else{
                    getElemento(R.id.btnBus).setText(getString(R.string.buscar));
                    getElemento(R.id.edtEanPlu).getElemento().setEnabled(true);
                    ocultarInformacionProducto();

                }
            }
        });




        add_on_click(R.id.btnSi, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product!=null){
                    //Hide search icon
                    getElemento(R.id.icn2).getElemento().setVisibility(View.GONE);
                    getElemento(R.id.lnl1).getElemento().setVisibility(View.GONE);
                    getElemento(R.id.lnl2).getElemento().setVisibility(View.VISIBLE);
                    findLastLocationByEpc();

                }else{
                    admin.toast(R.string.error_minimo_un_epc);
                }

            }
        });
        add_on_click(R.id.btnNo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getElemento(R.id.btnBus).getElemento().setVisibility(View.VISIBLE);
                getElemento(R.id.btnBus).setText(getString(R.string.buscar));
                getElemento(R.id.edtEanPlu).getElemento().setEnabled(true);
                ocultarInformacionProducto();
            }
        });
        add_on_click(R.id.btnVerOtro, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(SearchEpcLastLocation.class, null);
            }
        });
    }

    private void mostrarInformacionProducto(Product p){
        this.product = p;
        getElemento(R.id.lblDes1).setText(p.getDescription());
        getElemento(R.id.lblDes2).setText(p.getBranch());
        getElemento(R.id.lblDes3).setText(p.getColor());
        admin.loadImageFromInternet(
                p.getImagen(),
                (NetworkImageView)getElemento(R.id.img1).getElemento(),
                R.drawable.imagennoencontrada,
                R.drawable.imagennoencontrada);
        getElemento(R.id.icn2).getElemento().setVisibility(View.GONE);
        getElemento(R.id.img1).getElemento().setVisibility(View.VISIBLE);
        getElemento(R.id.lnl1).getElemento().setVisibility(View.VISIBLE);
        getElemento(R.id.lnl1a).getElemento().setVisibility(View.VISIBLE);
    }

    private void ocultarInformacionProducto(){
        this.product = null;
        getElemento(R.id.lblDes1).setText("");
        getElemento(R.id.lblDes2).setText("");
        getElemento(R.id.lblDes3).setText("");
        getElemento(R.id.icn2).getElemento().setVisibility(View.VISIBLE);
        getElemento(R.id.lnl1).getElemento().setVisibility(View.GONE);
        getElemento(R.id.lnl1a).getElemento().setVisibility(View.GONE);
        getElemento(R.id.lnl2).getElemento().setVisibility(View.GONE);
    }

    public void findLastLocationByEpc(){
        WebServices.getProductInShopByEpc(getElemento(R.id.edtEanPlu).getText(), this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                products = (ArrayList<ProductHasZone>) ok.getData();
                if(products!=null){
                    adapter.setItems(products);
                    adapter.notifyDataSetChanged();
                    getElemento(R.id.txtTags).setText(getString(R.string.total_unidades)+": " +products.size());
                }
            }

            @Override
            public void fail(ResultWebServiceFail fail) {

            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }



    @Override
    public void onBackPressed() {
        admin.callIntent(HomeSearch.class, null);
    }

    //region Menu

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(getString(R.string.log_out));
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }
        if(item.getTitle()!= null){
            if(item.getTitle().equals(getString(R.string.log_out))){
                //DataBase db = DataBase.getInstance(this);
                //db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion


}
