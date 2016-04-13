package cn.ibona.t1.common.model.data_center.client;

import cn.ibona.t1.common.model.data_center.request.IRequestAdapter;

/**
 * 抽象数据客户端，桥接模式
 */
public interface IDataClient<T> {
    /**
     * 执行请求
     */
    void perform(IRequestAdapter<T> adapter);

    /**
     * 根据tag取消所有的请求
     */
    void cancelAll(Object tag);

    /**
     * 取消所有tag是文本，且前缀为指定前缀的请求。
     */
    void cancelAllPrefix(String prefix);
}
