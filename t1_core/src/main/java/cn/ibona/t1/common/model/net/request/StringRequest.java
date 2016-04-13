package cn.ibona.t1.common.model.net.request;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import cn.ibona.t1.common.control.utils.LogUtil;
import cn.ibona.t1.common.model.data_center.request.ICancelable;

/**
 * 文本请求
 * Created by qun on 16/1/20.
 */
public class StringRequest extends com.android.volley.toolbox.StringRequest{
    public static final int DEFAULT_TIMEOUT_MS = 5000;
    public static final int DEFAULT_MAX_RETRIES = 0;
    public static final float DEFAULT_BACKOFF_MULT = 1.0f;

    private Map<String, String> headers;
    private Map<String, String> params;

    private ICancelable cancelable;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequest(int method,
                         String url,
                         Map<String, String> headers,
                         Map<String, String> params,
                         Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.headers = headers;
        this.params = params;

        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS,
                DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT));
    }

    public StringRequest(int method,
                         String url,
                         Map<String, String> headers,
                         Map<String, String> params,
                         ICancelable cancelable,
                         Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.headers = headers;
        this.params = params;
        this.cancelable = cancelable;

        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS,
                DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT));
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
    public Map<String, String> getParams() throws AuthFailureError {
        if(params==null) return super.getParams();
        LogUtil.i(getUrl() + "\n" + params);//打印url和参数
        return params;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {

        //copy from super
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        LogUtil.json(parsed);//打印请求返回的数据

        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));

        //另一个方式
//        try {
//            String json = new String(response.data, "utf-8");
//            json = json.substring(json.indexOf("{"));
//
//            return Response.success(json,
//                    HttpHeaderParser.parseCacheHeaders(response));
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return Response.error(new ParseError(e));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Response.error(new ParseError(e));
//        }
    }

    @Override
    protected void deliverResponse(String response) {
        super.deliverResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        LogUtil.e("deliverError = " + error);
    }

    @Override
    public boolean isCanceled() {
        //扩展是否取消的判断，使volley能够通过外层提供的标志进行取消的判定，
        //以避免进行不必要的请求或者在返回时进行不需要的回调。
        //通过内部调用的方式，避免了外部与volley架构的绑定关系。实现松耦合。
        boolean ret = super.isCanceled();
        if(cancelable!=null) ret = ret && cancelable.isCanceled();
        return ret;
    }

    @Override
    public void cancel() {
        super.cancel();
        if(cancelable!=null) cancelable.cancel();
    }
}
