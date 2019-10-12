package inventarioreal.com.inventarioreal_admin.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.DiferenceInventoryErp;
import jamper91.com.easyway.Util.Administrador;


/**
 * Created by jorge.moreno on 16/02/2017.
 */

public class ListAdapterDiferenceInventoryErpVisual extends RecyclerView.Adapter<ListAdapterDiferenceInventoryErpVisual.ViewHolder> {


    private static final String TAG = "ListAdapterDifInvErpVis";
    private Activity activity;
    private Administrador admin;
    private LinkedList<DiferenceInventoryErp> items;
    private LinkedList<DiferenceInventoryErp> todos;
    private OnItemClickListener onItemClickListener;

    public ListAdapterDiferenceInventoryErpVisual(Activity activity, Administrador admin, LinkedList<DiferenceInventoryErp> items, OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.admin = admin;
        this.items = items;
        this.todos = items;
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(LinkedList<DiferenceInventoryErp> items) {
        this.items = items;
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_diference_inventory_erp_visual, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DiferenceInventoryErp item = items.get(position);
        holder.getTxt5().setText(item.getEan());
        holder.getTxt4().setText((item.getTotal()-item.getErp())+"");
        holder.getTxt3().setText(item.getErp()+"");
        holder.getTxt2().setText(item.getTotal()+"");
        holder.getTxt1().setText(item.getSize());
        if(item.getImagen()==null)
            item.setImagen("");
        admin.loadImageFromInternet(item.getImagen(), holder.getImgProduct(), R.drawable.lost, R.drawable.inventory);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(DiferenceInventoryErp item) {
        try {
            int position = items.indexOf(item);
            notifyItemInserted(position);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void remove(DiferenceInventoryErp item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void update(DiferenceInventoryErp item, int position){
        items.set(position, item);
        notifyItemChanged(position);
    }



    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        TextView txt5;
        NetworkImageView imgProduct;


        public ViewHolder(View view) {
            super(view);
                this.txt1 = (TextView)view.findViewById(R.id.txt1);
                this.txt2 = (TextView)view.findViewById(R.id.txt2);
                this.txt3 = (TextView)view.findViewById(R.id.txt3);
                this.txt4 = (TextView)view.findViewById(R.id.txt4);
                this.txt5 = (TextView)view.findViewById(R.id.txt5);
                this.imgProduct = (NetworkImageView) view.findViewById(R.id.imgProduct);


        }

        public TextView getTxt1() {
            return txt1;
        }

        public void setTxt1(TextView txtTotal) {
            this.txt1 = txtTotal;
        }

        public TextView getTxt2() {
            return txt2;
        }

        public void setTxt2(TextView txtSize) {
            this.txt2 = txtSize;
        }

        public TextView getTxt3() {
            return txt3;
        }

        public void setTxt3(TextView txt3) {
            this.txt3 = txt3;
        }

        public TextView getTxt4() {
            return txt4;
        }

        public void setTxt4(TextView txt4) {
            this.txt4 = txt4;
        }

        public TextView getTxt5() {
            return txt5;
        }

        public void setTxt5(TextView txt5) {
            this.txt5 = txt5;
        }

        public NetworkImageView getImgProduct() {
            return imgProduct;
        }

        public void setImgProduct(NetworkImageView imgProduct) {
            this.imgProduct = imgProduct;
        }
        public void bind(final DiferenceInventoryErp item) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }
    }
}
