package inventarioreal.com.inventarioreal_admin.pages;

import android.Manifest;
import android.os.Bundle;

import com.daimajia.androidanimations.library.Techniques;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.util.Constants;
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
        addElemento(new Animacion(findViewById(R.id.img1), Techniques.Flash));
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
        if(!admin.obtener_preferencia(Constants.token).isEmpty()){
            admin.callIntent(Home.class, null);
        }else{
            admin.callIntent(Login.class, null);
        }
    }
}
