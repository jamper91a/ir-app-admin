package inventarioreal.com.inventarioreal_admin.pages.Transferencias;

import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Transferencias.CrearTransferencia.CrearTransferenciaStep1;
import inventarioreal.com.inventarioreal_admin.pages.Transferencias.ManifiestoElectronico.ManifiestoElectronicoHome;
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

}
