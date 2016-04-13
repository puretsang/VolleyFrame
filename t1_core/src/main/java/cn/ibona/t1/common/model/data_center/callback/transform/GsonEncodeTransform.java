package cn.ibona.t1.common.model.data_center.callback.transform;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by qun on 16/1/22.
 */
public class GsonEncodeTransform<F> implements ITransform<F, String>{

    @Override
    public String transform(F msg, Type type) {
        return new Gson().toJson(msg, type);
    }
}
