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

import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;
import jamper91.com.easyway.Util.Administrador;


/**
 * Created by jorge.moreno on 16/02/2017.
 */

public class ListAdapterProductosZonasHasTransferencia extends RecyclerView.Adapter<ListAdapterProductosZonasHasTransferencia.ViewHolder> {

    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=1;

    private static final String TAG = "ListAdapterEpcs";
    private Activity activity;
    private Administrador admin;
    private LinkedList<TransfersHasZonesProduct> items;
    private LinkedList<TransfersHasZonesProduct> todos;
    private OnItemClickListener onItemClickListener;

    public ListAdapterProductosZonasHasTransferencia(Activity activity, Administrador admin, LinkedList<TransfersHasZonesProduct> items, OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.admin = admin;
        this.items = items;
        this.todos = items;
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(LinkedList<TransfersHasZonesProduct> items) {
        this.items = items;
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(items.get(position).getProduct().getEpc().getEpc());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_list_productos_zonas_has_transferencias, parent, false);
                break;
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_productos_zonas_has_transferencias, parent, false);
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
                final TransfersHasZonesProduct item = items.get(position-1);
                Log.d(TAG, item.getState()+"");
                holder.getTxt1().setText(item.getProduct().getTotal()+"");
                holder.getTxt2().setText(item.getProduct().getEpc().getEpc());
                holder.getTxt3().setText(item.getProduct().getProduct().getDescription());
                holder.getTxt4().setText(item.getTransfer().getManifest());
                holder.getTxt5().setText(item.getTransfer().getShopDestination().getName());
                holder.getTxt6().setText(item.getState()? "C" : "E");
                holder.bind(item);
                break;
        }



    }

    @Override
    public int getItemCount() {
        return items.size()+1;
    }

    public void add(TransfersHasZonesProduct item) {
        try {
            int position = items.indexOf(item);
            notifyItemInserted(position);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void remove(TransfersHasZonesProduct item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void update(TransfersHasZonesProduct item, int position){
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
                    items = (LinkedList<TransfersHasZonesProduct>) results.values;
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
                    LinkedList<TransfersHasZonesProduct> FilteredArrayNames = new LinkedList<>();
                    for (int i = 0; i < todos.size(); i++) {
                        TransfersHasZonesProduct dataNames = todos.get(i);
                        if (dataNames.getProduct().getEpc().getEpc().toLowerCase().contains(constraint))  {
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

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        LinearLayout lnl1;
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        TextView txt5;
        TextView txt6;
        int viewType;

        //region Header items
        TextView txtTotal, txtEanPlu, txtDetails, txtME, txtLocal, txtEstado;

        //endregion


        public ViewHolder(View view, int viewType) {
            super(view);
            if(viewType==TYPE_ITEM)
            {
                this.lnl1 = (LinearLayout)view.findViewById(R.id.lnl1);
                this.txt1 = (TextView)view.findViewById(R.id.txt1);
                this.txt2 = (TextView)view.findViewById(R.id.txt2);
                this.txt3 = (TextView)view.findViewById(R.id.txt3);
                this.txt4 = (TextView)view.findViewById(R.id.txt4);
                this.txt5 = (TextView)view.findViewById(R.id.txt5);
                this.txt6 = (TextView)view.findViewById(R.id.txt6);
            }
            else{
                this.txtTotal = (TextView)view.findViewById(R.id.txtTotal);
                this.txtEanPlu = (TextView)view.findViewById(R.id.txtEanPlu);
                this.txtDetails = (TextView)view.findViewById(R.id.txtDetails);
                this.txtME= (TextView)view.findViewById(R.id.txtME);
                this.txtLocal= (TextView)view.findViewById(R.id.txtLocal);
                this.txtEstado= (TextView)view.findViewById(R.id.txtEstado);

            }
            this.viewType = viewType;

        }

        public LinearLayout getLnl1() {
            return lnl1;
        }

        public TextView getTxt1() {
            return txt1;
        }

        public void setTxt1(TextView txt1) {
            this.txt1 = txt1;
        }

        public TextView getTxt2() {
            return txt2;
        }

        public void setTxt2(TextView txt2) {
            this.txt2 = txt2;
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

        public TextView getTxt6() {
            return txt6;
        }

        public void setTxt6(TextView txt6) {
            this.txt6 = txt6;
        }

        public TextView getTxtTotal() {
            return txtTotal;
        }

        public void setTxtTotal(TextView txtTotal) {
            this.txtTotal = txtTotal;
        }

        public TextView getTxtEanPlu() {
            return txtEanPlu;
        }

        public void setTxtEanPlu(TextView txtEanPlu) {
            this.txtEanPlu = txtEanPlu;
        }

        public TextView getTxtDetails() {
            return txtDetails;
        }

        public void setTxtDetails(TextView txtDetails) {
            this.txtDetails = txtDetails;
        }

        public void bind(final TransfersHasZonesProduct item) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }

    }
}
