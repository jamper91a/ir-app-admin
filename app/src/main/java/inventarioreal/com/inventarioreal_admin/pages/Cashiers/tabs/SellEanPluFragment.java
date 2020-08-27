package inventarioreal.com.inventarioreal_admin.pages.Cashiers.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterProductosZonas;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterProductosZonasVisual;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import jamper91.com.easyway.Util.Administrador;

public class SellEanPluFragment extends Fragment {

    private SellEanPluViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;
    public ListAdapterProductosZonas adapter1;
    public ListAdapterProductosZonasVisual adapterVisual;
    private Administrador admin;
//    private LinkedList<Epcs> epcs = new LinkedList<>();



    public static SellEanPluFragment newInstance() {
        return new SellEanPluFragment();
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.ean_plu_fragment, container, false);
        this.elementos = new LinkedHashMap<>();
//        addElemento(v.findViewById(R.id.swtVisual));
        addElemento(v.findViewById(R.id.containerVisual));
        addElemento(v.findViewById(R.id.icnVisual));
        addElemento(v.findViewById(R.id.btnVisual));
        addElemento(v.findViewById(R.id.containerList));
        addElemento(v.findViewById(R.id.icnVisualDark));
        addElemento(v.findViewById(R.id.btnVisualDark));
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
        adapter1 = new ListAdapterProductosZonas(getActivity(), admin, mViewModel.getProducts().getValue(), new OnItemClickListener() {
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
        adapterVisual = new ListAdapterProductosZonasVisual(getActivity(), admin, mViewModel.getProducts().getValue(), new OnItemClickListener() {
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
        mViewModel = ViewModelProviders.of(getActivity()).get(SellEanPluViewModel.class);
        mViewModel.getProducts().observe(this, new Observer<LinkedList<ProductHasZone>>() {
            @Override
            public void onChanged(@Nullable LinkedList<ProductHasZone> products) {
                if(products!=null){
                    adapter1.setItems(products);
                    adapter1.notifyDataSetChanged();
                    adapterVisual.setItems(products);
                    adapterVisual.notifyDataSetChanged();
                }
            }
        });

    }

    private void onClick(){
        final LinearLayout containerVisual = (LinearLayout) getElemento(R.id.containerVisual);
        final LinearLayout containerList = (LinearLayout) getElemento(R.id.containerList);

        containerVisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView lst1 = (RecyclerView)getElemento(R.id.lst1);
                lst1.setLayoutManager(new GridLayoutManager(getContext(), 3));
                lst1.setAdapter(adapterVisual);
                containerVisual.setVisibility(View.GONE);
                containerList.setVisibility(View.VISIBLE);
            }
        });


        containerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView lst1 = (RecyclerView)getElemento(R.id.lst1);
                lst1.setLayoutManager(new LinearLayoutManager(getContext()));
                lst1.setAdapter(adapter1);
                containerVisual.setVisibility(View.VISIBLE);
                containerList.setVisibility(View.GONE);
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
