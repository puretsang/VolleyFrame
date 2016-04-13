package cn.ibona.t1.common.model.net.request;

import com.android.volley.Response;

import java.util.Map;

/**
 * Created by RockyTsui on 15/9/2.
 */
public class GsonPostRequest<T> extends GsonRequest<T> {
    public GsonPostRequest(String url,
                           Map<String, String> headers,
                           Map<String, String> params,
                           Class<T> clazz,
                           Response.Listener<T> listener,
                           Response.ErrorListener errorListener) {
        super(Method.POST, url, headers, params, clazz, listener, errorListener);
    }
}
