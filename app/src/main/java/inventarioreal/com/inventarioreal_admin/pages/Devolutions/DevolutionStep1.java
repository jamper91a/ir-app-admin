package inventarioreal.com.inventarioreal_admin.pages.Devolutions;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Devolution;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Administrador;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class DevolutionStep1 extends CicloActivity {


//    final DataBase db = DataBase.getInstance(this);
    private ProductHasZone request = new ProductHasZone();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_devolucion_decliente_step1);
        // toolbar
        getSupportActionBar().setTitle(R.string.devoluciones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.titleIcn),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.titleTxt),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblLocal), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtLocal), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblZona), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnZonaDestino), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnPodLec), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.timeContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtFecha), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtHora), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnIni), Techniques.FadeInLeft));




}

    @Override
    public void getData() {
        try {
            Intent intent = getIntent();
            String message = intent.getStringExtra(Constants.parameters);
            Devolution devolucion = new Devolution();
            devolucion.setType(Integer.parseInt(message));
            request.setDevolution(devolucion);
            //Obtener Zonas
            getZonas();
            //Obtener poder de lecura
            getPoderLectura();
            //Obtener date actual
            getFecha();
            ImageView titleIcn = (ImageView)getElemento(R.id.titleIcn).getElemento();
            switch (devolucion.getType()){
                case 1:
                    getElemento(R.id.titleTxt).setText(getString(R.string.devoluciones_clientes));
                    titleIcn.setImageDrawable(getDrawable(R.drawable.icn_returns_clients_blue));
                    break;
                case 2:
                    getElemento(R.id.titleTxt).setText(getString(R.string.devoluciones_proveedores));
                    titleIcn.setImageDrawable(getDrawable(R.drawable.icn_returns_providers_blue));
                    break;
            }
        } catch (Exception e) {
            admin.toast(e.getMessage());
        }
    }



    private void getZonas() {

        WebServices.getZonesByShopId(
                this,
                admin,
                new ResultWebServiceInterface() {
                    @Override
                    public void ok(ResultWebServiceOk ok)
                    {
                        Zone[] zones = (Zone[]) ok.getData();
                        final LinkedList zonas = new LinkedList<> (Arrays.asList(zones));
                        ArrayAdapter<Zone> adapter =
                                new ArrayAdapter<>(getApplicationContext(), R.layout.simple_spinner_item, zonas);
                        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

                        ((Spinner)getElemento(R.id.spnZonaDestino).getElemento()).setAdapter(adapter);

                        ((Spinner)getElemento(R.id.spnZonaDestino).getElemento()).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                request.setZone((Zone) zonas.get(position));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void fail(ResultWebServiceFail fail) {
                        admin.toast(fail.getError());
                    }
                }
        );
        Gson gson = new Gson();
        //Obtengo el usuario almacenado desdes el login para usar el local al cual el usuario es asignado
        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);

        getElemento(R.id.txtLocal).setText(empleado.getEmployee().getShop().getName());
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
//                request.setPower(poderLectura[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getFecha() {
        request.setCreatedAt(admin.getCurrentDateAndTime());
        String[] date = request.getCreatedAt().split(" ");
        getElemento(R.id.txtFecha).setText(date[0]);
        getElemento(R.id.txtHora).setText(date[1]);
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnIni, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Administrador.callIntent(DevolutionStep2.class, request, ProductHasZone.class);
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


        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }
        if(item.getTitle()!= null){
            if(item.getTitle().equals(getString(R.string.log_out))){
                //DataBase db = DataBase.getInstance(this);
                //db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
    @Override
    public void onBackPressed() {
        Administrador.callIntent(HomeDevoluciones.class, null);
    }

    //endregion
}