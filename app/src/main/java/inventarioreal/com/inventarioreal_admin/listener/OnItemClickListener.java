package inventarioreal.com.inventarioreal_admin.listener;

/**
 * Created by jorge.moreno on 16/02/2017.
 */

public interface OnItemClickListener {
    void onItemClick(Object item);
    void onLongItemClick(Object item);
    void onItemClick(int view, Object item);
}
