package inventarioreal.com.inventarioreal_admin.pages.Cashiers;

import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Cashiers.Sell.SellCommodity;
import inventarioreal.com.inventarioreal_admin.pages.Home;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class HomeCashier extends CicloActivity {

    private SlidingMenu menu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_home_cashier);
        this.menu =init_menu(this,R.layout.layout_menu);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.btnIni),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSal),Techniques.FadeInLeft));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {

        add_on_click(R.id.btnIni, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(SellCommodity.class);

            }
        });


        add_on_click(R.id.btnSal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(Home.class, null);
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
        WebServices.sync(HomeCashier.this, admin, new ResultWebServiceInterface() {
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
