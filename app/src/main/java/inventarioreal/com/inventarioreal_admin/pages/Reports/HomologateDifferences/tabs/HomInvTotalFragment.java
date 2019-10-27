package inventarioreal.com.inventarioreal_admin.pages.Reports.HomologateDifferences.tabs;

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

public class HomInvTotalFragment extends Fragment {

    private HomInvTotalViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;

    public static HomInvTotalFragment newInstance() {
        return new HomInvTotalFragment();
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
        ((TextView)getElemento(R.id.txt1)).setText(R.string.unidades_no_encontradas);
        ((TextView)getElemento(R.id.txt3)).setText(R.string.fecha);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(HomInvTotalViewModel.class);
        mViewModel.getAmount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer amount) {
                TextView txtCant = (TextView) getElemento(R.id.txt2);
                txtCant.setText(amount+"");
            }
        });
        mViewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String fecha) {
                TextView txtFecha = (TextView) getElemento(R.id.txt4);
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
