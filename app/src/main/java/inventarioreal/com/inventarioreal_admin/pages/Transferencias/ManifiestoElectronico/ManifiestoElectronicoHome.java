package inventarioreal.com.inventarioreal_admin.pages.Transferencias.ManifiestoElectronico;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ManifiestoElectronicoHome extends CicloActivity {

    private Gson gson=new Gson();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this, this, R.layout.activity_manifiesto_electronico_home);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txtLoc), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSal), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnIng), Techniques.FadeInLeft));
    }

    @Override
    public void getData() {
        LoginResponse loginResponse = gson.fromJson(admin.obtener_preferencia(Constants.empleado), LoginResponse.class);
        getElemento(R.id.txtLoc).setText(loginResponse.getEmpleado().getLocales_id().getName());
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnIng, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(ManifiestoElectronicoIngresos.class, null);
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

    //region Menu

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
