package inventarioreal.com.inventarioreal_admin.pages.Reportes.InventarioEanPlu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Productos;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class InventarioEanPlu extends CicloActivity {

    private String TAG="IngresoMercancia";
    //private UhfManager uhfManager;
    private ArrayList<ProductosZonas> productos = new ArrayList<>();
    private Productos productos_id=null;
    private ListAdapterInventarioEanPlu adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(this,this,R.layout.activity_inventario_ean_plu);
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
        WebServices.findProductsByEanPlu(productos_id.id, this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                productos = (ArrayList<ProductosZonas>) ok.getData();
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
                                Productos productoConsultado = (Productos) ok.getData();
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

    private void mostrarInformacionProducto(Productos p){
        this.productos_id = p;
        getElemento(R.id.lblDes1).setText(p.getDescripcion());
        getElemento(R.id.lblDes2).setText(p.getMarca());
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



}
