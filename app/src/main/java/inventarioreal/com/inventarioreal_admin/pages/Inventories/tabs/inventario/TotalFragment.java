package inventarioreal.com.inventarioreal_admin.pages.Inventories.tabs.inventario;

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
        addElemento(v.findViewById(R.id.txt7));
        addElemento(v.findViewById(R.id.txt8));
        addElemento(v.findViewById(R.id.timeContainer));
        addElemento(v.findViewById(R.id.txtFecha));
        addElemento(v.findViewById(R.id.txtHora));
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(TotalViewModel.class);
        mViewModel.getInventario().observe(this, new Observer<Inventory>() {
            @Override
            public void onChanged(@Nullable Inventory inventarios) {
                TextView txtAmount = (TextView) getElemento(R.id.txt2);
                TextView txtMessage= (TextView) getElemento(R.id.txt8);
                txtAmount.setText(inventarios.getProducts().length+"");
                txtMessage.setText(inventarios.getMessage());

                TextView txtFecha = (TextView) getElemento(R.id.txtFecha);
                TextView txtHora = (TextView) getElemento(R.id.txtHora);
                String[] date = inventarios.getDate().split("T");
                txtFecha.setText(inventarios.getZone().getName());
                txtHora.setText(inventarios.getDate().replace("T", " - ").replace('Z', ' ').replace(".000", ""));

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
