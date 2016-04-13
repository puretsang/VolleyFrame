package cn.ibona.t1.common.model.data_center.callback.parser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.ibona.t1.common.control.utils.LogUtil;

/**
 * Gson解析器
 */
public class GsonParser implements IParser{

    static final Gson gson = new Gson();

    @Override
    public <T> T parse(String json, Type type) {
        try {
            return gson.fromJson(json, type);
        }catch (JsonSyntaxException e){
            LogUtil.e(e.toString());
        }
        return null;
    }

//    private Type getFirstActualTypeArgument(){
//        Type[] types = getActualTypeArguments();
//        return types[0];
//    }
//
//    /**
//     * 获得实际的类型参数（泛型的真实类型）
//     */
//    private Type[] getActualTypeArguments(){
//        Type genType = getClass().getGenericSuperclass();
//        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//        return params;
//    }
}
