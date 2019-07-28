package inventarioreal.com.inventarioreal_admin.pages;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.RFDIListener;
import inventarioreal.com.inventarioreal_admin.util.RFDIReader;
import jamper91.com.easyway.Util.Animacion;
import jamper91.com.easyway.Util.CicloActivity;

public class ReaderActivity extends CicloActivity {

    RFDIReader rfdiReader =  null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String epc = msg.getData().getString("epc");
                    admin.toast(epc);
                    break ;
            }
        }
    } ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rfdiReader = new RFDIReader(new RFDIListener() {
            @Override
            public void onEpcAdded(String epc) {
                Message msg = new Message();
                msg.what = 1;
                Bundle b = new Bundle();
                b.putString("epc", epc);
                msg.setData(b);
                handler.sendMessage(msg);
            }

            @Override
            public void onEpcRepeated(String epc) {
                Message msg = new Message();
                msg.what = 1;
                Bundle b = new Bundle();
                b.putString("epc", "onEpcRepeated:"+epc);
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        rfdiReader.initSDK();
        init(this,this,R.layout.activity_reader);

    }

    @Override
    public void initGui() {
        addElemento(new Animacion(findViewById(R.id.btn1), Techniques.FadeInLeft));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initOnClick() {
        add_on_click(R.id.btn1, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rfdiReader.startReader();
            }
        });
    }

    @Override
    public void hasAllPermissions() {

    }

    @Override
    protected void onResume() {
        rfdiReader.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        rfdiReader.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        rfdiReader.onDestroy();
        super.onDestroy();
    }
}
