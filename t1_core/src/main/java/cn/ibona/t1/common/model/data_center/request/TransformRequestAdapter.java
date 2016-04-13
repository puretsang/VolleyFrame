package cn.ibona.t1.common.model.data_center.request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.ibona.t1.common.model.data_center.callback.ICallback;
import cn.ibona.t1.common.model.data_center.callback.transform.ITransform;

/**
 * 对返回结果进行指定转换的请求适配器，
 * 使用转换器包中的相关类族
 * Created by qun on 16/1/21.
 */
public abstract class TransformRequestAdapter<F, T> extends RequestAdapter<F> {

    ITransform<F, T> transform;
    ICallback<T> callback;

    public TransformRequestAdapter(ITransform<F, T> parser) {
        this.transform = parser;
    }

    @Override
    public void onError(Error error) {
        if(callback!=null) callback.onError(error);
    }

    @Override
    public final void onSuccess(F data) {
        T obj = transform.transform(data, getTargetTypeArgument());
        onTransformSuccess(obj);
    }

    /**
     * 转换成功时
     */
    protected void onTransformSuccess(T obj){
        if(callback!=null) callback.onSuccess(obj);
    }

    public ICallback<T> getCallback() {
        return callback;
    }

    public void setCallback(ICallback<T> callback) {
        this.callback = callback;
    }

    public ITransform<F, T> getTransform() {
        return transform;
    }

    public void setTransform(ITransform<F, T> transform) {
        this.transform = transform;
    }

    /**
     * 获取泛型中的T，转换目标类的类型
     */
    private Type getTargetTypeArgument(){
        Type[] types = getActualTypeArguments();
        return types[1];
    }

    /**
     * 获得实际的类型参数（泛型的真实类型）
     */
    private Type[] getActualTypeArguments(){
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return params;
    }
}
