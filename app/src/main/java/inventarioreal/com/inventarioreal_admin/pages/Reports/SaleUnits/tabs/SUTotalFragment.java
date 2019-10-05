package inventarioreal.com.inventarioreal_admin.pages.Reports.SaleUnits.tabs;

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

public class SUTotalFragment extends Fragment {

    private SUTotalViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;

    public static SUTotalFragment newInstance() {
        return new SUTotalFragment();
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
        ((TextView)getElemento(R.id.txt1)).setText("Unidades Vendidas");
        ((TextView)getElemento(R.id.txt3)).setText("Unidades devueltas");
        ((TextView)getElemento(R.id.txt5)).setText("Fecha");
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SUTotalViewModel.class);
        mViewModel.getAmountSelled().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer amount) {
                TextView txtUniVen = (TextView) getElemento(R.id.txt2);
                txtUniVen.setText(amount+"");
            }
        });
        mViewModel.getAmountReturned().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer amount) {
                TextView txtUniRet = (TextView) getElemento(R.id.txt4);
                txtUniRet.setText(amount+"");
            }
        });
        mViewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String fecha) {
                TextView txtFecha = (TextView) getElemento(R.id.txt6);
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
