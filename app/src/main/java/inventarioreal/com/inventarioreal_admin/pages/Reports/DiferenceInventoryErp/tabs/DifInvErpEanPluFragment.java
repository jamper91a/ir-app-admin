package inventarioreal.com.inventarioreal_admin.pages.Reports.DiferenceInventoryErp.tabs;

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
import android.widget.Switch;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterDiferenceInventoryErp;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterDiferenceInventoryErpVisual;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterProductosZonas;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterProductosZonasVisual;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.added.DiferenceInventoryErp;
import jamper91.com.easyway.Util.Administrador;

public class DifInvErpEanPluFragment extends Fragment {

    private DifInvErpEanPluViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;
    public ListAdapterDiferenceInventoryErp adapter1;
    public ListAdapterDiferenceInventoryErpVisual adapterVisual;
    private Administrador admin;


    public static DifInvErpEanPluFragment newInstance() {
        return new DifInvErpEanPluFragment();
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.ean_plu_fragment, container, false);
        this.elementos = new LinkedHashMap<>();
        addElemento(v.findViewById(R.id.swtVisual));
        addElemento(v.findViewById(R.id.lnl2));
        addElemento(v.findViewById(R.id.lst1));
        addElemento(v.findViewById(R.id.txtDate));

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
        adapter1 = new ListAdapterDiferenceInventoryErp(getActivity(), admin, mViewModel.getProducts().getValue(), new OnItemClickListener() {
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
        adapterVisual = new ListAdapterDiferenceInventoryErpVisual(getActivity(), admin, mViewModel.getProducts().getValue(), new OnItemClickListener() {
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
        ((TextView)getElemento(R.id.txtDate)).setText(admin.getCurrentDateAndTime());
        mViewModel = ViewModelProviders.of(getActivity()).get(DifInvErpEanPluViewModel.class);
        mViewModel.getProducts().observe(this, new Observer<LinkedList<DiferenceInventoryErp>>() {
            @Override
            public void onChanged(@Nullable LinkedList<DiferenceInventoryErp> products) {
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
        Switch swtVisual = (Switch)getElemento(R.id.swtVisual);
        swtVisual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    RecyclerView lst1 = (RecyclerView)getElemento(R.id.lst1);
                    lst1.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    lst1.setAdapter(adapterVisual);
                }else{
                    RecyclerView lst1 = (RecyclerView)getElemento(R.id.lst1);
                    lst1.setLayoutManager(new LinearLayoutManager(getContext()));
                    lst1.setAdapter(adapter1);
                }
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
