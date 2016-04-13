package cn.ibona.t1.common.model.data_center.callback.transform;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Gson解码转换
 * Created by qun on 16/1/22.
 */
public class GsonDecodeTransform<T> implements ITransform<String, T>{

    @Override
    public  T transform(String msg, Type type) {
        return new Gson().fromJson(msg, type);
    }
}
