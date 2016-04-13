package cn.ibona.t1.common.model.net.request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

/**
 * Created by qun on 15/9/6.
 */
public class RequestQueueProvider{

    static HashMap<Integer, RequestQueue> mMap = new HashMap<>();
    static final int DEFAULT_REQUEST_QUEUE_KEY = 0;

    public static RequestQueue getDefRequestQueue(Context context){
        RequestQueue ret = mMap.get(DEFAULT_REQUEST_QUEUE_KEY);
        if (ret == null){
            ret = Volley.newRequestQueue(context);
            mMap.put(DEFAULT_REQUEST_QUEUE_KEY, ret);
        }
        return ret;
    }

    public static void destroy(){
        //结束所有等待的请求
        for(RequestQueue queue : mMap.values()){
            queue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
        //清除所有所有请求队列
        mMap.clear();
    }
}
