package cn.ibona.t1.common.model.net.request;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.ibona.t1.common.control.utils.LogUtil;

/**
 * Created by RockyTsui on 15/9/2.
 */
public class GsonRequest<T> extends Request<T> {

    private static String TAG = GsonRequest.class.getSimpleName();

    public static final int DEFAULT_TIMEOUT_MS = 5000;
    public static final int DEFAULT_MAX_RETRIES = 0;
    public static final float DEFAULT_BACKOFF_MULT = 1.0f;

    private final Gson gson = new Gson();
    private final Map<String, String> headers;
    private final Map<String, String> params;
    private final Response.Listener<T> listener;
    private final Class<T> clazz;

    public GsonRequest(int method,
                       String url,
                       Map<String, String> headers,
                       Map<String, String> params,
                       Class<T> clazz,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.headers = headers;
        this.params = params;
        this.clazz = clazz;
        this.listener = listener;

        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS,
                DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, "utf-8");
            LogUtil.json(json);

            json = json.substring(json.indexOf("{"));

            return Response.success(gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        LogUtil.e("deliverError = " + error);
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        if(headers != null) return headers;

        //如果直接返回空，有部分api会出现404异常
        Map<String, String> tmp = super.getHeaders();
//        Map<String, String> tmp = new HashMap<String, String>();
//        tmp.put("Content-Type", "application/json; charset=utf-8");
        return tmp;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        LogUtil.i(getUrl() + "\n"+ params.toString());
        return params;
    }
}
