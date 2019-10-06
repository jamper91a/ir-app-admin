package inventarioreal.com.inventarioreal_admin.pages.Reports.Returns;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pages.Reports.HomeReportes;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.DataBase;
import inventarioreal.com.inventarioreal_admin.util.DatePickerFragment;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class REStep1 extends CicloActivity {
    //Var to determinate type of devolution, 1 clients, 2 suppliers
    private int type =1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.parameters);
        this.type = Integer.parseInt(message);
        init(this,this, R.layout.activity_report_sell_units_step_1);
        // toolbar
        if(this.type == 1)
            getSupportActionBar().setTitle("Reporte Devolucion Clientes");
        else
            getSupportActionBar().setTitle("Reporte Devolucion Proveedores");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.edt1), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.txt2), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.edt2), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.btn1), Techniques.SlideInLeft));

    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.edt1, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });
        add_on_click(R.id.edt2, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

        add_on_click(R.id.btn1, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestREStep2 request = new RequestREStep2();
                request.setFirstDate(getElemento(R.id.edt1).getText());
                request.setSecondDate(getElemento(R.id.edt2).getText());
                request.setType(type);
                try {
                    if(request.validar()){
                        admin.callIntent(REStep2.class, request, RequestREStep2.class);
                    }
                } catch (Error error) {
                    admin.toast(error.getMessage());
                }

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

    //endregion

    private void showDatePickerDialog(final View view) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + "-" + twoDigits(month+1) + "-" + twoDigits(day);
                ((EditText)view).setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }


}
