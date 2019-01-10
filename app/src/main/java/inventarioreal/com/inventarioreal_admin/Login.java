package inventarioreal.com.inventarioreal_admin;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;

import inventarioreal.com.inventarioreal_admin.pojo.WebServices.LoginResponseWebService;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class Login extends CicloActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_login);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion((EditText)findViewById(R.id.edtEmail), Techniques.Bounce));
        addElemento(new Animacion((EditText)findViewById(R.id.edtPass), Techniques.Bounce));
        addElemento(new Animacion((Button)findViewById(R.id.btnLogin), Techniques.Bounce));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnLogin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServices.login(
                        getElemento(R.id.edtEmail).getText(),
                        getElemento(R.id.edtPass).getText(),
                        Login.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                LoginResponseWebService data = (LoginResponseWebService)ok.getData();
                                admin.toast("Thanks "+data.getEmpleado().getUser_id().getUsername());
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast(fail.getError());
                            }
                        }
                );
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }
}
