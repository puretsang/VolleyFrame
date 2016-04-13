package cn.ibona.t1.common.model.net.request;

/**
 * Created by qun on 15/9/8.
 */
public interface ICallback<T> {
    void onSuccess(T model);
    void onError(T1Exception e);
}
