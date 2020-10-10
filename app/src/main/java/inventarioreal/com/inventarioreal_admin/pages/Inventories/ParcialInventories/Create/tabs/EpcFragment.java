package inventarioreal.com.inventarioreal_admin.pages.Inventories.ParcialInventories.Create.tabs;

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
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.adapters.ListAdapterTotalReportEpcDetails;
import inventarioreal.com.inventarioreal_admin.listener.OnItemClickListener;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Inventory;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.ProductHasZone;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo.Transfer;
import jamper91.com.easyway.Util.Administrador;

public class EpcFragment extends Fragment {

    private EpcViewModel mViewModel;
    private LinkedHashMap<Integer, View> elementos;
    public ListAdapterTotalReportEpcDetails adapter1;
    private Administrador admin;


    public static EpcFragment newInstance() {
        return new EpcFragment();
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
        addElemento(v.findViewById(R.id.timeContainer));
        addElemento(v.findViewById(R.id.txtFecha));
        addElemento(v.findViewById(R.id.txtHora));

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
        adapter1 = new ListAdapterTotalReportEpcDetails(getActivity(), admin, mViewModel.getAllProducts().getValue(), new OnItemClickListener() {
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
        mViewModel = ViewModelProviders.of(getActivity()).get(EpcViewModel.class);

        mViewModel.getAllProducts().observe(this, new Observer<LinkedList<ProductHasZone>>() {
            @Override
            public void onChanged(@Nullable LinkedList<ProductHasZone> productosZonas) {
                if(productosZonas!=null){
                    adapter1.setData(productosZonas);
                    adapter1.notifyDataSetChanged();
                }
            }
        });

        mViewModel.getInventario().observe(this, new Observer<Inventory>() {
            @Override
            public void onChanged(@Nullable Inventory inventory) {
                TextView txtFecha = (TextView) getElemento(R.id.txtFecha);
                TextView txtHora = (TextView) getElemento(R.id.txtHora);
                txtFecha.setText(inventory.getZone().getName());
                txtHora.setText(inventory.getDate().replace("T", " - ").replace('Z', ' ').replace(".000", ""));
            }
        });

        mViewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                TextView txtFecha = (TextView) getElemento(R.id.txtFecha);
                TextView txtHora = (TextView) getElemento(R.id.txtHora);
                String[] date = null;
                if(s.contains("T")) {
                    date = s.split("T");
                } else if (s.contains(" ")) {
                    date = s.split(" ");
                }
                if(date != null) {
                    txtFecha.setText(date[0]);
                    txtHora.setText(date[1]);
                }

            }
        });
        mViewModel.getTransfer().observe(this, new Observer<Transfer>() {
            @Override
            public void onChanged(@Nullable Transfer transfer) {
                ((TextView)getElemento(R.id.txtFecha)).setText(transfer.getShopDestination().getName());
                ((TextView)getElemento(R.id.txtHora)).setText(transfer.getCreatedAt());
            }
        });


    }

    private void onClick(){
    }

    private void addElemento(View v){
        v.setVisibility(View.VISIBLE);
        this.elementos.put(v.getId(), v);
    }
    private View getElemento(int id){
        return this.elementos.get(id);
    }

}