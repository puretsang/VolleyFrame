package cn.ibona.t1.common.model.data_center.request;

/**
 * 可取消的
 * Created by qun on 16/1/26.
 */
public interface ICancelable {
    void cancel();
    boolean isCanceled();
}
