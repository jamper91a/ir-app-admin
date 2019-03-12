package inventarioreal.com.inventarioreal_admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;

/**
 * Created by sonu on 19/09/16.
 */

public class RecyclerAdapterInventarios extends RecyclerView.Adapter<RecyclerAdapterInventarios.RecyclerViewHolder> {

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView txtZona;
        private TextView txtFecha;
        private TextView txtHora;
        private CheckBox chb1;

        RecyclerViewHolder(View view) {
            super(view);
            txtZona = (TextView) view.findViewById(R.id.txtZona);
            txtFecha = (TextView) view.findViewById(R.id.txtFecha);
            txtHora = (TextView) view.findViewById(R.id.txtHora);
            chb1 = (CheckBox) view.findViewById(R.id.chb1);
        }

    }

    private ArrayList<Inventarios> inventarios;
    private Context context;
    private ArrayList<Integer> inventariosSeleccionados = new ArrayList<>();



    public RecyclerAdapterInventarios(Context context, ArrayList<Inventarios> inventarios) {
        this.inventarios = inventarios;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_custom_row_layout, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int i) {

        holder.txtZona.setText(inventarios.get(i).getZonas_id().getName());
        try {
            holder.txtFecha.setText(inventarios.get(i).getFecha().split("T")[0]);
            holder.txtHora.setText(inventarios.get(i).getFecha().split("T")[1]);
        } catch (Exception e) {

        }

        //check the radio button if both position and selectedPosition matches
        holder.chb1.setChecked(inventariosSeleccionados.contains(i));


        //Set the position tag to both radio button and txtZona
        holder.chb1.setTag(i);
        holder.txtZona.setTag(i);
        holder.txtZona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.chb1.performClick();
            }


        });

        holder.chb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CompoundButton) v).isChecked()){
                    itemCheckBoxChanged(v);
                } else {
                    inventariosSeleccionados.remove((Integer) v.getTag());
                }

            }
        });


    }


    //On selecting any view set the current position to selectedPositon and notify adapter
    private void itemCheckBoxChanged(View v) {
        inventariosSeleccionados.add((Integer) v.getTag());
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return (null != inventarios ? inventarios.size() : 0);
    }

    //Return the selectedPosition item
    public ArrayList<Integer> getInventariosSeleccionados() throws Exception {
        if(!inventariosSeleccionados.isEmpty()){
            return inventariosSeleccionados;
        }else{
            throw new Exception("No se seleccionaron inventarios");
        }
    }

    public void setInventarios(ArrayList<Inventarios> inventarios) {
        this.inventarios = inventarios;
    }
}