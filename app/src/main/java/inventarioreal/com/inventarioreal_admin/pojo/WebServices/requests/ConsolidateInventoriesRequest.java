package inventarioreal.com.inventarioreal_admin.pojo.WebServices.requests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.List;

import inventarioreal.com.inventarioreal_admin.util.Constants;

import static inventarioreal.com.inventarioreal_admin.util.Constants.username;

public class ConsolidateInventoriesRequest {
    private List<Long> inventories;
    private String name;

    public ConsolidateInventoriesRequest(List<Long> inventories, String name) {
        this.inventories = inventories;
        this.name = name;
    }

    public HashMap<String, String> getCampos(){
        HashMap<String, String> campos = new HashMap<>();
        campos.put(Constants.inventories, getInventories());
        campos.put(Constants.name, name);
        return campos;
    }

    private String getInventories(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        for (Long inventory: inventories)
            array.add(inventory);
        return  gson.toJson(array);
    }



}
