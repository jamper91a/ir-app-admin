package inventarioreal.com.inventarioreal_admin.pages.Inventario;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Intents.RequestInventariorCrear2;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class InventarioCrear1 extends CicloActivity {

    private SlidingMenu menu;
    final DataBase db = DataBase.getInstance(this);

    private RequestInventariorCrear2 request = new RequestInventariorCrear2();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_inventario_crear_1);
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

        sync();


    }

    private void sync(){

        WebServices.sync(InventarioCrear1.this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                //Obtener Zonas
                getZonas();
                //Obtener poder de lecura
                getPoderLectura();
                //Obtener fecha actual
                getFecha();
            }

            @Override
            public void fail(ResultWebServiceFail fail) {
                admin.toast(fail.getError());

            }
        });
    }

    private void getZonas(){
        Gson gson = new Gson();
        //Obtengo el usuario almacenado desdes el login para usar el local al cual el usuario es asignado
        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.empleado), LoginResponse.class);
        //Obtengo las zonas usando el local del empleado
//        LinkedList<HashMap<String, String>> zonas=db.getByColumn(Constants.table_zonas,Constants.locales_id, empleado.getEmpleado().getLocales_id().getId());
        final LinkedList zonas=db.getByColumn(
                Constants.table_zonas,
                Constants.locales_id,
                empleado.getEmpleado().getLocales_id().getId()+"",
                Zonas.class);

        ArrayAdapter<Zonas> adapter =
                new ArrayAdapter<>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, zonas);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        ((Spinner)getElemento(R.id.spnZona).getElemento()).setAdapter(adapter);

        ((Spinner)getElemento(R.id.spnZona).getElemento()).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setZona_id((Zonas)zonas.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getPoderLectura(){
        final String[] poderLectura = new String[]{"100","500","1000"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, poderLectura);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        ((Spinner)getElemento(R.id.spnPodLec).getElemento()).setAdapter(adapter);
        ((Spinner)getElemento(R.id.spnPodLec).getElemento()).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setPower(poderLectura[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getFecha(){
        request.setFecha(admin.getCurrentDateAndTime());
        getElemento(R.id.txtFecha).setText(request.getFecha());
    }
    @Override
    public void initOnClick() {
        add_on_click(R.id.btnIni, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Valido que la informacion este completa
                if(request.validar())
                    admin.callIntent(InventarioCrear2.class, request, RequestInventariorCrear2.class);
                else
                    admin.toast("Revisa los datos");

            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

}
