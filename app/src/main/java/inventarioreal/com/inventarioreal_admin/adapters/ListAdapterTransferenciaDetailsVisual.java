package inventarioreal.com.inventarioreal_admin.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.ProductosTransferenciaDetail;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.TransferenciaDetails;
import jamper91.com.easyway.Util.Administrador;


/**
 * Created by jorge.moreno on 16/02/2017.
 */

public class ListAdapterTransferenciaDetailsVisual extends RecyclerView.Adapter<ListAdapterTransferenciaDetailsVisual.ViewHolder> {


    private static final String TAG = "ListAdapterEpcs";
    private Activity activity;
    private Administrador admin;
    private ProductosTransferenciaDetail[] data;
    private OnItemClickListener onItemClickListener;

    public ListAdapterTransferenciaDetailsVisual(Activity activity, Administrador admin, TransferenciaDetails data, OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.admin = admin;
        if(data!= null)
            this.data = data.getProductos();
        else{
            this.data = new ProductosTransferenciaDetail[0];
        }
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(ProductosTransferenciaDetail[] data) {
        this.data = data;
    }

    @Override
    public long getItemId(int position) {
        return data[position].getProducto().getId();
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
        final ProductosTransferenciaDetail item = data[position];
        holder.getTxt3().setText(item.getProducto().getEan());
        holder.getTxt2().setText(item.getProducto().getSize());
        holder.getTxt1().setText(item.getEnviados()+"");
        if(item.getProducto().getImagen()==null)
            item.getProducto().setImagen("");
        admin.loadImageFromInternet(item.getProducto().getImagen(), holder.getImgProduct(), R.drawable.lost, R.drawable.inventory);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txt1;
        TextView txt2;
        TextView txt3;
        NetworkImageView imgProduct;


        public ViewHolder(View view) {
            super(view);
                this.txt1 = (TextView)view.findViewById(R.id.txt1);
                this.txt2 = (TextView)view.findViewById(R.id.txt2);
                this.txt3 = (TextView)view.findViewById(R.id.txt3);
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

        public NetworkImageView getImgProduct() {
            return imgProduct;
        }

        public TextView getTxt3() {
            return txt3;
        }

        public void setTxt3(TextView txt3) {
            this.txt3 = txt3;
        }

        public void setImgProduct(NetworkImageView imgProduct) {
            this.imgProduct = imgProduct;
        }
        public void bind(final ProductosTransferenciaDetail item) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }
    }
}
