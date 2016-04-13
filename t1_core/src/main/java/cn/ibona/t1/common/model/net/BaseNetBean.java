package cn.ibona.t1.common.model.net;

import java.io.Serializable;

/**
 * 基础网络bean
 * 数据结构由后台统一，所有网络请求的实体类都要继承BaseNetBean
 * */
public class BaseNetBean<T extends Serializable> implements Serializable {

    /**
     * 是否成功(0成功，其它:失败)
     */
    private int status;

    /**
     * 响应信息
     */
    private String info;

    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData(){
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseNetBean{" +
                "status='" + status + '\'' +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
