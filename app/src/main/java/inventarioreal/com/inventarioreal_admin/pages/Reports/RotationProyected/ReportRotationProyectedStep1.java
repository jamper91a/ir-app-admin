package inventarioreal.com.inventarioreal_admin.pages.Reports.RotationProyected;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterEpcs;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.listener.RFDIListener;
import inventarioreal.com.inventarioreal_admin.pages.Home;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Reports.HomeReportes;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.AddCommodityResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Product;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zone;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.RFDIReader;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ReportRotationProyectedStep1 extends CicloActivity {

    RequestRPStep2 request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_report_rotation_proyected_step_1);
        // toolbar
        getSupportActionBar().setTitle("Rotacion Proyectada");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.lbl0),Techniques.FadeInLeft));
//        addElemento(new Animacion(findViewById(R.id.num1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edt1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lbl1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEanPlu),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBus),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes3),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.img1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl1a),Techniques.FadeInLeft, null, false));
        addElemento(new Animacion(findViewById(R.id.lbl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSi),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnNo),Techniques.FadeInLeft));

    }

    @Override
    public void getData() {
        this.request = new RequestRPStep2();
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnBus, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServices.getProductByEanPlu(
                        getElemento(R.id.edtEanPlu).getText(),
                        ReportRotationProyectedStep1.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                Product productoConsultado = (Product) ok.getData();
                                mostrarInformacionProducto(productoConsultado);
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                if(fail.getError().equals("error_G06")){
                                    admin.toast("Codigo EAN/PLU no existe");
                                }
                                getElemento(R.id.lnl1a).getElemento().setVisibility(View.GONE);
                            }
                        });
            }
        });




        add_on_click(R.id.btnSi, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int days = Integer.parseInt(getElemento(R.id.edt1).getText());
                    request.setDays(days);
                    if(request.validar()){

                        getRotationProyected();
                    }
                } catch (NumberFormatException e) {
                    admin.toast(e.getMessage());
                } catch (Exception e) {
                    admin.toast(e.getMessage());
                }

            }
        });
        add_on_click(R.id.btnNo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.callIntent(HomeReportes.class, null);
            }
        });
    }

    private void mostrarInformacionProducto(Product p){
        this.request.setProduct(p);
        getElemento(R.id.lblDes1).setText(p.getDescription());
        getElemento(R.id.lblDes2).setText(p.getBranch());
        getElemento(R.id.lblDes3).setText(p.getColor());
        admin.loadImageFromInternet(
                p.getImagen(),
                (NetworkImageView)getElemento(R.id.img1).getElemento(),
                R.drawable.imagennoencontrada,
                R.drawable.imagennoencontrada);
        getElemento(R.id.lnl1a).getElemento().setVisibility(View.VISIBLE);
    }

    private void getRotationProyected(){
        WebServices.rotationProyected(
                request.getDays(),
                request.getProduct().getId() + "",
                this,
                admin,
                new ResultWebServiceInterface() {
                    @Override
                    public void ok(ResultWebServiceOk ok) {
                        ArrayList<ProductHasZone> products = (ArrayList<ProductHasZone>) ok.getData();
                        request.setTotal(products.size());
                        admin.callIntent(ReportRotationProyectedStep2.class, request, RequestRPStep2.class);
                    }

                    @Override
                    public void fail(ResultWebServiceFail fail) {
                        admin.toast(fail.getError());
                    }
                });
    }

    @Override
    public void hasAllPermissions() {

    }



    @Override
    public void onBackPressed() {
        admin.callIntent(HomeReportes.class, null);
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
                DataBase db = DataBase.getInstance(this);
                db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion


}
