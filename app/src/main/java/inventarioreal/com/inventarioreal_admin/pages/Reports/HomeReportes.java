package inventarioreal.com.inventarioreal_admin.pages.Reports;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Home;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenceInventoryErp.ReportInventoryErp;
import inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenciaInventariosFisicos.DIFStep1;
import inventarioreal.com.inventarioreal_admin.pages.Reports.HomologateDifferences.HomologateDiferencesStep1;
import inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioEanPlu.InventarioEanPlu;
import inventarioreal.com.inventarioreal_admin.pages.Reports.InventarioTotal.ReporteInventarioTotal;
import inventarioreal.com.inventarioreal_admin.pages.Reports.Returns.REStep1;
import inventarioreal.com.inventarioreal_admin.pages.Reports.RotationProyected.ReportRotationProyectedStep1;
import inventarioreal.com.inventarioreal_admin.pages.Reports.RotationUnits.RUStep1;
import inventarioreal.com.inventarioreal_admin.pages.Reports.SaleUnits.SUStep1;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.DiferenceInventoryErp;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class HomeReportes extends CicloActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_home_reportes);
        //toolbar
        getSupportActionBar().setTitle(R.string.reportes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        add_on_click(R.id.btnHomDifInvFis, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(HomologateDiferencesStep1.class);

            }
        });
        add_on_click(R.id.btnDifInvFisYErp, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(ReportInventoryErp.class, null);

            }
        });
        add_on_click(R.id.btnUniVen, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(SUStep1.class);

            }
        });
        add_on_click(R.id.btnRotInv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(RUStep1.class);

            }
        });
        add_on_click(R.id.btnRotPro, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync(ReportRotationProyectedStep1.class);

            }
        });
        add_on_click(R.id.btnDevCli, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(REStep1.class, 1, Integer.class);
            }
        });
        add_on_click(R.id.btnDevPro, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(REStep1.class, 2, Integer.class);
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
