package inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Home;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Consolidate.ConsolidarInventarioStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step1.CrearInventarioStep1;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.ViewIntentoriesConsolidated.Step1.VisualizarInventariosConsolidados;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.ViewInventoriesByZone.Step1.VisualizarInventarioPorZonaStep1;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class InventarioParcialHome extends CicloActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_inventario_home);
        // toolbar
        getSupportActionBar().setTitle(R.string.inventarios);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.btnCre),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnCon),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnVisPorZon),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnVisCon),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnRet),Techniques.FadeInLeft));
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
                admin.callIntent(VisualizarInventariosConsolidados.class, null);
            }
        });
        add_on_click(R.id.btnRet, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
