package inventarioreal.com.inventarioreal_admin.util;

import com.android.volley.VolleyError;

/**
 * Created by @jvillafane on 11/07/2016.
 */
public interface ResponseListener {
    public void onResponse(String response);
    public void onErrorResponse(VolleyError error);
}
