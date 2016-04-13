package cn.ibona.t1.common.model.net.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import cn.ibona.t1.common.control.utils.LogUtil;

/**
 *
 */
public class GsonGetRequest<T> extends GsonRequest<T> {

    static String TAG = GsonGetRequest.class.getSimpleName();

    public GsonGetRequest(String url,
                          Map<String, String> headers,
                          Map<String, String> params,
                          Class<T> clazz,
                          Response.Listener<T> listener,
                          Response.ErrorListener errorListener) {
        super(Method.GET, url, headers, params, clazz, listener, errorListener);
    }

    public static String buildParams(Map<String, String> params) {
        if (params == null || params.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder(100);
        int i = 0;
        for (String key : params.keySet()) {
            if (params.get(key) == null)
                continue;

            if (0 == i) {
                sb.append("?");
            } else {
                sb.append("&");
            }

            try {
                sb.append(key).append("=").append(URLEncoder.encode(params.get(key), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        return sb.toString();
    }

    @Override
    public String getUrl() {
        try {
            String ret = super.getUrl() + buildParams(getParams());
            LogUtil.i(ret);
            return ret;
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        return super.getUrl();
    }
}
