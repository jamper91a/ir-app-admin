package inventarioreal.com.inventarioreal_admin.pages.Inventario.InventarioParcial.CrearInventario.ConsolidarInventario;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.RecyclerAdapterInventarios;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Zonas;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ConsolidarInventarioStep1 extends CicloActivity {

    private RecyclerAdapterInventarios adapter;
    private ArrayList<Inventarios> inventariosPorConsolidar;
    RecyclerView recyclerView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_consolidar_inventario_step1);
    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.txt1), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.lst1), Techniques.SlideInLeft));
        addElemento(new Animacion(findViewById(R.id.button2), Techniques.SlideInLeft));

    }

    @Override
    public void getData() {
        getInventarios();
    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.button2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    adapter.getInventariosSeleccionados();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

    private void getInventarios() {
        recyclerView = (RecyclerView) getElemento(R.id.lst1).getElemento();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ConsolidarInventarioStep1.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        inventariosPorConsolidar = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Inventarios aux = new Inventarios();
            Zonas aux2= new Zonas();
            aux2.setName("Zotano "+i);
            aux.setZonas_id(aux2);
            inventariosPorConsolidar.add(aux);
        }
        adapter = new RecyclerAdapterInventarios(ConsolidarInventarioStep1.this, inventariosPorConsolidar);
        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        WebServices.listarInventarioParcialesNoConsolidados(this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                inventariosPorConsolidar = (ArrayList<Inventarios>) ok.getData();
                adapter.setInventarios(inventariosPorConsolidar);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void fail(ResultWebServiceFail fail) {

            }
        });


    }

}
