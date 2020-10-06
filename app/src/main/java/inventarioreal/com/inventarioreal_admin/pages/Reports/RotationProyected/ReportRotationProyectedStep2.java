package inventarioreal.com.inventarioreal_admin.pages.Reports.RotationProyected;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Home;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Reports.HomeReportes;
import inventarioreal.com.inventarioreal_admin.pages.Reports.Returns.RequestREStep2;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ReportRotationProyectedStep2 extends CicloActivity {

    RequestRPStep2 requestRPStep2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_report_rotation_proyected_step_2);
        // toolbar
        getSupportActionBar().setTitle(R.string.reporte_rotacion_proyectada);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.lbl0),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lbl1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lbl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lbl3),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.img1),Techniques.FadeInLeft));

    }

    @Override
    public void getData() {
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        Gson gson = new Gson();
        this.requestRPStep2 = gson.fromJson(message, RequestRPStep2.class);
        mostrarInformacionProducto();

    }

    @Override
    public void initOnClick() {


    }

    private void mostrarInformacionProducto(){

        getElemento(R.id.lbl1).setText(this.requestRPStep2.getProduct().getEan());
        getElemento(R.id.lbl3).setText(this.requestRPStep2.getTotal()+"");
        admin.loadImageFromInternet(
                this.requestRPStep2.getProduct().getImagen(),
                (NetworkImageView)getElemento(R.id.img1).getElemento(),
                R.drawable.imagennoencontrada,
                R.drawable.imagennoencontrada);
    }

    @Override
    public void hasAllPermissions() {

    }



    @Override
    public void onBackPressed() {
        admin.callIntent(ReportRotationProyectedStep1.class, null);
    }

    //region Menu

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(getString(R.string.log_out));
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

    //endregion


}
