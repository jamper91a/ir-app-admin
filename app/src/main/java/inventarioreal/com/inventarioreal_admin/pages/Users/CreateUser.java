package inventarioreal.com.inventarioreal_admin.pages.Users;

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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CreateUserRequest;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class CreateUser extends CicloActivity {
    final DataBase db = DataBase.getInstance(this);
    private CreateUserRequest request;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_users_modify_step_2);
        //toolbar
        getSupportActionBar().setTitle(R.string.crear_nuevo_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnLocal), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEmail), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt3), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtPassword), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt4), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtRPassword), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt5), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnType), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btn1), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btn2), Techniques.FadeInLeft));
    }

    @Override
    public void getData() {
        this.request = new CreateUserRequest(this, this.admin);
        this.getShops();
        this.getUserTypes();

    }

    private void getShops() {
        Gson gson = new Gson();
        //Obtengo el usuario almacenado desdes el login para usar el local al cual el usuario es asignado
        LoginResponse employee = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        //Obtengo las zonas usando el local del employee
        final LinkedList<Shop> shops = db.getByColumn(
                Constants.table_shops,
                Constants.column_company,
                employee.getEmployee().getCompany().getId()+ "",
                Shop.class);


        ArrayAdapter<Shop> adapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, shops);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner) getElemento(R.id.spnLocal).getElemento()).setAdapter(adapter);

        ((Spinner) getElemento(R.id.spnLocal).getElemento()).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setShop((shops.get(position)).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                request.setShop(0);
            }
        });

        request.setCompany(employee.getEmployee().getCompany().getId());


    }

    private void getUserTypes() {
        Gson gson = new Gson();
        final LinkedList<UserType> userTypes = new LinkedList<>();
        userTypes.add(new UserType(2, "Administrador"));
        userTypes.add(new UserType(3, "Cajero"));
        userTypes.add(new UserType(4, "Bodega"));
        ArrayAdapter<UserType> adapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner) getElemento(R.id.spnType).getElemento()).setAdapter(adapter);

        ((Spinner) getElemento(R.id.spnType).getElemento()).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setType((userTypes.get(position)).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                request.setType(0);
            }
        });


    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btn1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getElemento(R.id.edtEmail).setText("");
                getElemento(R.id.edtPassword).setText("");
                getElemento(R.id.edtRPassword).setText("");

            }
        });
        add_on_click(R.id.btn2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    request.setUsername(getElemento(R.id.edtEmail).getText());
                    request.setPassword(getElemento(R.id.edtPassword).getText());
                    request.setRpassword(getElemento(R.id.edtRPassword).getText());
                    request.validar();
                    WebServices.createUser(CreateUser.this, request, admin, new ResultWebServiceInterface() {
                        @Override
                        public void ok(ResultWebServiceOk ok) {
                            admin.toast(getString(R.string.usuario_creado_exitosamente));
                            onBackPressed();
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

    @Override
    public void onBackPressed() {
        admin.callIntent(HomeUsers.class, null);
    }
    //endregion
}

class UserType{
    private int id;
    private String name;

    public UserType() {
    }

    public UserType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
