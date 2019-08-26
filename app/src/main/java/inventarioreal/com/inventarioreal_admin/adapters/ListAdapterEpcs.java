package inventarioreal.com.inventarioreal_admin.adapters;

import android.app.Activity;
import android.graphics.Color;
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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epc;
import jamper91.com.easyway.Util.Administrador;


/**
 * Created by jorge.moreno on 16/02/2017.
 */

public class ListAdapterEpcs extends RecyclerView.Adapter<ListAdapterEpcs.ViewHolder> {

    private static final String TAG = "ListAdapterEpcs";
    private Activity activity;
    private Administrador admin;
    private LinkedList<Epc> items;
    private LinkedList<Epc> todos;
    private OnItemClickListener onItemClickListener;

    public ListAdapterEpcs(Activity activity, Administrador admin, LinkedList<Epc> items, OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.admin = admin;
        this.items = items;
        this.todos = items;
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(LinkedList<Epc> items) {
        this.items = items;
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(items.get(position).getEpc());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_productos_zonas, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Epc item = items.get(position);
        holder.getTxt1().setText(item.getEpc());
        if(item.isError()){
            holder.getLnl1().setBackgroundColor(Color.parseColor("#A9E64336"));
        }
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(Epc item) {
        try {
            int position = items.indexOf(item);
            notifyItemInserted(position);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void clear() {
        try {
            int amountItems = items.size();
//            items.clear();
//            notifyItemRangeRemoved(0, amountItems);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void remove(Epc item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void update(Epc item, int position){
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
                    items = (LinkedList<Epc>) results.values;
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
                        Epc dataNames = todos.get(i);
                        if (dataNames.getEpc().toLowerCase().contains(constraint))  {
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
        LinearLayout lnl1;
        TextView txt1;
        TextView txt2;
        TextView txt3;


        public ViewHolder(View view) {
            super(view);
            this.lnl1 = (LinearLayout)view.findViewById(R.id.lnl1);
            this.txt1 = (TextView)view.findViewById(R.id.txt1);
            this.txt2 = (TextView)view.findViewById(R.id.txt2);
            this.txt3 = (TextView)view.findViewById(R.id.txt3);
            txt2.setVisibility(View.GONE);
            txt3.setVisibility(View.GONE);

        }

        public LinearLayout getLnl1() {
            return lnl1;
        }

        public TextView getTxt1() {
            return txt1;
        }



        public void bind(final Epc item) {
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
