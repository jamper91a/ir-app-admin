package inventarioreal.com.inventarioreal_admin.pages;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.daimajia.androidanimations.library.Techniques;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.ZonasListarResponse;
import inventarioreal.com.inventarioreal_admin.pojo.Zona;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class Inventario_Crear extends CicloActivity {

    private SlidingMenu menu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_inventario_crear);
        this.menu =init_menu(this,R.layout.layout_menu);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txtLocal),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnZona),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnPodLec),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtFecha),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnIni),Techniques.FadeInLeft));
    }

    @Override
    public void getData() {
        //Obtener Zonas
        WebServices.listarZonas(this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                ZonasListarResponse response = (ZonasListarResponse)ok.getData();

                ArrayAdapter<Zona> adapter =
                        new ArrayAdapter<Zona>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, response.getZonas());
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                ((Spinner)getElemento(R.id.spnZona).getElemento()).setAdapter(adapter);
            }

            @Override
            public void fail(ResultWebServiceFail fail) {
                admin.toast(fail.getError());
            }
        });
        //Obtener poder de lecura
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnIni, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

}
