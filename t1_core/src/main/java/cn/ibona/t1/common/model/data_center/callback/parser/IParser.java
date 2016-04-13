package cn.ibona.t1.common.model.data_center.callback.parser;

import java.lang.reflect.Type;

/**
 * 解析器接口
 */
public interface IParser {
    <T>T parse(String msg, Type type);
}
