package inventarioreal.com.inventarioreal_admin.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.TransfersHasZonesProduct;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.TransferenciaDetails;
import jamper91.com.easyway.Util.Administrador;


/**
 * Created by jorge.moreno on 16/02/2017.
 */

public class ListAdapterTransferEpcDetails extends RecyclerView.Adapter<ListAdapterTransferEpcDetails.ViewHolder> {

    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=1;

    private static final String TAG = "ListAdapterTransferencia";
    private Activity activity;
    private Administrador admin;
    private TransferenciaDetails data;
    private OnItemClickListener onItemClickListener;

    public ListAdapterTransferEpcDetails(Activity activity, Administrador admin, TransferenciaDetails transferenciaDetails, OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.admin = admin;
        this.data= transferenciaDetails;
        this.onItemClickListener = onItemClickListener;
    }

    public TransferenciaDetails getData() {
        return data;
    }

    public void setData(TransferenciaDetails data) {
        this.data = data;
    }

    @Override
    public long getItemId(int position) {
        return data.getTransfer().getProducts()[position].getProduct().getId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_list_transferencia_epc, parent, false);
                break;
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transferencia_epc, parent, false);
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
                final TransfersHasZonesProduct item = data.getTransfer().getProducts()[position-1];
                holder.getTxt1().setText(item.getProduct().getProduct().getId()+"");
                holder.getTxt2().setText(item.getProduct().getEpc().getEpc());
                holder.getTxt3().setText(item.getState()+"");
                holder.getTxt4().setText(item.getProduct().getProduct().getDescription());
                holder.bind(item);
                break;
        }



    }

    @Override
    public int getItemCount() {
        try {
            return data.getTransfer().getProducts().length+1;
        } catch (Exception e) {
            return 1;
        }
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
        int viewType;

        //region Header items
        TextView txtEnv, txtRec, txtEan, txtDet;

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
            }
            else{
                this.txtEnv = (TextView)view.findViewById(R.id.txtEnv);
                this.txtRec= (TextView)view.findViewById(R.id.txtRec);
                this.txtEan= (TextView)view.findViewById(R.id.txtEan);
                this.txtDet= (TextView)view.findViewById(R.id.txtDet);

            }
            this.viewType = viewType;

        }

        public LinearLayout getLnl1() {
            return lnl1;
        }

        public void setLnl1(LinearLayout lnl1) {
            this.lnl1 = lnl1;
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

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public TextView getTxtEnv() {
            return txtEnv;
        }

        public void setTxtEnv(TextView txtEnv) {
            this.txtEnv = txtEnv;
        }

        public TextView getTxtRec() {
            return txtRec;
        }

        public void setTxtRec(TextView txtRec) {
            this.txtRec = txtRec;
        }

        public TextView getTxtEan() {
            return txtEan;
        }

        public void setTxtEan(TextView txtEan) {
            this.txtEan = txtEan;
        }

        public TextView getTxtDet() {
            return txtDet;
        }

        public void setTxtDet(TextView txtDet) {
            this.txtDet = txtDet;
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
