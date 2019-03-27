package inventarioreal.com.inventarioreal_admin.pages.Inventario.InventariosColaborativos.VisualizarPorZona.Step2.tabs;

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
import inventarioreal.com.inventarioreal_admin.pages.Inventario.Inventarios.VisualizarPorZona.Step2.tabs.TotalViewModel;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventarios;

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
        addElemento(v.findViewById(R.id.txt0));
        addElemento(v.findViewById(R.id.txtCant));
        addElemento(v.findViewById(R.id.txt1));
        addElemento(v.findViewById(R.id.txtFecha));
        addElemento(v.findViewById(R.id.txt2));
        addElemento(v.findViewById(R.id.txtZona));
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(TotalViewModel.class);
        mViewModel.getInventario().observe(this, new Observer<Inventarios>() {
            @Override
            public void onChanged(@Nullable Inventarios inventarios) {
                TextView txtFecha = (TextView) getElemento(R.id.txtFecha);
                TextView txtZona = (TextView) getElemento(R.id.txtZona);
                txtFecha.setText(inventarios.getFecha().replace("T", " - "));
                txtZona.setText(inventarios.getZonas_id().getName());

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
