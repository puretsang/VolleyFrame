package cn.ibona.t1.common.model.net.request;

import com.android.volley.VolleyError;

/**
 * Created by qun on 15/9/8.
 */
public class T1Exception extends Exception{
    VolleyError volleyError;

    public T1Exception(VolleyError error){
        volleyError = error;
    }

    @Override
    public String getMessage() {
        return volleyError.getMessage();
    }

    @Override
    public void printStackTrace() {
        volleyError.printStackTrace();
    }

}
