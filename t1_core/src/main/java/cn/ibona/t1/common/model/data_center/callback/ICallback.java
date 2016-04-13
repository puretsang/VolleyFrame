package cn.ibona.t1.common.model.data_center.callback;

/**
 * 网络请求数据回调
 */
public interface ICallback<T> {
    void onSuccess(T data);
    void onError(Error error);
}
