package inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Home;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.Consolidate.ConsolidarInventariosCooperativoStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.Create.Step1.CrearInventarioCooperativoStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.Join.UnirseInventariosCooperativo;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.ViewInventoriesConsolidated.Step1.ViewCoopInvConsolidatedStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.ViewInventoriesByZone.Step1.VisualizarInventarioCooperativoPorZonaStep1;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class InventariosColaborativosHome extends CicloActivity {

    private SlidingMenu menu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_inventarios_colaborativos_home);
        this.menu =init_menu(this,R.layout.layout_menu);
        // toolbar
        getSupportActionBar().setTitle(R.string.inventario_cooperativo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
                admin.callIntent(CrearInventarioCooperativoStep1.class, null);
            }
        });

        add_on_click(R.id.btnUni, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(UnirseInventariosCooperativo.class, null);
            }
        });

        add_on_click(R.id.btnCon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(ConsolidarInventariosCooperativoStep1.class, null);
            }
        });

        add_on_click(R.id.btnVisPorZon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(VisualizarInventarioCooperativoPorZonaStep1.class, null);
            }
        });
        add_on_click(R.id.btnVisCon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(ViewCoopInvConsolidatedStep1.class, null);
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
