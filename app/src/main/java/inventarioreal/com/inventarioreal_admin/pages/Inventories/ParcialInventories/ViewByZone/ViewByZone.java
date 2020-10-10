package inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.ViewByZone;

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
import inventarioreal.com.inventarioreal_admin.pages.Inventories.Intents.RequestInventoryZoneStep2;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.InventarioParcialHome;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.ViewInventory;
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

public class ViewByZone extends CicloActivity {



    private RecyclerAdapterInventarios adapter;
    private ArrayList<Inventory> inventariosPorZonas = new ArrayList<>();
    RecyclerView recyclerView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this, R.layout.activity_visualizar_inventarios);
        // toolbar
        getSupportActionBar().setTitle(R.string.visualizar_inventarios_parciales_por_zonas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.lst1), Techniques.SlideInLeft));

        //Cambiar los textos a mostrar

        getElemento(R.id.txt1).setText(getString(R.string.seleccione_inventario_a_visualizar));

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapterInventarios(this, inventariosPorZonas, admin, new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                Inventory inv = (Inventory) item;
                admin.toast(inv.getCreatedAt());
                RequestInventoryZoneStep2 requestInventarioPorZonaStep2 = new RequestInventoryZoneStep2(inv);
                Administrador.callIntent(ViewInventory.class, requestInventarioPorZonaStep2, RequestInventoryZoneStep2.class);
            }

            @Override
            public void onLongItemClick(Object item) {

            }

            @Override
            public void onItemClick(int view, Object item) {

            }
        });
        recyclerView.setAdapter(adapter);
        WebServices.listInventories(Constants.tipo_all,false,this, admin, new ResultWebServiceInterface() {
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
        Administrador.callIntent(InventarioParcialHome.class, null);
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