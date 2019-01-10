package inventarioreal.com.inventarioreal_admin;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;

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

    }

    @Override
    public void hasAllPermissions() {

    }
}
