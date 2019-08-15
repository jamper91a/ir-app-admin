package inventarioreal.com.inventarioreal_admin.pages.Reportes;

import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Home;
import inventarioreal.com.inventarioreal_admin.pages.Reportes.DiferenciaInventariosFisicos.DIFStep1;
import inventarioreal.com.inventarioreal_admin.pages.Reportes.InventarioEanPlu.InventarioEanPlu;
import inventarioreal.com.inventarioreal_admin.pages.Reportes.InventarioTotal.ReporteInventarioTotal;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class HomeReportes extends CicloActivity {

    private SlidingMenu menu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_home_reportes);
        this.menu =init_menu(this,R.layout.layout_menu);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.btnInvTot),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnInvEanPlu),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnDifInvFis),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnDifInvFisYErp),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnHomDifInvFis),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnUniVen),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnRotInv),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnDevCli),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnDevPro),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnRotPro),Techniques.FadeInLeft));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {

        add_on_click(R.id.btnInvTot, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(ReporteInventarioTotal.class);

            }
        });
        add_on_click(R.id.btnInvEanPlu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(InventarioEanPlu.class);

            }
        });
        add_on_click(R.id.btnDifInvFis, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(DIFStep1.class);

            }
        });

    }

    @Override
    public void hasAllPermissions() {

    }

    @Override
    public void onBackPressed() {
        admin.callIntent(Home.class, null);
    }

    public void sync(final Class destino){
        WebServices.sync(HomeReportes.this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                admin.callIntent(destino, null);
            }

            @Override
            public void fail(ResultWebServiceFail fail) {
                admin.toast(fail.getError());
                admin.callIntent(destino, null);


            }
        });
    }
}
