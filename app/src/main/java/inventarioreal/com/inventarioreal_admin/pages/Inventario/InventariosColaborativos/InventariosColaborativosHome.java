package inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos;

import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Home;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos.Consolidar.ConsolidarInventariosColaborativosStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos.Crear.Step1.CrearInventarioColaborativoStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos.Unir.UnirseInventariosColaborativos;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos.VisualizarConsolidados.Step1.VisualizarInventariosColaborativosConsolidadosStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos.VisualizarPorZona.Step1.VisualizarInventarioColaborativoPorZonaStep1;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class InventariosColaborativosHome extends CicloActivity {

    private SlidingMenu menu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_inventarios_colaborativos_home);
        this.menu =init_menu(this,R.layout.layout_menu);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.btnCre), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnUni), Techniques.FadeInLeft));
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
                admin.callIntent(CrearInventarioColaborativoStep1.class, null);
            }
        });

        add_on_click(R.id.btnUni, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(UnirseInventariosColaborativos.class, null);
            }
        });

        add_on_click(R.id.btnCon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(ConsolidarInventariosColaborativosStep1.class, null);
            }
        });

        add_on_click(R.id.btnVisPorZon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(VisualizarInventarioColaborativoPorZonaStep1.class, null);
            }
        });
        add_on_click(R.id.btnVisCon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(VisualizarInventariosColaborativosConsolidadosStep1.class, null);
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
