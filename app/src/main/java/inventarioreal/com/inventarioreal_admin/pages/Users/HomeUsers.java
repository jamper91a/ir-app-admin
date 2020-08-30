package inventarioreal.com.inventarioreal_admin.pages.Users;

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
import inventarioreal.com.inventarioreal_admin.pages.Transfers.Ingresos;
import inventarioreal.com.inventarioreal_admin.pages.Transfers.ManifiestoElectronico.ManifiestoElectronicoHome;
import inventarioreal.com.inventarioreal_admin.pages.Users.ChangeState.ChangeStateUserStep1;
import inventarioreal.com.inventarioreal_admin.pages.Users.ListEmployees.ListEmployees;
import inventarioreal.com.inventarioreal_admin.pages.Users.UpdateUsers.ModifyUserStep1;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class HomeUsers extends CicloActivity {
    private Class destino = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_home_users);
        //toolbar
        getSupportActionBar().setTitle(R.string.usuarios);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.titleIcn),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.titleTxt),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btn1), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btn2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btn3), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btn4), Techniques.FadeInLeft));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btn1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destino = CreateUser.class;
                sync();

            }
        });
        add_on_click(R.id.btn2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destino = ModifyUserStep1.class;
                sync();

            }
        });
        add_on_click(R.id.btn3, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destino = ChangeStateUserStep1.class;
                sync();

            }
        });
        add_on_click(R.id.btn4, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destino = ListEmployees.class;
                sync();

            }
        });
    }

    private void sync() {

        WebServices.sync(0,HomeUsers.this, admin, new ResultWebServiceInterface() {
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

    @Override
    public void onBackPressed() {
        admin.callIntent(Home.class, null);
    }
    //endregion
}
