package cn.ibona.t1.common.model.data_center.request;

import java.util.Map;

import cn.ibona.t1.common.model.data_center.callback.ICallback;


/**
 * 请求适配器接口
 * Created by qun on 16/1/19.
 */
public interface IRequestAdapter<T> extends ICallback<T>, ICancelable{

    enum Method{
        GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, PATCH
    }

    String getUrl();
    Map<String, String> getParams();
    Map<String, String> getHeaders();
    Object getTag();

    Method getMethod();
    //TODO 实现body功能
//    byte[] getBody();

//    //TODO 将callback改造成成员方法
//    ICallback<T> getCallback();


}
