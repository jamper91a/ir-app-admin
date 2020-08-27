package inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs;

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

public class TransTotalFragment extends Fragment {

    private TransTotalViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;

    public static TransTotalFragment newInstance() {
        return new TransTotalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.total_fragment, container, false);
        this.elementos = new LinkedHashMap<>();
        addElemento(v.findViewById(R.id.txt2));
//        addElemento(v.findViewById(R.id.txt4));
        addElemento(v.findViewById(R.id.timeContainer));
        addElemento(v.findViewById(R.id.txtFecha));
        addElemento(v.findViewById(R.id.txtHora));
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(TransTotalViewModel.class);
        mViewModel.getAmount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                TextView txtCant = (TextView)getElemento(R.id.txt2);
                txtCant.setText(integer+"");
            }
        });
        mViewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                TextView txtFecha = (TextView) getElemento(R.id.txtFecha);
                TextView txtHora = (TextView) getElemento(R.id.txtHora);
                String[] date = s.split(" ");
                txtFecha.setText(date[0]);
                txtHora.setText(date[1]);
            }
        });
    }

    private void addElemento(View v){
        this.elementos.put(v.getId(), v);
    }
    private View getElemento(int id){
        return this.elementos.get(id);
    }


}
