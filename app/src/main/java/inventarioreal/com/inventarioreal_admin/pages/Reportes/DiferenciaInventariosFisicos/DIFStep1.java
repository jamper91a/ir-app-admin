package inventarioreal.com.inventarioreal_admin.pages.Reportes.DiferenciaInventariosFisicos;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.RecyclerAdapterInventariosConsolidados;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.InventarioParcialHome;
import inventarioreal.com.inventarioreal_admin.pages.Reportes.HomeReportes;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class DIFStep1 extends CicloActivity {
    private RecyclerAdapterInventariosConsolidados adapter;
    private ArrayList<ConsolidatedInventory> inventariosConsolidados = new ArrayList<>();
    RecyclerView recyclerView = null;
    private ConsolidatedInventory inventarioInicial=null;
    private ConsolidatedInventory inventarioFinal=null;
    //Var to know in which step the use is
    private int step=1;


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
        getElemento(R.id.txt1).setText("Selecciones el inventory Inicial");
        getElemento(R.id.btnIni).setText("Siguiente");
    }

    @Override
    public void getData() {
        getInventariosConsolidados();
    }

    @Override
    public void initOnClick() {

    }

    @Override
    public void hasAllPermissions() {

    }

    private void getInventariosConsolidados() {
        recyclerView = (RecyclerView) getElemento(R.id.lst1).getElemento();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapterInventariosConsolidados(this, inventariosConsolidados, admin, new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                ConsolidatedInventory inv = (ConsolidatedInventory) item;
                if(step==2){
//                    inv.setId(2);
                    inventarioFinal=inv;
                    RequestDIFStep2 requestDIFStep2 = new RequestDIFStep2(inventarioInicial, inventarioFinal);
                    try {
                        requestDIFStep2.validar();
                            admin.callIntent(DIFStep2.class, requestDIFStep2, RequestDIFStep2.class);
                    } catch (Error e) {
                        admin.toast(e.getMessage());
                    }
                }
                if(step==1){
                    inventarioInicial=inv;
                    getElemento(R.id.txt1).setText("Selecciones el inventory final");
                    step=2;
                }

            }

            @Override
            public void onLongItemClick(Object item) {

            }

            @Override
            public void onItemClick(int view, Object item) {

            }
        });
        recyclerView.setAdapter(adapter);
        WebServices.listAllConsolidatedInventories(this, admin, new ResultWebServiceInterface() {
            @Override
            public void ok(ResultWebServiceOk ok) {
                inventariosConsolidados = (ArrayList<ConsolidatedInventory>) ok.getData();
                adapter.setInventarios(inventariosConsolidados);
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
        admin.callIntent(HomeReportes.class, null);
    }


}
