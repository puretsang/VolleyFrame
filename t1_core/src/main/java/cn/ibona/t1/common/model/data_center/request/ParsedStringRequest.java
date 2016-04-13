package cn.ibona.t1.common.model.data_center.request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.ibona.t1.common.control.utils.LogUtil;
import cn.ibona.t1.common.model.data_center.callback.ICallback;
import cn.ibona.t1.common.model.data_center.callback.parser.GsonParser;
import cn.ibona.t1.common.model.data_center.callback.parser.IParser;

/**
 * 限定请求返回的数据为文本，且对文本进行解析
 * 默认使用Gson进行解析
 * Created by qun on 16/1/21.
 */
public abstract class ParsedStringRequest<T> extends RequestAdapter<String> {

    public static final boolean DEBUG_GENERIC_TYPE = false;
    IParser parser;
    ICallback<T> callback;

    public ParsedStringRequest(IParser parser) {
        this.parser = parser;
    }

    public ParsedStringRequest() {
        this(new GsonParser());
    }

    @Override
    public void onError(Error error) {
        if(callback!=null) callback.onError(error);
    }

    @Override
    public final void onSuccess(String data) {
        T obj = parser.parse(data, getGenericType());
        onParseSuccess(obj);
    }

    /**
     * 解析成功时
     */
    protected void onParseSuccess(T obj){
        if(callback!=null) callback.onSuccess(obj);
    }

    public IParser getParser() {
        return parser;
    }

    public void setParser(IParser parser) {
        this.parser = parser;
    }

    public ICallback<T> getCallback() {
        return callback;
    }

    public void setCallback(ICallback<T> callback) {
        this.callback = callback;
    }

    /**
     * 获取泛型中的T，转换目标类的类型
     * @deprecated 这个只适合一层继承的情况。
     */
    private Type getTargetTypeArgument(){
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return params[0];
    }

    /**
     * 获取泛型类型
     */
    private Type getGenericType() {
        Class<?> cls = getClass();
        Class<?> save;
        if(DEBUG_GENERIC_TYPE) LogUtil.i(cls.toString());
        //通过继承链，找到类型本身
        do {
            //保存当前类，备用
            save = cls;
            //得到父类
            cls = cls.getSuperclass();
            if(DEBUG_GENERIC_TYPE) LogUtil.i(cls.toString());
            //如果其父类是ParsedStringRequest.class，说明到了我们想要的类层次
            if(cls == ParsedStringRequest.class) {
                //从保存的当前类中获取带泛型父类
                Type t = save.getGenericSuperclass();
                if(DEBUG_GENERIC_TYPE) LogUtil.i("getGenericSuperclass " + t.toString());
                if (t instanceof ParameterizedType) {
                    Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                    if(DEBUG_GENERIC_TYPE) LogUtil.i("args[0] " + args[0].toString());
                    return args[0];
                }
            }
        }while(cls != Object.class);
        LogUtil.e("ParsedStringRequest.class getGenericType() error!!");
        onError(new Error("ParsedStringRequest.class getGenericType() error!!"));
        return null;
    }
}
