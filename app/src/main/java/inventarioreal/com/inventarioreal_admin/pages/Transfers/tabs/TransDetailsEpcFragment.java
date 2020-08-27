package inventarioreal.com.inventarioreal_admin.pages.Transfers.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterTransferEpcDetails;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.TransferenciaDetails;
import jamper91.com.easyway.Util.Administrador;

public class TransDetailsEpcFragment extends Fragment {

    private TransDetailsEanPluViewModel mViewModel;
//    private TransDetailsTotalViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;
    public ListAdapterTransferEpcDetails adapter1;
    private Administrador admin;
    private TransferenciaDetails transferencia = null;



    public static TransDetailsEpcFragment newInstance() {
        return new TransDetailsEpcFragment();
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.epc_fragment, container, false);
        this.elementos = new LinkedHashMap<>();
        addElemento(v.findViewById(R.id.lnl2));
        addElemento(v.findViewById(R.id.lst1));
        addElemento(v.findViewById(R.id.txtFecha));
        addElemento(v.findViewById(R.id.txtHora));


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();

        adapter1 = new ListAdapterTransferEpcDetails(getActivity(), admin, mViewModel.getTransferencia().getValue(), new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
            }

            @Override
            public void onLongItemClick(Object item) {

            }

            @Override
            public void onItemClick(int view, Object item) {

            }
        });


        RecyclerView lst1 = (RecyclerView)getElemento(R.id.lst1);
        lst1.setLayoutManager(new LinearLayoutManager(getContext()));
        lst1.setHasFixedSize(true);
        lst1.setAdapter(adapter1);
        // TODO: Use the ViewModel
        onClick();
    }

    private void getData(){
        mViewModel = ViewModelProviders.of(getActivity()).get(TransDetailsEanPluViewModel.class);
        mViewModel.getTransferencia().observe(this, new Observer<TransferenciaDetails>() {
            @Override
            public void onChanged(@Nullable TransferenciaDetails transferencia) {
                if(transferencia !=null && transferencia.getProductos()!= null){
                    adapter1.setData(transferencia);
                    adapter1.notifyDataSetChanged();
                }
            }
        });

    }

    private void onClick(){

    }

    private void addElemento(View v){
        this.elementos.put(v.getId(), v);
    }
    private View getElemento(int id){
        return this.elementos.get(id);
    }

}
