package inventarioreal.com.inventarioreal_admin;

import android.Manifest;
import android.os.Bundle;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;

import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class SplashScreen extends CicloActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String [] permisos = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE
        };
        setPermissions(permisos);
        init(this,this,R.layout.activity_splash_screen);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion((ImageView)findViewById(R.id.img1), Techniques.Flash));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {

    }

    @Override
    public void hasAllPermissions() {
        //Valido si esta logeado  o no
//        if(!admin.obtener_preferencia(Constants.user).isEmpty()){
//            admin.callIntent(Menu.class, null);
//        }else{
//            admin.callIntent(LoginResponseWebService.class, null);
//        }
        admin.callIntent(Login.class, null);
    }
}
