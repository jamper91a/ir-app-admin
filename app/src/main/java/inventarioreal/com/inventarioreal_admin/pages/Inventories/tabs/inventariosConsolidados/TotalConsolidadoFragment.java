package inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventariosConsolidados;

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
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ConsolidatedInventory;

public class TotalConsolidadoFragment extends Fragment {

    private TotalConsolidadoViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;

    public static TotalConsolidadoFragment newInstance() {
        return new TotalConsolidadoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.total_fragment, container, false);
        this.elementos = new LinkedHashMap<>();
        addElemento(v.findViewById(R.id.txt1));
        addElemento(v.findViewById(R.id.txt2));
        addElemento(v.findViewById(R.id.txt3));
        addElemento(v.findViewById(R.id.txt4));
        addElemento(v.findViewById(R.id.txt5));
        addElemento(v.findViewById(R.id.txt6));
        addElemento(v.findViewById(R.id.txt7));
        addElemento(v.findViewById(R.id.txt8));
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(TotalConsolidadoViewModel.class);
        mViewModel.getInventario().observe(this, new Observer<ConsolidatedInventory>() {
            @Override
            public void onChanged(@Nullable ConsolidatedInventory inventory) {
                TextView txtCant = (TextView) getElemento(R.id.txt2);
                TextView txtFecha = (TextView) getElemento(R.id.txt4);
                TextView txtZona = (TextView) getElemento(R.id.txt6);
                TextView txtMessage= (TextView) getElemento(R.id.txt8);
                txtMessage.setVisibility(View.GONE);
                txtFecha.setText(inventory.getCreatedAt().replace("T", " - "));
                txtZona.setText(inventory.getName());
                txtCant.setText(inventory.getTotal_products()+"");

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
