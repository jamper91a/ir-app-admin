package inventarioreal.com.inventarioreal_admin.pages.Transferencias.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedHashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.TransferenciaDetails;

public class TransferenciaDetailsTotalFragment extends Fragment {

    private TransferenciaDetailsTotalViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;

    public static TransferenciaDetailsTotalFragment newInstance() {
        return new TransferenciaDetailsTotalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.transferencia_details_total_fragment, container, false);
        this.elementos = new LinkedHashMap<>();
        addElemento(v.findViewById(R.id.txt0));
        addElemento(v.findViewById(R.id.txtEnv));
        addElemento(v.findViewById(R.id.txt1));
        addElemento(v.findViewById(R.id.txtRec));
        addElemento(v.findViewById(R.id.txt2));
        addElemento(v.findViewById(R.id.txtFal));
        addElemento(v.findViewById(R.id.txt3));
        addElemento(v.findViewById(R.id.txtFec));
        addElemento(v.findViewById(R.id.txt4));
        addElemento(v.findViewById(R.id.txtLocDes));
        addElemento(v.findViewById(R.id.txt5));
        addElemento(v.findViewById(R.id.txtGen));
        addElemento(v.findViewById(R.id.txt6));
        addElemento(v.findViewById(R.id.txtMen));
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(TransferenciaDetailsTotalViewModel.class);
        mViewModel.getTransferencia().observe(this, new Observer<TransferenciaDetails>() {
            @Override
            public void onChanged(@Nullable TransferenciaDetails transferenciaDetails) {
                if(transferenciaDetails!=null){
                    try {
                        TextView txtEnv =(TextView) getElemento(R.id.txtEnv);
                        txtEnv.setText(transferenciaDetails.getEnviados()+"");
                        TextView txtRec =(TextView) getElemento(R.id.txtRec);
                        txtRec.setText(transferenciaDetails.getRecibidos()+"");
                        TextView txtFal =(TextView) getElemento(R.id.txtFal);
                        txtFal.setText(transferenciaDetails.getFaltantes()+"");
                        TextView txtFec =(TextView) getElemento(R.id.txtFec);
                        txtFec.setText(transferenciaDetails.getFecha()+"");
                        TextView txtLocDes =(TextView) getElemento(R.id.txtLocDes);
                        txtLocDes.setText(transferenciaDetails.getDestino().getName());
                        TextView txtGen =(TextView) getElemento(R.id.txtGen);
                        txtGen.setText(transferenciaDetails.getGenerador().getUsername());
                        TextView txtMen =(TextView) getElemento(R.id.txtMen);
                        txtMen.setText(transferenciaDetails.getMensaje());
                    } catch (Exception e) {
                        Log.e("Exception",e.getMessage());
                    }
                }

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
