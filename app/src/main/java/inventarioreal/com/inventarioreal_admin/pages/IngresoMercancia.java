package inventarioreal.com.inventarioreal_admin.pages;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.Producto;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class IngresoMercancia extends CicloActivity {

    private SlidingMenu menu;
    private String TAG="IngresoMercancia";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_ingreso_mercancia);
//        this.menu =init_menu(this,R.layout.layout_menu);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.lbl1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEanPlu),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBus),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes3),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.img1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lbl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSi),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnNo),Techniques.FadeInLeft));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnBus, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServices.getProductByEanPlu(
                        getElemento(R.id.edtEanPlu).getText(),
                        IngresoMercancia.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                Producto producto = (Producto) ok.getData();
                                mostrarInformacionProducto(producto);
                                admin.loadImageFromInternet(
                                        "https://m.media-amazon.com/images/I/A13usaonutL._CLa%7C2140,2000%7C5118hwdonfL.png%7C0,0,2140,2000+0.0,0.0,2140.0,2000.0._UX522_.png",
                                        (NetworkImageView)getElemento(R.id.img1).getElemento(),
                                        R.drawable.ic_launcher_background,
                                        R.drawable.ic_launcher_background);


                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast(fail.getError());
                            }
                        });
            }
        });
    }

    private void mostrarInformacionProducto(Producto p){
        getElemento(R.id.lblDes1).setText(p.getDescripcion());
        getElemento(R.id.lblDes2).setText(p.getMarca());
        getElemento(R.id.lblDes3).setText(p.getColor());
    }

    @Override
    public void hasAllPermissions() {

    }

}
