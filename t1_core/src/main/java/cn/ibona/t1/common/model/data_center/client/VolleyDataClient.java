package cn.ibona.t1.common.model.data_center.client;

import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import cn.ibona.t1.common.BaseApplication;
import cn.ibona.t1.common.control.utils.LogUtil;
import cn.ibona.t1.common.model.data_center.request.IRequestAdapter;
import cn.ibona.t1.common.model.data_center.request.RequestAdapter;
import cn.ibona.t1.common.model.net.request.RequestQueueProvider;
import cn.ibona.t1.common.model.net.request.StringRequest;

/**
 * Volley请求客户端,<br/>
 * 内部使用volley RequestQueue单例，所以多次创建不会增加实际客户端数。
 */
public class VolleyDataClient implements IDataClient<String> {
    public static final boolean DEBUG = false;
    private RequestQueue queue;

    public VolleyDataClient() {
        queue = RequestQueueProvider.getDefRequestQueue(BaseApplication.getInstance());
    }

    @Override
    public void perform(IRequestAdapter<String> adapter) {
        //实现volley的请求。
        if(DEBUG) LogUtil.i("perform " + adapter);

        if(adapter==null) {
            LogUtil.e("perform(RequestAdapter<String>) arg is null !! ");
            return;
        }
        Request request = buildRequest(adapter);
        queue.add(request);
    }

    @Override
    public void cancelAll(Object tag) {
        if(DEBUG) LogUtil.i("cancelAll " + tag);
        queue.cancelAll(tag);
    }

    @Override
    public void cancelAllPrefix(final String prefix){
        if(DEBUG) LogUtil.i("cancelAllPrefix " + prefix);
        if(prefix==null){
            return;
        }
        queue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                Object tag = request.getTag();
                if(DEBUG) LogUtil.i("cancelAllPrefix " + prefix +" -> "+ tag);
                if(tag instanceof String){
                    if(( (String)tag ).startsWith(prefix)) {
                        if(DEBUG) LogUtil.i("cancelAllPrefix " + prefix +" -> "+ tag +" true");
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 使用请求适配器创建volley 的String请求。
     */
    private static StringRequest buildRequest(@NonNull final IRequestAdapter<String> adapter) {
        int method = getVolleyMethod(adapter.getMethod());
        StringRequest ret = new StringRequest(method, adapter.getUrl(), adapter.getHeaders(),
                adapter.getParams(), adapter,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //请求成功的回调
                        if (adapter != null && !adapter.isCanceled()) {
                            adapter.onSuccess(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //请求失败的回调
                        if (adapter != null && !adapter.isCanceled()) {
                            adapter.onError(new Error(error.toString()));
                        }
                    }
                });
        ret.setTag(adapter.getTag());
        return ret;
    }

    /**
     * 将通用的请求类型转化为volley请求类型
     */
    private static int getVolleyMethod(RequestAdapter.Method method) {
        int ret;
        switch (method){
            case GET:
                ret = Request.Method.GET;
                break;
            case POST:
                ret = Request.Method.POST;
                break;
            case PUT:
                ret = Request.Method.PUT;
                break;
            case DELETE:
                ret = Request.Method.DELETE;
                break;
            case HEAD:
                ret = Request.Method.HEAD;
                break;
            case OPTIONS:
                ret = Request.Method.OPTIONS;
                break;
            case TRACE:
                ret = Request.Method.TRACE;
                break;
            case PATCH:
                ret = Request.Method.PATCH;
                break;
            default:
                ret = Request.Method.GET;
        }
        return ret;
    }

}
