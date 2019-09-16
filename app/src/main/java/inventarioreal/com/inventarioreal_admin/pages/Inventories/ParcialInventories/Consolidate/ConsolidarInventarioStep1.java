package inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Consolidate;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.RecyclerAdapterInventariosAConsolidar;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.InventarioParcialHome;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ConsolidarInventarioStep1 extends CicloActivity {

    private static final String TAG = "ConsolidarInventario";
    private RecyclerAdapterInventariosAConsolidar adapter;
    private ArrayList<Inventory> inventariosPorConsolidar= new ArrayList<>();
    RecyclerView recyclerView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_consolidar_inventario_step1);
        // toolbar
        getSupportActionBar().setTitle("Consolidar Inventarios");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.lst1), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.btnIni), Techniques.SlideInLeft));
    }

    @Override
    public void getData() {
        getinventariosPorConsolidar();
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnIni, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<Integer> invenntariosSeleccionados = adapter.getInventariosSeleccionados();
                    ArrayList<Long> invenntariosSeleccionadosId = new ArrayList<>();
                    String inventarioName = "";
                    for (Integer i: invenntariosSeleccionados
                         ) {
                        inventarioName+=" / "+ inventariosPorConsolidar.get(i.intValue()).getZone().getName();
                        invenntariosSeleccionadosId.add(inventariosPorConsolidar.get(i).getId());
                    }
                    WebServices.consolidateInventories(
                            invenntariosSeleccionadosId,
                            inventarioName,
                            ConsolidarInventarioStep1.this,
                            admin,
                            new ResultWebServiceInterface() {
                                @Override
                                public void ok(ResultWebServiceOk ok) {
                                    admin.toast("Inventarios consolidados con exito");
                                }

                                @Override
                                public void fail(ResultWebServiceFail fail) {

                                }
                            });
                } catch (Exception e) {
                    admin.toast(e.getMessage());
                }

            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

    private void getinventariosPorConsolidar() {
        recyclerView = (RecyclerView) getElemento(R.id.lst1).getElemento();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ConsolidarInventarioStep1.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapterInventariosAConsolidar(ConsolidarInventarioStep1.this, inventariosPorConsolidar, admin);
        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        WebServices.listInventories(Constants.tipo_no_consolidado,false,this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                inventariosPorConsolidar = (ArrayList<Inventory>) ok.getData();
                adapter.setInventarios(inventariosPorConsolidar);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void fail(ResultWebServiceFail fail) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        admin.callIntent(InventarioParcialHome.class, null);
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
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
