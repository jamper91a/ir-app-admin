package inventarioreal.com.inventarioreal_admin.pages;

import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Cashiers.HomeCashier;
import inventarioreal.com.inventarioreal_admin.pages.Commodity.AddCommodity;
import inventarioreal.com.inventarioreal_admin.pages.Devolutions.HomeDevoluciones;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.InventarioParcialHome;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.InventariosColaborativosHome;
import inventarioreal.com.inventarioreal_admin.pages.Reports.HomeReportes;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.HomeTransferencia;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class Home extends CicloActivity {

    private SlidingMenu menu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_home);
        this.menu =init_menu(this,R.layout.layout_menu);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.btnIng),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSalMer),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnInv),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnInvCoo),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnTrans),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnDev),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnRep),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnUsu),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBusYGeo),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSal),Techniques.FadeInLeft));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {

        add_on_click(R.id.btnIng, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(AddCommodity.class);

            }
        });
        add_on_click(R.id.btnSalMer, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(HomeCashier.class);

            }
        });
        add_on_click(R.id.btnInv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(InventarioParcialHome.class);
            }
        });
        add_on_click(R.id.btnInvCoo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(InventariosColaborativosHome.class);
            }
        });

        add_on_click(R.id.btnTrans, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(HomeTransferencia.class);
            }
        });

        add_on_click(R.id.btnDev, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(HomeDevoluciones.class);
            }
        });
        add_on_click(R.id.btnRep, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(HomeReportes.class, null);
            }
        });

        add_on_click(R.id.btnInvCoo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(InventariosColaborativosHome.class);
            }
        });

        add_on_click(R.id.btnBusYGeo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                admin.callIntent(crear_inventario.class, null);
                sync(ReaderActivity.class);
            }
        });

        add_on_click(R.id.btnSal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBase db = DataBase.getInstance(Home.this);
                db.deleteAllData();
                admin.log_out(Login.class);
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

    @Override
    public void onBackPressed() {
        admin.exitApp();
    }

    public void sync(final Class destino){
        WebServices.sync(Home.this, admin, new ResultWebServiceInterface() {
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
