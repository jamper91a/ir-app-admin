package inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos.VisualizarPorZona.Step1;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.RecyclerAdapterInventarios;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Intents.RequestInventarioPorZonaStep2;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos.InventariosColaborativosHome;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos.VisualizarPorZona.Step2.VisualizarInventarioColaborativoPorZonaStep2;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class VisualizarInventarioColaborativoPorZonaStep1 extends CicloActivity {



    private RecyclerAdapterInventarios adapter;
    private ArrayList<Inventory> inventariosPorZonas = new ArrayList<>();
    RecyclerView recyclerView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this, R.layout.activity_visualizar_inventarios);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.lst1), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.btnIni), Techniques.SlideInLeft));

        //Cambiar los textos a mostrar

        getElemento(R.id.txt1).setText("Seleccione el inventory cooperativo a visualizar");
        getElemento(R.id.btnIni).setText("Visualizar");

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VisualizarInventarioColaborativoPorZonaStep1.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapterInventarios(VisualizarInventarioColaborativoPorZonaStep1.this, inventariosPorZonas, admin, new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                Inventory inv = (Inventory) item;
                admin.toast(inv.createdAt);
                RequestInventarioPorZonaStep2 requestInventarioPorZonaStep2 = new RequestInventarioPorZonaStep2(inv);
                admin.callIntent(VisualizarInventarioColaborativoPorZonaStep2.class, requestInventarioPorZonaStep2, RequestInventarioPorZonaStep2.class);
            }

            @Override
            public void onLongItemClick(Object item) {

            }

            @Override
            public void onItemClick(int view, Object item) {

            }
        });
        recyclerView.setAdapter(adapter);
        WebServices.listInventories(Constants.tipo_all,true,this, admin, new ResultWebServiceInterface() {
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
}
