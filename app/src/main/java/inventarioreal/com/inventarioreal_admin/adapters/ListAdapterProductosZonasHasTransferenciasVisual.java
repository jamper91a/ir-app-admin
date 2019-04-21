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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonasHasTransferencias;
import jamper91.com.easyway.Util.Administrador;


/**
 * Created by jorge.moreno on 16/02/2017.
 */

public class ListAdapterProductosZonasHasTransferenciasVisual extends RecyclerView.Adapter<ListAdapterProductosZonasHasTransferenciasVisual.ViewHolder> {


    private static final String TAG = "ListAdapterEpcs";
    private Activity activity;
    private Administrador admin;
    private LinkedList<ProductosZonasHasTransferencias> items;
    private LinkedList<ProductosZonasHasTransferencias> todos;
    private OnItemClickListener onItemClickListener;

    public ListAdapterProductosZonasHasTransferenciasVisual(
            Activity activity,
            Administrador admin,
            LinkedList<ProductosZonasHasTransferencias> items,
            OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.admin = admin;
        this.items = items;
        this.todos = items;
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(LinkedList<ProductosZonasHasTransferencias> items) {
        this.items = items;
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(items.get(position).getProductos_zona_id().getEpcs_id().getEpc());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_productos_zona_visual, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductosZonasHasTransferencias item = items.get(position);
        holder.getTxtSize().setText(item.getProductos_zona_id().getProductos_id().getTalla());
        holder.getTxtTotal().setText(item.getProductos_zona_id().getTotal()+"");
        if(item.getProductos_zona_id().getProductos_id().getImagen()==null)
            item.getProductos_zona_id().getProductos_id().setImagen("");
        admin.loadImageFromInternet(item.getProductos_zona_id().getProductos_id().getImagen(), holder.getImgProduct(), R.drawable.lost, R.drawable.inventory);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(ProductosZonas item) {
        try {
            int position = items.indexOf(item);
            notifyItemInserted(position);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void remove(ProductosZonas item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void update(ProductosZonasHasTransferencias item, int position){
        items.set(position, item);
        notifyItemChanged(position);
    }

    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if(results.count ==0)
                {
                    notifyDataSetChanged();
                }else{
                    items = (LinkedList<ProductosZonasHasTransferencias>) results.values;
                    notifyDataSetChanged();
                }

            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();


                constraint = constraint.toString().toLowerCase();
                if(constraint==null && constraint.length() == 0)
                {
                    results.values = todos;
                    results.count = todos.size();
                }else{
                    LinkedList<ProductosZonasHasTransferencias> FilteredArrayNames = new LinkedList<>();
                    for (int i = 0; i < todos.size(); i++) {
                        ProductosZonasHasTransferencias dataNames = todos.get(i);
                        if (dataNames.getProductos_zona_id().getEpcs_id().getEpc().toLowerCase().contains(constraint))  {
                            FilteredArrayNames.add(dataNames);
                        }
                    }
                    results.count = FilteredArrayNames.size();
                    results.values = FilteredArrayNames;
                }
                // perform your search here using the searchConstraint String.






                return results;
            }
        };

        return filter;
    }


    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txtTotal;
        TextView txtSize;
        NetworkImageView imgProduct;


        public ViewHolder(View view) {
            super(view);
                this.txtTotal = (TextView)view.findViewById(R.id.txtTotal);
                this.txtSize = (TextView)view.findViewById(R.id.txtSize);
                this.imgProduct = (NetworkImageView) view.findViewById(R.id.imgProduct);


        }

        public TextView getTxtTotal() {
            return txtTotal;
        }

        public void setTxtTotal(TextView txtTotal) {
            this.txtTotal = txtTotal;
        }

        public TextView getTxtSize() {
            return txtSize;
        }

        public void setTxtSize(TextView txtSize) {
            this.txtSize = txtSize;
        }

        public NetworkImageView getImgProduct() {
            return imgProduct;
        }

        public void setImgProduct(NetworkImageView imgProduct) {
            this.imgProduct = imgProduct;
        }
        public void bind(final ProductosZonasHasTransferencias item) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }
    }
}
