package inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioEanPlu;

import android.os.Bundle;
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
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class InventarioEanPlu extends CicloActivity {

    private String TAG="IngresoMercancia";
    //private UhfManager uhfManager;
    private ArrayList<ProductHasZone> productos = new ArrayList<>();
    private Product productos_id=null;
    private ListAdapterInventarioEanPlu adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_inventario_ean_plu);
        //toolbar
        getSupportActionBar().setTitle("Reporte Inventarios Ean/Plu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.lbl1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEanPlu),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBus),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes3),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.img1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lbl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSi),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnNo),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl2),Techniques.FadeInLeft,null, false));
        addElemento(new Animacion(findViewById(R.id.lst1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtTags),Techniques.FadeInLeft));

        adapter = new ListAdapterInventarioEanPlu(this, admin, productos, new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                admin.toast("onItemClick");
            }

            @Override
            public void onLongItemClick(Object item) {
            }

            @Override
            public void onItemClick(int view, Object item) {
                admin.toast("onItemClick");
            }
        });
        RecyclerView lst1 = (RecyclerView)getElemento(R.id.lst1).getElemento();
        lst1.setLayoutManager(new LinearLayoutManager(this));
        lst1.setAdapter(adapter);
    }

    @Override
    public void getData() {
    }

    public void findProductosByEanPlu(){
        WebServices.getProductInShopByEanPlu(productos_id.id, this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                productos = (ArrayList<ProductHasZone>) ok.getData();
                if(productos!=null){
                    adapter.setItems(productos);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void fail(ResultWebServiceFail fail) {

            }
        });
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnBus, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServices.getProductByEanPlu(
                        getElemento(R.id.edtEanPlu).getText(),
                        InventarioEanPlu.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                Product productoConsultado = (Product) ok.getData();
                                mostrarInformacionProducto(productoConsultado);
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast(fail.getError());
                            }
                        });
            }
        });

        add_on_click(R.id.btnSi, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productos_id!=null){

                    getElemento(R.id.lnl1).getElemento().setVisibility(View.GONE);
                    findProductosByEanPlu();
                    getElemento(R.id.lnl2).getElemento().setVisibility(View.VISIBLE);

                }else{
                    admin.toast("Se debe buscar un producto");
                }
            }
        });

    }

    private void mostrarInformacionProducto(Product p){
        this.productos_id = p;
        getElemento(R.id.lblDes1).setText(p.getDescription());
        getElemento(R.id.lblDes2).setText(p.getBranch());
        getElemento(R.id.lblDes3).setText(p.getColor());
        admin.loadImageFromInternet(
                p.getImagen(),
                (NetworkImageView)getElemento(R.id.img1).getElemento(),
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background);
    }

    @Override
    public void hasAllPermissions() {

    }

    //region Menu

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(getString(R.string.log_out));
//        getMenuInflater().inflate(menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }
        if(item.getTitle()!= null){
            if(item.getTitle().equals(getString(R.string.log_out))){
                DataBase db = DataBase.getInstance(this);
                db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion



}
