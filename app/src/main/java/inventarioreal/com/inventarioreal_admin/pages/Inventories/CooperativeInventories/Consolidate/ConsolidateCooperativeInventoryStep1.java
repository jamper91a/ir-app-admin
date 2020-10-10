package inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.Consolidate;

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
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.InventariosColaborativosHome;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Administrador;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ConsolidateCooperativeInventoryStep1 extends CicloActivity {

    private static final String TAG = "ConsolidarInventario";
    private RecyclerAdapterInventariosAConsolidar adapter;
    private ArrayList<Inventory> inventariosPorConsolidar= new ArrayList<>();
    RecyclerView recyclerView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_consolidar_inventario_step1);
        // toolbar
        getSupportActionBar().setTitle(R.string.consolidar_inventario_cooperativo);
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
                    inventarioName += "/ " + getString(R.string.cooperativo);
                    if(inventarioName.length()>254)
                        inventarioName = inventarioName.substring(0,254);
                    WebServices.consolidateInventories(
                            invenntariosSeleccionadosId,
                            inventarioName,
                            ConsolidateCooperativeInventoryStep1.this,
                            admin,
                            new ResultWebServiceInterface() {
                                @Override
                                public void ok(ResultWebServiceOk ok) {
                                    admin.toast(R.string.inventarios_consolidados_exito);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ConsolidateCooperativeInventoryStep1.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapterInventariosAConsolidar(ConsolidateCooperativeInventoryStep1.this, inventariosPorConsolidar, admin);
        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        WebServices.listInventories(Constants.tipo_no_consolidado,true,this, admin, new ResultWebServiceInterface() {
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
        Administrador.callIntent(InventariosColaborativosHome.class, null);
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
                //DataBase db = DataBase.getInstance(this);
                //db.deleteAllData();
                admin.log_out(Login.class);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion
}
