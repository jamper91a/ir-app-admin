package inventarioreal.com.inventarioreal_admin.pages.Reportes.InventarioEanPlu;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epcs;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductosZonas;
import jamper91.com.easyway.Util.Administrador;


/**
 * Created by jorge.moreno on 16/02/2017.
 */

public class ListAdapterInventarioEanPlu extends RecyclerView.Adapter<ListAdapterInventarioEanPlu.ViewHolder> {

    private static final String TAG = "dapterInventarioEanPlu";
    private Activity activity;
    private Administrador admin;
    private ArrayList<ProductosZonas> items;
    private ArrayList<ProductosZonas> todos;
    private OnItemClickListener onItemClickListener;

    public ListAdapterInventarioEanPlu(Activity activity, Administrador admin, ArrayList<ProductosZonas> items, OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.admin = admin;
        this.items = items;
        this.todos = items;
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(ArrayList<ProductosZonas> items) {
        this.items = items;
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_inventarios_ean_plu, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductosZonas item = items.get(position);
        holder.getTxtId().setText(item.getId()+"");
        holder.getTxEpc().setText(item.getEpcs_id().getEpc());
        holder.getTxtZona().setText(item.getZonas_id().getName());
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


    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if(results.count ==0)
                {
                    notifyDataSetChanged();
                }else{
                    items = (ArrayList<ProductosZonas>) results.values;
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
                    LinkedList<Epcs> FilteredArrayNames = new LinkedList<>();
                    for (int i = 0; i < todos.size(); i++) {
                        ProductosZonas dataNames = todos.get(i);
//                        if (dataNames.getProductos_id().get.toLowerCase().contains(constraint))  {
//                            FilteredArrayNames.add(dataNames);
//                        }
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
        LinearLayout lnl1;
        private TextView txtId;
        private TextView txEpc;
        private TextView txtZona;


        public ViewHolder(View view) {
            super(view);
            this.lnl1 = (LinearLayout)view.findViewById(R.id.lnl1);
            this.txtId = (TextView)view.findViewById(R.id.txtId);
            this.txEpc = (TextView)view.findViewById(R.id.txtEpc);
            this.txtZona = (TextView)view.findViewById(R.id.txtZona);

        }

        public LinearLayout getLnl1() {
            return lnl1;
        }

        public TextView getTxtId() {
            return txtId;
        }

        public void setTxtId(TextView txtId) {
            this.txtId = txtId;
        }

        public TextView getTxEpc() {
            return txEpc;
        }

        public void setTxEpc(TextView txEpc) {
            this.txEpc = txEpc;
        }

        public TextView getTxtZona() {
            return txtZona;
        }

        public void setTxtZona(TextView txtZona) {
            this.txtZona = txtZona;
        }

        public void bind(final ProductosZonas item) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongItemClick(item);
                    return true;
                }
            });
        }

    }
}
