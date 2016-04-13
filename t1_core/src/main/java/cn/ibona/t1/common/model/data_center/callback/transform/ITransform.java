package cn.ibona.t1.common.model.data_center.callback.transform;

import java.lang.reflect.Type;

/**
 * 装换器
 * Created by qun on 16/1/22.
 */
public interface ITransform<F, T> {
    T transform(F msg, Type type);
}
