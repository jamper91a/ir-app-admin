package inventarioreal.com.inventarioreal_admin;

import android.Manifest;
import android.os.Bundle;

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

    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {

    }

    @Override
    public void hasAllPermissions() {

    }
}
