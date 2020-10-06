package inventarioreal.com.inventarioreal_admin.pages.Transfers.CrearTransferencia;

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

import java.util.Arrays;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.HomeTransferencia;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class CrearTransferenciaStep1 extends CicloActivity {


//    final DataBase db = DataBase.getInstance(this);
    private Transfer request = new Transfer();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_crear_transferencia_step1);
        //toolbar
        getSupportActionBar().setTitle(R.string.crear_transferencia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.titleIcn),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.titleTxt),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtLocOri), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnLocalDestino), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.timeContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtFecha), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txtHora), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnIni), Techniques.FadeInLeft));
    }

    @Override
    public void getData() {
        try {
            //Obtener Zonas
            getShops();
            //Obtener date actual
            getFecha();
        } catch (Exception e) {
            admin.toast(e.getMessage());
        }
    }



    private void getShops() {
        Gson gson = new Gson();
        //Obtengo el usuario almacenado desdes el login para usar el local al cual el usuario es asignado
        final LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        WebServices.getShopsByCompany(this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                Shop[] tiendas = (Shop[]) ok.getData();
                final LinkedList<Shop> shops = new LinkedList<Shop> (Arrays.asList(tiendas));
                //Busco el local actual y lo eliminio de las opciones
                for (int i = 0; i < shops.size(); i++) {
                    if(shops.get(i).getId() == empleado.getEmployee().getShop().getId()){
                        shops.remove(i);
                    }
                }
                ArrayAdapter<Shop> adapter =
                        new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, shops);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                ((Spinner) getElemento(R.id.spnLocalDestino).getElemento()).setAdapter(adapter);

                ((Spinner) getElemento(R.id.spnLocalDestino).getElemento()).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        request.setShopDestination((Shop)shops.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        request.setShopDestination(null);
                    }
                });
            }

            @Override
            public void fail(ResultWebServiceFail fail) {

            }
        });

        //Set Local origen
        request.setShopSource(empleado.getEmployee().getShop());
        getElemento(R.id.txtLocOri).setText(request.getShopSource().getName());

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
                admin.callIntent(CrearTransferenciaStep2.class, request, Transfer.class);
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

    @Override
    public void onBackPressed() {
        admin.callIntent(HomeTransferencia.class, null);
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
}
