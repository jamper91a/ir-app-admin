package inventarioreal.com.inventarioreal_admin.pages.Transferencias.CrearTransferencia;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class CrearTransferenciaStep1 extends CicloActivity {


    final DataBase db = DataBase.getInstance(this);
    private Transfer request = new Transfer();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_crear_transferencia_step1);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtLocOri), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnLocalDestino), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt3), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnPodLec), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtFecha), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnIni), Techniques.FadeInLeft));
    }

    @Override
    public void getData() {
        try {
            //Obtener Zonas
            getLocales();
            //Obtener poder de lecura
            getPoderLectura();
            //Obtener date actual
            getFecha();
        } catch (Exception e) {
            admin.toast(e.getMessage());
        }
    }



    private void getLocales() {
        Gson gson = new Gson();
        //Obtengo el usuario almacenado desdes el login para usar el local al cual el usuario es asignado
        LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        //Obtengo las zonas usando el local del employee
        final LinkedList locales = db.getByColumn(
                Constants.table_shops,
                Constants.column_company,
                empleado.getEmployee().getCompany().getId()+ "",
                Shop.class);

        ArrayAdapter<Shop> adapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, locales);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner) getElemento(R.id.spnLocalDestino).getElemento()).setAdapter(adapter);

        ((Spinner) getElemento(R.id.spnLocalDestino).getElemento()).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setShopDestination((Shop)locales.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                request.setShopDestination(null);
            }
        });

        //Set Local origen
        request.setShopSource(empleado.getEmployee().getShop());
        Log.d("lOCAL", request.getShopSource().getName());
        getElemento(R.id.txtLocOri).setText(request.getShopSource().getName());

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
        getElemento(R.id.txtFecha).setText(request.getCreatedAt());
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnIni, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(CrearTransferenciaStep2.class, request, Transfer.class);
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

    //endregion
}
