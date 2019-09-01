package inventarioreal.com.inventarioreal_admin.pages.Transfers;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Home;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.CrearTransferencia.CrearTransferenciaStep1;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.ManifiestoElectronico.ManifiestoElectronicoHome;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class HomeTransferencia extends CicloActivity {
    private SlidingMenu menu;
    private Class destino = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_home_transferencia);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.btnIng), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSal), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnMan), Techniques.FadeInLeft));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnIng, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destino = Ingresos.class;
                sync();

            }
        });
        add_on_click(R.id.btnSal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destino = CrearTransferenciaStep1.class;
                sync();

            }
        });
        add_on_click(R.id.btnMan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destino = ManifiestoElectronicoHome.class;
                sync();

            }
        });
    }

    private void sync() {

        WebServices.sync(HomeTransferencia.this, admin, new ResultWebServiceInterface() {
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

    @Override
    public void hasAllPermissions() {

    }

    //region Menu

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(getString(R.string.log_out));
//        getMenuInflater().inflate(menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.getTitle().equals(getString(R.string.log_out))){
            admin.log_out(Login.class);
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_favorite) {
//            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        admin.callIntent(Home.class, null);
    }

    //endregion
}
