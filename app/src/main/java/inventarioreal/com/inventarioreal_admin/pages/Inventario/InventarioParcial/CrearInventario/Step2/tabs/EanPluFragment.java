package inventarioreal.com.inventarioreal_admin.pages.Inventario.InventarioParcial.CrearInventario.Step2.tabs;

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
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapter1;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Epcs;
import jamper91.com.easyway.Util.Administrador;

public class EanPluFragment extends Fragment {

    private EanPluViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;
    public ListAdapter1 adapter1;
    private Administrador admin;
//    private LinkedList<Epcs> epcs = new LinkedList<>();



    public static EanPluFragment newInstance() {
        return new EanPluFragment();
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.ean_plu_fragment, container, false);
        this.elementos = new LinkedHashMap<>();
        addElemento(v.findViewById(R.id.lnl2));
        addElemento(v.findViewById(R.id.lst1));

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
        adapter1 = new ListAdapter1(getActivity(), admin, mViewModel.getEpcs().getValue(), new OnItemClickListener() {
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
        lst1.setAdapter(adapter1);
        // TODO: Use the ViewModel
    }

    private void getData(){
        mViewModel = ViewModelProviders.of(getActivity()).get(EanPluViewModel.class);
        mViewModel.getEpcs().observe(this, new Observer<LinkedList<Epcs>>() {
            @Override
            public void onChanged(@Nullable LinkedList<Epcs> epcs) {
                if(epcs.size()>0){
                    adapter1.add(epcs.getLast());
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
