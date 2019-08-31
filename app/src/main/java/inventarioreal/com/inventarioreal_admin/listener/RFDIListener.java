package inventarioreal.com.inventarioreal_admin.listener;

/**
 * Created by jorge.moreno on 16/02/2017.
 */

public interface RFDIListener {
    void onEpcAdded(String epc);
    void onEpcRepeated(String epc);
    void onStateChanged(boolean state);
    void onKeyPresses(String key);
}
