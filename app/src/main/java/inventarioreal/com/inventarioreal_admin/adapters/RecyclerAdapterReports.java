package inventarioreal.com.inventarioreal_admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Report;
import jamper91.com.easyway.Util.Administrador;

/**
 * Created by sonu on 19/09/16.
 */

public class RecyclerAdapterReports extends RecyclerView.Adapter<RecyclerAdapterReports.RecyclerViewHolder> {



    private ArrayList<Report> reports;
    private Context context;
    private Administrador admin;
    private OnItemClickListener onItemClickListener;



    public RecyclerAdapterReports(Context context, ArrayList<Report> reports, Administrador admin, OnItemClickListener onItemClickListener) {
        this.reports = reports;
        this.context = context;
        this.admin = admin;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_inventarios, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int i) {
        final Report item = reports.get(i);
        holder.txtZona.setText(item.getId()+"");
        try {
            holder.txtFecha.setText(reports.get(i).getCreatedAt().split("T")[0]);
            holder.txtHora.setText(reports.get(i).getCreatedAt().split("T")[1]);
        } catch (Exception e) {

        }
        holder.txtZona.setTag(i);
        holder.bind(item);


    }




    @Override
    public int getItemCount() {
        return (null != reports ? reports.size() : 0);
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView txtZona;
        private TextView txtFecha;
        private TextView txtHora;

        RecyclerViewHolder(View view) {
            super(view);
            txtZona = (TextView) view.findViewById(R.id.txtZona);
            txtFecha = (TextView) view.findViewById(R.id.txtFecha);
            txtHora = (TextView) view.findViewById(R.id.txtHora);
        }

        public void bind(final Report item) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }

    }
}