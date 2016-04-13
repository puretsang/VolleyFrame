package cn.ibona.t1.common.model.data_center.request;

import java.util.Map;


/**
 * 请求适配器
 * Created by qun on 16/1/19.
 */
public abstract class RequestAdapter<T> implements IRequestAdapter<T>{

    private boolean cancel;
    private Object tag;

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Map<String, String> getParams() {
        return null;
    }

    @Override
    public Map<String, String> getHeaders() {
        return null;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    //TODO 实现body功能。
//    @Override
//    public byte[] getBody() {
//        return new byte[0];
//    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public void cancel() {
        cancel = true;
    }

    @Override
    public boolean isCanceled() {
        return cancel;
    }

    /**
     * 设置tag
     */
    public void setTag(Object tag){
        this.tag = tag;
    }

    //    @Override
//    public ICallback<T> getCallback() {
//        return null;
//    }
}
