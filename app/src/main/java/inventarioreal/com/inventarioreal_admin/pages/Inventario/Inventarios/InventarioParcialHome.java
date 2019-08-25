package inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios;

import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Home;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.Consolidar.ConsolidarInventarioStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.Crear.Step1.CrearInventarioStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.VisualizarConsolidados.Step1.VisualizarInventariosConsolidadosStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.VisualizarPorZona.Step1.VisualizarInventarioPorZonaStep1;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class InventarioParcialHome extends CicloActivity {

    private SlidingMenu menu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_inventario_home);
        this.menu =init_menu(this,R.layout.layout_menu);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.btnCre),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnCon),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnVisPorZon),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnVisCon),Techniques.FadeInLeft));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnCre, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(CrearInventarioStep1.class, null);
            }
        });

        add_on_click(R.id.btnCon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(ConsolidarInventarioStep1.class, null);
            }
        });

        add_on_click(R.id.btnVisPorZon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(VisualizarInventarioPorZonaStep1.class, null);
            }
        });
        add_on_click(R.id.btnVisCon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(VisualizarInventariosConsolidadosStep1.class, null);
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
}
