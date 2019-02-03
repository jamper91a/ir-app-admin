package inventarioreal.com.inventarioreal_admin.pages;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.handheld.UHF.UhfManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.LinkedList;
import java.util.List;

import cn.pda.serialport.Tools;
import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapter1;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.Producto;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class IngresoMercancia extends CicloActivity {

    private SlidingMenu menu;
    private String TAG="IngresoMercancia";
    private UhfManager uhfManager;
    public ListAdapter1 adapter1;
    private LinkedList<Epc> epcs = new LinkedList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this,this,R.layout.activity_ingreso_mercancia);
        //start inventory thread
        Thread thread = new InventoryThread();
        thread.start();
//        this.menu =init_menu(this,R.layout.layout_menu);
    }
    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.lbl1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.edtEanPlu),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBus),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes3),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.img1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lbl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSi),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnNo),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lnl2),Techniques.FadeInLeft,null, false));
        addElemento(new Animacion(findViewById(R.id.lst1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnCan),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnEmp),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnGua),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnBor),Techniques.FadeInLeft));

        adapter1 = new ListAdapter1(this, admin, epcs, new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
            }

            @Override
            public void onLongItemClick(Object item) {

            }

            @Override
            public void onItemClick(int view, Object item) {

            }
        });
        RecyclerView lst1 = (RecyclerView)getElemento(R.id.lst1).getElemento();
        lst1.setLayoutManager(new LinearLayoutManager(this));
        lst1.setAdapter(adapter1);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btnBus, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServices.getProductByEanPlu(
                        getElemento(R.id.edtEanPlu).getText(),
                        IngresoMercancia.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                Producto producto = (Producto) ok.getData();
                                mostrarInformacionProducto(producto);
                                admin.loadImageFromInternet(
                                        "https://m.media-amazon.com/images/I/A13usaonutL._CLa%7C2140,2000%7C5118hwdonfL.png%7C0,0,2140,2000+0.0,0.0,2140.0,2000.0._UX522_.png",
                                        (NetworkImageView)getElemento(R.id.img1).getElemento(),
                                        R.drawable.ic_launcher_background,
                                        R.drawable.ic_launcher_background);


                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast(fail.getError());
                            }
                        });
            }
        });

        add_on_click(R.id.btnSi, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getElemento(R.id.lnl1).getElemento().setVisibility(View.GONE);
                getElemento(R.id.lnl2).getElemento().setVisibility(View.VISIBLE);
                if (!startFlag) {
                    startFlag = true;
                } else {
                    startFlag = false;
                }
            }
        });

        add_on_click(R.id.btnGua, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServices.addMercancia(
                        1,
                        1,
                        epcs,
                        IngresoMercancia.this,
                        admin,
                        new ResultWebServiceInterface() {
                            @Override
                            public void ok(ResultWebServiceOk ok) {
                                admin.toast("Ok");
                            }

                            @Override
                            public void fail(ResultWebServiceFail fail) {
                                admin.toast(fail.getError());
                            }
                        }
                );
            }
        });
    }

    private void mostrarInformacionProducto(Producto p){
        getElemento(R.id.lblDes1).setText(p.getDescripcion());
        getElemento(R.id.lblDes2).setText(p.getMarca());
        getElemento(R.id.lblDes3).setText(p.getColor());
    }

    @Override
    public void hasAllPermissions() {

    }

    //region UHD Sdk
    public void initSdk(){
        try {
            uhfManager = UhfManager.getInstance();
            uhfManager.setOutputPower(26);
            uhfManager.setWorkArea(2);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    private boolean runFlag=true;
    private boolean startFlag = false;

    @Override
    protected void onResume() {
        super.onResume();
        uhfManager = UhfManager.getInstance();
        if (uhfManager == null) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initSdk();
    }

    @Override
    protected void onPause() {
        startFlag = false;
        uhfManager.close();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        startFlag = false;
        if (uhfManager != null) {
            uhfManager.close();
        }
        super.onDestroy();
    }

    private Epc createEpc(String epc){
        Epc epcTag = new Epc();
        epcTag.setEpc(epc);
        epcTag.setCount(1);
        epcs.add(epcTag);
        adapter1.add(epcTag);
        return epcTag;
    }

    private void addToList(final String epc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // The epc for the first time
                if (epcs.isEmpty()) {
                    createEpc(epc);
                } else {
                    for (int i = 0; i < epcs.size(); i++) {
                        Epc mEPC = epcs.get(i);
                        // list contain this epc
                        if (epc.equals(mEPC.getEpc())) {
                            mEPC.setCount(mEPC.getCount() + 1);
                            break;
                        } else if (i == (epcs.size() - 1)) {
                            Epc epcObj = createEpc(epc);
                        }
                    }

                }
            }
        });
    }

    /**
     * Inventory Epc Thread
     */

    class InventoryThread extends Thread {
        private List<byte[]> epcList;

        @Override
        public void run() {
            super.run();
            while (runFlag) {
                if (startFlag) {
                    // manager.stopInventoryMulti()
                    epcList = uhfManager.inventoryRealTime(); // inventory real time
                    if (epcList != null && !epcList.isEmpty()) {
                        for (byte[] epc : epcList) {
                            String epcStr = Tools.Bytes2HexString(epc,
                                    epc.length);
                            addToList(epcStr);
                        }
                    }
                    epcList = null;
                    try {
                        Thread.sleep( 40);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //endregion
}
