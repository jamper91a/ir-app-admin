package inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public abstract class InventarioRealPojo {
    public InventarioRealPojo() {
    }

    public void fromHashMap(Class myClass, HashMap<String,String> data) throws IllegalAccessException {
        Class thisClass = myClass.getClass();
        for(Field field : thisClass.getFields()){
            if(data.containsKey(field.getName())){
                field.set(myClass,data.get(field.getName()));
            }
        }
    }
    public void toHashMap(Class myClass) throws IllegalAccessException, NoSuchFieldException {

        HashMap<String,String> data= new HashMap<>();
        Class thisClass = myClass.getClass();
        for(Field field : thisClass.getFields()){
            data.put(field.getName(), (String) field.get(myClass));
            if(data.containsKey(field.getName())){
                field.set(myClass,data.get(field.getName()));
            }
        }
    }

    public abstract ContentValues getContentValues();
}
