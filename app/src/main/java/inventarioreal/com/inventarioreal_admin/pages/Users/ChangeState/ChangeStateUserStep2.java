package inventarioreal.com.inventarioreal_admin.pages.Users.ChangeState;

import android.content.Intent;
import android.os.Bundle;
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
import inventarioreal.com.inventarioreal_admin.pages.Users.UpdateUsers.ModifyUserStep1;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Employee;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Group;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.ChangeStateUserRequest;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CreateUserRequest;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ChangeStateUserStep2 extends CicloActivity {
    final DataBase db = DataBase.getInstance(this);
    private ChangeStateUserRequest request;
    private Employee employee;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_users_change_state_step_2);
        getSupportActionBar().setTitle(R.string.activar_desactivar_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtLocal), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEmail), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt3), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtType), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt4), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtState), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btn1), Techniques.FadeInLeft));
    }

    @Override
    public void getData() {
        //toolbar
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.employee = gson.fromJson(message, Employee.class);
        this.request = new ChangeStateUserRequest();
        this.request.setUsername(this.employee.getUser().getUsername());
        getElemento(R.id.edtLocal).setText(this.employee.getShop().getName());
        getElemento(R.id.edtEmail).setText(this.employee.getUser().getUsername());
        getElemento(R.id.edtType).setText(this.employee.getUser().getGroup().getName());
        getElemento(R.id.edtState).setText(
                this.employee.getUser().getActive() ? getString(R.string.active) : getString(R.string.no_active)
        );



    }


    @Override
    public void initOnClick() {
        add_on_click(R.id.btn1,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    request.setActive(!employee.getUser().getActive());
                    WebServices.changeStateUser(ChangeStateUserStep2.this, request, admin, new ResultWebServiceInterface() {
                        @Override
                        public void ok(ResultWebServiceOk ok) {
                            admin.toast(getString(R.string.usuario_modificado_exitosamente));
                            admin.callIntent(ChangeStateUserStep1.class, null);
                        }

                        @Override
                        public void fail(ResultWebServiceFail fail) {
                            admin.toast(fail.getError());
                        }
                    });

                } catch (Error e) {
                    admin.toast(e.getMessage());
                }
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

    //endregion
}

