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
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterDiferenceInventoryErp;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterDiferenceInventoryErpVisual;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
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
        String date[] = admin.getCurrentDateAndTime().split(" ");
        ((TextView)getElemento(R.id.txtFecha)).setText(date[0]);
        ((TextView)getElemento(R.id.txtHora)).setText(date[1]);
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
        v.setVisibility(View.VISIBLE);
        this.elementos.put(v.getId(), v);
    }
    private View getElemento(int id){
        return this.elementos.get(id);
    }

}
