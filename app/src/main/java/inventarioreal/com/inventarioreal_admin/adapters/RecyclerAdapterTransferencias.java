package inventarioreal.com.inventarioreal_admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transferencias;
import jamper91.com.easyway.Util.Administrador;

/**
 * Created by sonu on 19/09/16.
 */

public class RecyclerAdapterTransferencias extends RecyclerView.Adapter<RecyclerAdapterTransferencias.RecyclerViewHolder> {



    private ArrayList<Transferencias> transferencias;
    private Context context;
    private Administrador admin;
    private OnItemClickListener onItemClickListener;
    private String Tag= "RecyclerAdapterTransferencias";


    public RecyclerAdapterTransferencias(Context context, ArrayList<Transferencias> transferencias, Administrador admin, OnItemClickListener onItemClickListener) {
        this.transferencias= transferencias;
        this.context = context;
        this.admin = admin;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_transferencias, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int i) {

        holder.txtNum.setText(transferencias.get(i).getId()+"");
        holder.txtLocDes.setText(transferencias.get(i).getLocal_destino_id().getName());
        holder.txtManEle.setText(transferencias.get(i).getManifiesto());
        try {
            holder.txtFec.setText(transferencias.get(i).getCreatedAt().split("T")[0]);
            holder.txtHor.setText(transferencias.get(i).getCreatedAt().split("T")[1]);
        } catch (Exception e) {
            Log.e(Tag, e.getMessage());
        }

    }


    @Override
    public int getItemCount() {
        return (null != transferencias ? transferencias.size() : 0);
    }

    public void setTransferencias(ArrayList<Transferencias> transferencias) {
        this.transferencias = transferencias;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNum;
        private TextView txtLocDes;
        private TextView txtManEle;
        private TextView txtFec;
        private TextView txtHor;

        RecyclerViewHolder(View view) {
            super(view);
            txtNum = (TextView) view.findViewById(R.id.txtNum);
            txtLocDes = (TextView) view.findViewById(R.id.txtLocDes);
            txtManEle = (TextView) view.findViewById(R.id.txtManEle);
            txtFec = (TextView) view.findViewById(R.id.txtFec);
            txtHor = (TextView) view.findViewById(R.id.txtHor);
        }

        public void bind(final Transferencias item) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }
    }

}