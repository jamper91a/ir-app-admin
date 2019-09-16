package inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.Join;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.RecyclerAdapterInventarios;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.Intents.RequestCreateInventory2;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.Create.Step2.CrearInventarioColaborativoStep2;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.InventariosColaborativosHome;
import inventarioreal.com.inventarioreal_admin.pages.Login;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class UnirseInventariosColaborativos extends CicloActivity {

    private RecyclerAdapterInventarios adapter;
    private ArrayList<Inventory> inventariosPorZonas = new ArrayList<>();
    RecyclerView recyclerView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_inventarios_colaborativos_universe);
        // toolbar
        getSupportActionBar().setTitle("Universe Inv Cooperativos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.lst1), Techniques.SlideInLeft));

        //Cambiar los textos a mostrar

        getElemento(R.id.txt1).setText("Selecciones el inventory a unirse");
    }

    @Override
    public void getData() {
        getinventariosPorConsolidar();
    }

    @Override
    public void initOnClick() {

    }

    @Override
    public void hasAllPermissions() {

    }

    private void getinventariosPorConsolidar() {
        recyclerView = (RecyclerView) getElemento(R.id.lst1).getElemento();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UnirseInventariosColaborativos.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapterInventarios(UnirseInventariosColaborativos.this, inventariosPorZonas, admin, new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                Inventory inv = (Inventory) item;
                //Busco la zonaz
                RequestCreateInventory2 requestInventariorCrear2 = new RequestCreateInventory2(inv);
                requestInventariorCrear2.setUnion(true);
                admin.callIntent(CrearInventarioColaborativoStep2.class, requestInventariorCrear2, RequestCreateInventory2.class);
            }

            @Override
            public void onLongItemClick(Object item) {

            }

            @Override
            public void onItemClick(int view, Object item) {

            }
        });
        recyclerView.setAdapter(adapter);
        WebServices.listInventories(Constants.tipo_no_consolidado,true,this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                inventariosPorZonas = (ArrayList<Inventory>) ok.getData();
                adapter.setInventarios(inventariosPorZonas);
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
        admin.callIntent(InventariosColaborativosHome.class, null);
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
