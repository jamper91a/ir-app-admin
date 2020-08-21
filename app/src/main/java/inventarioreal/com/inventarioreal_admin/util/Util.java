package inventarioreal.com.inventarioreal_admin.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Header;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.OnAcceptCancelListener;
import inventarioreal.com.inventarioreal_admin.pages.Cashiers.HomeCashier;
import inventarioreal.com.inventarioreal_admin.pages.Cashiers.Sell.SellCommodity;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;


public class Util {


    public static SoundPool sp ;
    public static Map<Integer, Integer> suondMap;
    public static Context context;

    //
    public static void initSoundPool(Context context){
        Util.context = context;
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        suondMap = new HashMap<Integer, Integer>();
        suondMap.put(1, sp.load(context, R.raw.beep, 1));
    }

    //
    public static  void play(int sound, int number){
        AudioManager am = (AudioManager)Util.context.getSystemService(Util.context.AUDIO_SERVICE);
        //
        float audioMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //
        float audioCurrentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolume/audioMaxVolume;
        sp.play(
                suondMap.get(sound), //
                audioCurrentVolume, //
                audioCurrentVolume, //
                1, //
                number, //
                1);//
    }
    public static void pasue() {
        sp.pause(0);
    }

    public static void askForEmail(final OnAcceptCancelListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.sacar_producto));

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogView = inflater.inflate(R.layout.dialog_send_report, null);
        final EditText edtEmail = dialogView.findViewById(R.id.edtEmail);
        builder.setView(dialogView);
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onAccept(edtEmail.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                listener.onCancel(null);
            }
        });

        builder.show();
    }

    public static HashMap<String, String>  findErrorInWebService(List<Header> headers){
        HashMap<String, String> exit = new HashMap<>();
        for (Header header : headers) {
            if (header.getName().equals("x-exit")) {
                exit.put("exit", header.getValue());
            }
            if (header.getName().equals("x-exit-description")) {
                exit.put("description", header.getValue());
            }
        }
        return exit;
    }
}
