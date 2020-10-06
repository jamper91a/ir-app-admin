package inventarioreal.com.inventarioreal_admin.pages.Search.EanPluSonar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterInventarioEanPlu;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.listener.RFDIListener;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Reports.HomeReportes;
import inventarioreal.com.inventarioreal_admin.pages.Search.EpcSonar.SearchEpcSonar;
import inventarioreal.com.inventarioreal_admin.pages.Search.HomeSearch;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.RFDIReader;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class SearchEanPluSonar extends CicloActivity {
    private Product product = null;
    private ArrayList<ProductHasZone> allProducts = new ArrayList<>();
    private ArrayList<ProductHasZone> productsFound = new ArrayList<>();
    private ListAdapterInventarioEanPlu adapter = null;

    RFDIReader rfdiReader =  null;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String epc = msg.getData().getString("epc");
                    addToList(epc);
                    break ;
                case 3:
                    boolean state = msg.getData().getBoolean("state");
                    changedStateLecture(state);
                    break ;
            }
        }
    } ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rfdiReader = new RFDIReader(new RFDIListener() {
            @Override
            public void onEpcAdded(String epc) {
                Message msg = new Message();
                msg.what = 1;
                Bundle b = new Bundle();
                b.putString("epc", epc);
                msg.setData(b);
                handler.sendMessage(msg);
            }

            @Override
            public void onEpcRepeated(String epc) {
            }

            @Override
            public void onStateChanged(boolean state) {
                Message msg = new Message();
                msg.what = 3;
                Bundle b = new Bundle();
                b.putBoolean("state", state);
                msg.setData(b);
                handler.sendMessage(msg);
            }

            @Override
            public void onKeyPresses(String key) {

            }
        }, this);
        init(this,this,R.layout.activity_search_ean_plu_sonar);
        // toolbar
        getSupportActionBar().setTitle(R.string.busqueda_items_ean_plu_sonar);
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
        addElemento(new Animacion(findViewById(R.id.btnLee),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBor),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnVerOtro),Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.lst1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtTags),Techniques.FadeInLeft));

        adapter = new ListAdapterInventarioEanPlu(this, admin, productsFound, new OnItemClickListener() {
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
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnBus, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getElemento(R.id.edtEanPlu).getElemento().isEnabled()){
                    getElemento(R.id.edtEanPlu).getElemento().setEnabled(false);

                    WebServices.getProductByEanPlu(
                            getElemento(R.id.edtEanPlu).getText(),
                            SearchEanPluSonar.this,
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

        add_on_click(R.id.btnLee, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedStateLecture(!rfdiReader.isStartReader());
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
                    findProductosByEanPlu();

                }else{
                    admin.toast(R.string.se_debe_buscar_un_producto);
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
                admin.callIntent(SearchEanPluSonar.class, null);
            }
        });

        add_on_click(R.id.btnBor, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean();
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

    public void findProductosByEanPlu(){
        WebServices.getProductInShopByEanPlu(product.getId(), this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                allProducts = (ArrayList<ProductHasZone>) ok.getData();

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
//                DataBase db = DataBase.getInstance(this);
//                db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion


    private void addToList(final String epc) {
        createEpc(epc);
    }

    private void createEpc(String epc){
        //Find the epc in the list
        for (ProductHasZone product: allProducts){
            if(product.getEpc().getEpc().equals(epc)){
                productsFound.add(product);
            }
        }
        if(productsFound !=null){
            adapter.setItems(productsFound);
            adapter.notifyDataSetChanged();
            getElemento(R.id.txtTags).setText(getString(R.string.total_unidades) + ": " + productsFound.size());
        }
    }

    private void changedStateLecture(boolean state){
        if(state){
            rfdiReader.startReader();
            getElemento(R.id.btnLee).setText(getString(R.string.detener));
        }else{
            rfdiReader.stopReader();
            getElemento(R.id.btnLee).setText(getString(R.string.leer));
        }
    }

    private void clean(){
        rfdiReader.cleanEpcs();
        productsFound.clear();
        getElemento(R.id.txtTags).setText(getString(R.string.total_unidades) + ": 0");
        adapter.setItems(productsFound);
        adapter.notifyDataSetChanged();
    }

}
