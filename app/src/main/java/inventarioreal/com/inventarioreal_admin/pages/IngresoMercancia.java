package inventarioreal.com.inventarioreal_admin.pages;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SimpleAdapter;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.handheld.UHF.UhfManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pda.serialport.Tools;
import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.EPC;
import inventarioreal.com.inventarioreal_admin.pojo.Producto;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServiceResult.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class IngresoMercancia extends CicloActivity {

    private SlidingMenu menu;
    private String TAG="IngresoMercancia";
    private UhfManager uhfManager;
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
        addElemento(new Animacion(findViewById(R.id.lblDes1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lblDes3),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.img1),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.lbl2),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnSi),Techniques.FadeInLeft));
        addElemento(new Animacion(findViewById(R.id.btnNo),Techniques.FadeInLeft));
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
                initSdk();
                if (!startFlag) {
                    startFlag = true;
                } else {
                    startFlag = false;
                }
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
    private ArrayList<EPC> listEPC = new ArrayList<EPC>();
    private ArrayList<String> listepc = new ArrayList<String>();

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

    private void addToList(final List<EPC> list, final String epc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // The epc for the first time
                if (list.isEmpty()) {
                    EPC epcTag = new EPC();
                    epcTag.setEpc(epc);
                    epcTag.setCount(1);
                    list.add(epcTag);
                    listepc.add(epc);
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        EPC mEPC = list.get(i);
                        // list contain this epc
                        if (epc.equals(mEPC.getEpc())) {
                            mEPC.setCount(mEPC.getCount() + 1);
                            list.set(i, mEPC);
                            Log.d(TAG, mEPC.getEpc());
                            break;
                        } else if (i == (list.size() - 1)) {
                            // list doesn't contain this epc
                            EPC newEPC = new EPC();
                            newEPC.setEpc(epc);
                            newEPC.setCount(1);
                            list.add(newEPC);
                            listepc.add(epc);
                            Log.d(TAG, mEPC.getEpc());
                        }
                    }
                }
            }
        });
    }

    /**
     * Inventory EPC Thread
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
                            addToList(listEPC, epcStr);
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

}
