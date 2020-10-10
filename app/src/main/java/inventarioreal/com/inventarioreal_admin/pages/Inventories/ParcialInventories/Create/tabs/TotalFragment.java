package inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedHashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;

public class TotalFragment extends Fragment {

    private TotalViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;

    public static TotalFragment newInstance() {
        return new TotalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.total_fragment, container, false);
        this.elementos = new LinkedHashMap<>();
        addElemento(v.findViewById(R.id.txt1));
        addElemento(v.findViewById(R.id.txt2));
//        addElemento(v.findViewById(R.id.txt3));
//        addElemento(v.findViewById(R.id.txt4));
        addElemento(v.findViewById(R.id.txt5));
        addElemento(v.findViewById(R.id.txt6));
        addElemento(v.findViewById(R.id.timeContainer));
        addElemento(v.findViewById(R.id.txtFecha));
        addElemento(v.findViewById(R.id.txtHora));
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(TotalViewModel.class);
        mViewModel.getAmount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                TextView txtCant = (TextView) getElemento(R.id.txt2);
                txtCant.setText(integer+"");
            }
        });
        mViewModel.getInventory().observe(this, new Observer<Inventory>() {
            @Override
            public void onChanged(@Nullable Inventory inventory) {
                TextView txtFecha = (TextView) getElemento(R.id.txtFecha);
                TextView txtHora = (TextView) getElemento(R.id.txtHora);
                TextView txtZona = (TextView) getElemento(R.id.txt6);
                String[] date = null;
                if( inventory.getDate().contains("T")) {
                    date = inventory.getDate().split("T");
                } else {
                    date = inventory.getDate().split(" ");
                }
                if(date != null) {
                    txtFecha.setText(date[0]);
                    txtHora.setText(date[1]);
                }

                txtZona.setText(inventory.getZone().getName());
            }
        });
        mViewModel.getTransfer().observe(this, new Observer<Transfer>() {
            @Override
            public void onChanged(@Nullable Transfer transfer) {
                TextView txtFecha = (TextView) getElemento(R.id.txtFecha);
                TextView txtHora = (TextView) getElemento(R.id.txtHora);
                TextView lblLocDes = (TextView) getElemento(R.id.txt5);
                TextView txtLocDes = (TextView) getElemento(R.id.txt6);
                String[] date = null;
                if( transfer.getCreatedAt().contains("T")) {
                    date = transfer.getCreatedAt().split("T");
                } else {
                    date = transfer.getCreatedAt().split(" ");
                }
                if(date != null) {
                    txtFecha.setText(date[0]);
                    txtHora.setText(date[1]);
                }
                lblLocDes.setText(getString(R.string.local_destino)+": ");
                txtLocDes.setText(transfer.getShopDestination().getName());
            }
        });
    }

    private void addElemento(View v){
        v.setVisibility(View.VISIBLE);
        this.elementos.put(v.getId(), v);
    }
    private View getElemento(int id){
        return this.elementos.get(id);
    }


}
