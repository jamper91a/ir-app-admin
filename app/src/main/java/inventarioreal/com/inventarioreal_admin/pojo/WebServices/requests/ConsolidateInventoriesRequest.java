package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class ConsolidateInventoriesRequest implements WebServiceRequest{
    private List<Long> inventories;
    private String name;

    public ConsolidateInventoriesRequest(List<Long> inventories, String name) {
        this.inventories = inventories;
        this.name = name;
    }

    public HashMap<String, Object> getCampos(){
        HashMap<String, Object> campos = new HashMap<>();
        campos.put(Constants.inventories, getInventories());
        campos.put(Constants.name, name);
        return campos;
    }

    @Override
    public boolean validar() throws Error {
        return false;
    }

    private JSONArray getInventories(){
        Gson gson = new Gson();
        JSONArray array = new JSONArray();
        for (Long inventory: inventories)
            array.put(inventory);
        return  array;
    }



}
