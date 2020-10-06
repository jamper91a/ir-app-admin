package inventarioreal.com.inventarioreal_admin.pages.Users.UpdateUsers;

import android.content.Intent;
import android.content.res.ColorStateList;
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
import inventarioreal.com.inventarioreal_admin.pages.Users.HomeUsers;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Employee;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Shop;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.User;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests.CreateUserRequest;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Group;

public class ModifyUserStep2 extends CicloActivity {
//    final DataBase db = DataBase.getInstance(this);
    private CreateUserRequest request;
    private Employee employee;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_users_modify_step_2);
        getSupportActionBar().setTitle(R.string.modificar_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.titleIcn),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.titleTxt),Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.spnLocalContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnLocal), Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.spnTypeContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.txt6), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.spnType), Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.txt3), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEmailContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEmail), Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.txt4), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtPasswordContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtPassword), Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.txt5), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtRPasswordContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtRPassword), Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.txt2), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtNameContainer), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtName), Techniques.FadeInLeft));

        addElemento(new Animacion(findViewById(R.id.btn1), Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btn2), Techniques.FadeInLeft));

        ((ImageView)getElemento(R.id.titleIcn).getElemento()).setImageDrawable(getDrawable(R.drawable.icn_users_edit_white));
        ((ImageView)getElemento(R.id.titleIcn).getElemento()).setImageTintList(ColorStateList.valueOf(getColor(R.color.blue_dark)));
        getElemento(R.id.titleIcn).setText(getString(R.string.modificar_usuario));
    }

    @Override
    public void getData() {
        //toolbar
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.employee = gson.fromJson(message, Employee.class);
        this.request = new CreateUserRequest(this, this.admin);

        this.getShops();
        this.getUserTypes();

        getElemento(R.id.edtName).setText(this.employee.getUser().getName());
        getElemento(R.id.edtEmail).setText(this.employee.getUser().getUsername());



    }

    private void getShops() {

        Gson gson = new Gson();
        final Spinner spnLocal = ((Spinner) getElemento(R.id.spnLocal).getElemento());
        //Obtengo el usuario almacenado desdes el login para usar el local al cual el usuario es asignado
        final LoginResponse empleado = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
        WebServices.getShopsByCompany(this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                Shop[] tiendas = (Shop[]) ok.getData();
                final LinkedList<Shop> shops = new LinkedList<Shop> (Arrays.asList(tiendas));

                ArrayAdapter<Shop> adapter =
                        new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, shops);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnLocal.setAdapter(adapter);

                spnLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        request.setShop((shops.get(position)).getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        request.setShop(0);
                    }
                });

                request.setCompany(empleado.getEmployee().getCompany().getId());

                if(employee.getShop()!=null){
                    int pos = getShopIndex(spnLocal, employee.getShop());
                    spnLocal.setSelection(pos);
                }
            }

            @Override
            public void fail(ResultWebServiceFail fail) {

            }
        });

//
//
//        Gson gson = new Gson();
//        Spinner spnLocal = ((Spinner) getElemento(R.id.spnLocal).getElemento());
//        //Obtengo el usuario almacenado desdes el login para usar el local al cual el usuario es asignado
//        LoginResponse employee = gson.fromJson(admin.obtener_preferencia(Constants.employee), LoginResponse.class);
//        //Obtengo las zonas usando el local del employee
//        final LinkedList<Shop> shops = db.getByColumn(
//                Constants.table_shops,
//                Constants.column_company,
//                employee.getEmployee().getCompany().getId()+ "",
//                Shop.class);
//
//
//        ArrayAdapter<Shop> adapter =
//                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, shops);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spnLocal.setAdapter(adapter);
//
//        spnLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                request.setShop((shops.get(position)).getId());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                request.setShop(0);
//            }
//        });
//
//        request.setCompany(employee.getEmployee().getCompany().getId());
//
//        if(this.employee.getShop()!=null){
//            int pos = getShopIndex(spnLocal, this.employee.getShop());
//            spnLocal.setSelection(pos);
//        }


    }

    //private method of your class
    private int getShopIndex(Spinner spinner, Shop myShop){
        for (int i=0;i<spinner.getCount();i++){
            Shop aux = (Shop) spinner.getItemAtPosition(i);
            if(aux.getId() == myShop.getId())
            {
                return i;
            }
        }

        return 0;
    }

    private void getUserTypes() {
        Gson gson = new Gson();
        Spinner spnType = ((Spinner) getElemento(R.id.spnType).getElemento());
        final LinkedList<UserType> userTypes = new LinkedList<>();
        userTypes.add(new UserType(2, "Administrador"));
        userTypes.add(new UserType(3, "Cajero"));
        userTypes.add(new UserType(4, "Bodega"));
        ArrayAdapter<UserType> adapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnType.setAdapter(adapter);

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setType((userTypes.get(position)).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                request.setType(0);
            }
        });

        if(this.employee.getUser().getGroup()!=null){
            int pos = getUserTypeIndex(spnType, this.employee.getUser().getGroup());
            spnType.setSelection(pos);
        }


    }

    private int getUserTypeIndex(Spinner spinner, Group myGroup){
        for (int i=0;i<spinner.getCount();i++){
            UserType aux = (UserType) spinner.getItemAtPosition(i);
            if(aux.getId() == myGroup.getId())
            {
                return i;
            }
        }

        return 0;
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btn1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getElemento(R.id.edtName).setText("");
                getElemento(R.id.edtPassword).setText("");
                getElemento(R.id.edtRPassword).setText("");

            }
        });
        add_on_click(R.id.btn2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    request.setName(getElemento(R.id.edtName).getText());
                    request.setUsername(getElemento(R.id.edtEmail).getText());
                    request.setPassword(getElemento(R.id.edtPassword).getText());
                    request.setRpassword(getElemento(R.id.edtRPassword).getText());
                    request.validar();
                    WebServices.modifyUser(ModifyUserStep2.this, request, admin, new ResultWebServiceInterface() {
                        @Override
                        public void ok(ResultWebServiceOk ok) {
                            admin.toast(getString(R.string.usuario_modificado_exitosamente));
                            admin.callIntent(ModifyUserStep1.class, null);
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
                //DataBase db = DataBase.getInstance(this);
                //db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
////        admin.callIntent(ModifyUserStep1.class, null);
//        onBackPressed();
//
//    }
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
