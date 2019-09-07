package inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.Consolidate;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.RecyclerAdapterInventariosAConsolidar;
import inventarioreal.com.inventarioreal_admin.pages.Inventories.CooperativeInventories.InventariosColaborativosHome;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ConsolidarInventariosColaborativosStep1 extends CicloActivity {

    private static final String TAG = "ConsolidarInventario";
    private RecyclerAdapterInventariosAConsolidar adapter;
    private ArrayList<Inventory> inventariosPorConsolidar= new ArrayList<>();
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
                    inventarioName += "/ Colaborativo";
                    if(inventarioName.length()>254)
                        inventarioName = inventarioName.substring(0,254);
                    WebServices.consolidateInventories(
                            invenntariosSeleccionadosId,
                            inventarioName,
                            ConsolidarInventariosColaborativosStep1.this,
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ConsolidarInventariosColaborativosStep1.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapterInventariosAConsolidar(ConsolidarInventariosColaborativosStep1.this, inventariosPorConsolidar, admin);
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
        admin.callIntent(InventariosColaborativosHome.class, null);
    }
}
