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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import jamper91.com.easyway.Util.Administrador;

/**
 * Created by sonu on 19/09/16.
 */

public class RecyclerAdapterInventariosAConsolidar extends RecyclerView.Adapter<RecyclerAdapterInventariosAConsolidar.RecyclerViewHolder> {

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView txtZona;
        private TextView txtFecha;
        private TextView txtHora;
        private CheckBox chb1;

        RecyclerViewHolder(View view) {
            super(view);
            txtZona = (TextView) view.findViewById(R.id.txtNum);
            txtFecha = (TextView) view.findViewById(R.id.txtLocDes);
            txtHora = (TextView) view.findViewById(R.id.txtManEle);
            chb1 = (CheckBox) view.findViewById(R.id.chb1);
        }

    }

    private ArrayList<Inventory> inventarios;
    private Context context;
    private ArrayList<Integer> inventariosSeleccionados = new ArrayList<>();
    private Administrador admin;



    public RecyclerAdapterInventariosAConsolidar(Context context, ArrayList<Inventory> inventarios, Administrador admin) {
        this.inventarios = inventarios;
        this.context = context;
        this.admin = admin;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_inventarios_a_consolidar, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int i) {

        holder.txtZona.setText(inventarios.get(i).getZone().getName());
        try {
            holder.txtFecha.setText(inventarios.get(i).getDate().split("T")[0]);
            holder.txtHora.setText(inventarios.get(i).getDate().split("T")[1]);
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
                    boolean r=itemCheckBoxChanged(v);
                    ((CompoundButton) v).setChecked(r);
                    if(!r){
                        admin.toast(context.getString(R.string.error_inventarios_de_la_misma_zona));
                    }
                } else {
                    inventariosSeleccionados.remove(v.getTag());
                }

            }
        });


    }


    //On selecting any view set the current position to selectedPositon and notify adapter
    private boolean itemCheckBoxChanged(View v) {
        try {
            //Inventario seleccionado
            Inventory invSel = inventarios.get((int)v.getTag());
            //Reviso que no se haya agregado antes la misma zona
            for (Integer i:inventariosSeleccionados) {
                Inventory inv = inventarios.get(i.intValue());
                if(inv.getZone().getId()==invSel.getZone().getId())
                    return false;
            }
            inventariosSeleccionados.add((int) v.getTag());
            notifyDataSetChanged();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
            throw new Exception(context.getString(R.string.error_inventario_no_seleccionado));
        }
    }

    public void setInventarios(ArrayList<Inventory> inventarios) {
        this.inventarios = inventarios;
    }
}