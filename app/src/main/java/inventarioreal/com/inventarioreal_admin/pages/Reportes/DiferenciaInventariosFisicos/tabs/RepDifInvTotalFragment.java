package inventarioreal.com.inventarioreal_admin.pages.Reportes.DiferenciaInventariosFisicos.tabs;

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

public class RepDifInvTotalFragment extends Fragment {

    private RepDifInvTotalViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;

    public static RepDifInvTotalFragment newInstance() {
        return new RepDifInvTotalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.total_fragment, container, false);
        this.elementos = new LinkedHashMap<>();
        addElemento(v.findViewById(R.id.txt0));
        addElemento(v.findViewById(R.id.txtCant));
        addElemento(v.findViewById(R.id.txt1));
        addElemento(v.findViewById(R.id.txtLocDes));
        addElemento(v.findViewById(R.id.txt2));
        addElemento(v.findViewById(R.id.txtNum));
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(RepDifInvTotalViewModel.class);
        mViewModel.getAmount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer amount) {
                TextView txtCant = (TextView) getElemento(R.id.txtCant);
                txtCant.setText(amount+"");
            }
        });
        mViewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String fecha) {
                TextView txtFecha = (TextView) getElemento(R.id.txtLocDes);
                txtFecha.setText(fecha);
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
