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
import jamper91.com.easyway.Util.Administrador;


/**
 * Created by jorge.moreno on 16/02/2017.
 */

public class ListAdapterProductosZonasVisual extends RecyclerView.Adapter<ListAdapterProductosZonasVisual.ViewHolder> {


    private static final String TAG = "ListAdapterEpcs";
    private Activity activity;
    private Administrador admin;
    private LinkedList<ProductHasZone> items;
    private LinkedList<ProductHasZone> todos;
    private OnItemClickListener onItemClickListener;

    public ListAdapterProductosZonasVisual(Activity activity, Administrador admin, LinkedList<ProductHasZone> items, OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.admin = admin;
        this.items = items;
        this.todos = items;
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(LinkedList<ProductHasZone> items) {
        this.items = items;
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(items.get(position).getEpc().getEpc());
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
        final ProductHasZone item = items.get(position);
        holder.getTxtSize().setText(item.getProduct().getSize());
        holder.getTxtTotal().setText(item.getTotal()+"");
        if(item.getProduct().getImagen()==null)
            item.getProduct().setImagen("");
        admin.loadImageFromInternet(item.getProduct().getImagen(), holder.getImgProduct(), R.drawable.lost, R.drawable.inventory);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(ProductHasZone item) {
        try {
            int position = items.indexOf(item);
            notifyItemInserted(position);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void remove(ProductHasZone item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void update(ProductHasZone item, int position){
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
                    items = (LinkedList<ProductHasZone>) results.values;
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
                    LinkedList<ProductHasZone> FilteredArrayNames = new LinkedList<>();
                    for (int i = 0; i < todos.size(); i++) {
                        ProductHasZone dataNames = todos.get(i);
                        if (dataNames.getEpc().getEpc().toLowerCase().contains(constraint))  {
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
        public void bind(final ProductHasZone item) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }
    }
}
