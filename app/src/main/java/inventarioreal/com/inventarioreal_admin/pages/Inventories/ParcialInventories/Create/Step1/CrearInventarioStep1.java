package inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.Intents.RequestCreateInventory2;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.Step2.CrearInventarioStep2;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.InventarioParcialHome;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class CrearInventarioStep1 extends CicloActivity {

    private SlidingMenu menu;
    final DataBase db = DataBase.getInstance(this);
    private RequestCreateInventory2 request = new RequestCreateInventory2();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this, this, R.layout.activity_inventario_parcial_crear_inventario_step_1);
        this.menu = init_menu(this, R.layout.layout_menu);
        // toolbar
        getSupportActionBar().setTitle("Crear Inventarios Parciales");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txtLocal), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnZona), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnPodLec), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtFecha), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnIni), Techniques.FadeInLeft));
    }

    @Override
    public void getData() {
        //Obtener informacion actual del usuario
        try {
            LoginResponse empleado = new Gson().fromJson(admin.obtener_preferencia(Constants.employee),LoginResponse.class);
            if(empleado != null){
                getElemento(R.id.txtLocal).setText(empleado.getEmployee().getShop().getName());
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        sync();
    }

    private void sync() {

        WebServices.sync(CrearInventarioStep1.this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                //Obtener Zonas
                getZonas();
                //Obtener poder de lecura
                getPoderLectura();
                //Obtener date actual
                getFecha();
            }

            @Override
            public void fail(ResultWebServiceFail fail) {
                admin.toast(fail.getError());

            }
        });
    }

    private void getZonas() {
        Gson gson = new Gson();
        //Obtengo el usuario almacenado desdes el login para usar el local al cual el usuario es asignado
        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        //Obtengo las zonas usando el local del employee
//        LinkedList<HashMap<String, String>> zonas=db.getByColumn(Constants.table_zones,Constants.locales_id, employee.getEmployee().getShop().getId());
        final LinkedList zonas = db.getByColumn(
                Constants.table_zones,
                Constants.column_shop,
                empleado.getEmployee().getShop().getId() + "",
                Zone.class);

        ArrayAdapter<Zone> adapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, zonas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner) getElemento(R.id.spnZona).getElemento()).setAdapter(adapter);

        ((Spinner) getElemento(R.id.spnZona).getElemento()).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setZone((Zone) zonas.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getPoderLectura() {
        final String[] poderLectura = new String[]{"100", "500", "1000"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, poderLectura);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner) getElemento(R.id.spnPodLec).getElemento()).setAdapter(adapter);
        ((Spinner) getElemento(R.id.spnPodLec).getElemento()).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setPower(poderLectura[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getFecha() {
        request.setDate(admin.getCurrentDateAndTime());
        getElemento(R.id.txtFecha).setText(request.getDate());
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnIni, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Valido que la informacion este completa
                if (request.validar())
                    admin.callIntent(CrearInventarioStep2.class, request, RequestCreateInventory2.class);
                else
                    admin.toast("Revisa los datos");

            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

    @Override
    public void onBackPressed() {
        admin.callIntent(InventarioParcialHome.class, null);
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


