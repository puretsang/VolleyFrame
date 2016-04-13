package cn.ibona.t1.common.control.utils;

import android.os.SystemClock;

import cn.ibona.t1.common.model.shared_pref.BaseSettings;

/**
 * 服务器时间获取工具
 * Created by Administrator on 2015/11/12.
 */
public class ServerTimeUtil {

    private long serverTime;
    private final static String SERVER_TIME="server_time";

    public ServerTimeUtil() {}

    public void init(long serverTime){
        this.serverTime=serverTime;
//        存取服务器时间与获取服务器时间时的系统时间钟到本地
        BaseSettings.putLong(SERVER_TIME, serverTime * 1000 - SystemClock.elapsedRealtime());
    }


    /**
     * 取服务器时间
     * @return 如果返回0，表示没有初始化服务器时间；其他正常返回
     */
    public long getTime(){
        if (hasInit())   {
//            获取当前服务器时间算法：当前服务器时间=系统时间钟差值+旧服务器时间
            return SystemClock.elapsedRealtime()+BaseSettings.getLong(SERVER_TIME, 0);
        }
        return 0;
    }

    public boolean hasInit() {
        return BaseSettings.getLong(SERVER_TIME, 0)!=0;
    }
}
