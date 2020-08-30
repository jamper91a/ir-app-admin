package inventarioreal.com.inventarioreal_admin.pages.Users.ListEmployees;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Users.ChangeState.ChangeStateUserStep1;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Employee;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.ChangeStateUserRequest;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class DetailsEmployee extends CicloActivity {
    private Employee employee;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_users_details);
        getSupportActionBar().setTitle(R.string.usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.titleIcn),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.titleTxt),Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtLocalContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtLocal), Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.txt4), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtTypeContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtType), Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.txt3), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEmailContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEmail), Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.txt2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtNameContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtName), Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.txt5), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtStateContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtState), Techniques.FadeInLeft));
    }

    @Override
    public void getData() {
        //toolbar
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.employee = gson.fromJson(message, Employee.class);
        getElemento(R.id.edtLocal).setText(this.employee.getShop().getName());
        getElemento(R.id.edtName).setText(this.employee.getUser().getName());
        getElemento(R.id.edtEmail).setText(this.employee.getUser().getUsername());
        getElemento(R.id.edtType).setText(this.employee.getUser().getGroup().getName());
        getElemento(R.id.edtState).setText(
                this.employee.getUser().getActive() ? getString(R.string.active) : getString(R.string.no_active)
        );



    }


    @Override
    public void initOnClick() {
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

