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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Employee;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Report;
import jamper91.com.easyway.Util.Administrador;

/**
 * Created by sonu on 19/09/16.
 */

public class RecyclerAdapterEmployees extends RecyclerView.Adapter<RecyclerAdapterEmployees.RecyclerViewHolder> {



    private ArrayList<Employee> employees;
    private Context context;
    private Administrador admin;
    private OnItemClickListener onItemClickListener;



    public RecyclerAdapterEmployees(Context context, ArrayList<Employee> employees, Administrador admin, OnItemClickListener onItemClickListener) {
        this.employees = employees;
        this.context = context;
        this.admin = admin;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_employee, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int i) {
        final Employee item = employees.get(i);
        holder.txtName.setText(item.getUser().getName());
        holder.txtGroup.setText(item.getUser().getGroup().getName());
        holder.txtActive.setText(
                item.getUser().getActive() ?
                        context.getString(R.string.active) :
                        context.getString(R.string.no_active));
        try {
            holder.txtFecha.setText(item.getCreatedAt().split("T")[0]);
        } catch (Exception e) {

        }
        holder.bind(item);


    }




    @Override
    public int getItemCount() {
        return (null != employees ? employees.size() : 0);
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtGroup;
        private TextView txtActive;
        private TextView txtFecha;

        RecyclerViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtGroup = (TextView) view.findViewById(R.id.txtGroup);
            txtActive = (TextView) view.findViewById(R.id.txtActive);
            txtFecha = (TextView) view.findViewById(R.id.txtFecha);
        }

        public void bind(final Employee item) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }

    }
}