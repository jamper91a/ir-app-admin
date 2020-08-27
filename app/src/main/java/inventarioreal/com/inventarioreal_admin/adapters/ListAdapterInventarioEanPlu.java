package inventarioreal.com.inventarioreal_admin.adapters;

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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import jamper91.com.easyway.Util.Administrador;


/**
 * Created by jorge.moreno on 16/02/2017.
 */

public class ListAdapterInventarioEanPlu extends RecyclerView.Adapter<ListAdapterInventarioEanPlu.ViewHolder> {

    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=1;
    private static final String TAG = "dapterInventarioEanPlu";
    private ArrayList<ProductHasZone> items;
    private ArrayList<ProductHasZone> todos;
    private OnItemClickListener onItemClickListener;

    public ListAdapterInventarioEanPlu(Activity activity, Administrador admin, ArrayList<ProductHasZone> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.todos = items;
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(ArrayList<ProductHasZone> items) {
        this.items = items;
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_list_item_inventarios_ean_plu, parent, false);
                break;
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_inventarios_ean_plu, parent, false);
                break;
        }
        ViewHolder viewHolder = new ViewHolder(view, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.viewType){
            case TYPE_HEADER:
//                holder.bind(item);
                break;
            case TYPE_ITEM:
                final ProductHasZone item = items.get(position-1);
                holder.getTxtId().setText(item.getId()+"");
                holder.getTxEpc().setText(item.getEpc().getEpc());
                holder.getTxtZona().setText(item.getZone().getName());
                holder.bind(item);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return items.size()+1;
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public void add(ProductHasZone item) {
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
                    items = (ArrayList<ProductHasZone>) results.values;
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
                    LinkedList<Epc> FilteredArrayNames = new LinkedList<>();
                    for (int i = 0; i < todos.size(); i++) {
                        ProductHasZone dataNames = todos.get(i);
//                        if (dataNames.getProduct().get.toLowerCase().contains(constraint))  {
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
        int viewType;


        public ViewHolder(View view, int viewType) {
            super(view);
            this.lnl1 = (LinearLayout)view.findViewById(R.id.lnl1);
            this.txtId = (TextView)view.findViewById(R.id.txtId);
            this.txEpc = (TextView)view.findViewById(R.id.txt2);
            this.txtZona = (TextView)view.findViewById(R.id.txtZona);

            this.viewType = viewType;




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

        public void bind(final ProductHasZone item) {
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
