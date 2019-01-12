package inventarioreal.com.inventarioreal_admin.pages;

import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import inventarioreal.com.inventarioreal_admin.R;
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
                menu.showMenu();
            }
        });

        add_on_click(R.id.btnSal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
