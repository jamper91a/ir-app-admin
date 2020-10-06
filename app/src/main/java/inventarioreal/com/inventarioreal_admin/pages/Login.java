package inventarioreal.com.inventarioreal_admin.pages;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import inventarioreal.com.inventarioreal_admin.BuildConfig;
import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.SyncResponse;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class Login extends CicloActivity {
    private String TAG="Login";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_login);
        getSupportActionBar().setTitle(BuildConfig.ENV);


    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txtVersion), Techniques.Bounce));
        addElemento(new Animacion(findViewById(R.id.edtEmail), Techniques.Bounce));
        addElemento(new Animacion(findViewById(R.id.edtPass), Techniques.Bounce));
        addElemento(new Animacion(findViewById(R.id.btnLogin), Techniques.Bounce));
    }

    @Override
    public void getData() {
        getElemento(R.id.txtVersion).setText(getCurrentVersion());
        if(BuildConfig.ENV == "Debug") {
            getElemento(R.id.edtEmail).setText("cajero@ir.com");
            getElemento(R.id.edtPass).setText("12345");
        }
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
                                Gson gson=new Gson();
                                LoginResponse data = (LoginResponse)ok.getData();
                                admin.escribir_preferencia(Constants.employee, gson.toJson(data, LoginResponse.class));
                                admin.callIntent(Home.class, null);

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

    @Override
    public void onBackPressed() {
        admin.exitApp();
    }

    public String getCurrentVersion(){
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
