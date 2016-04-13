package cn.ibona.t1.common;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.Iterator;
import java.util.List;

import cn.ibona.t1.common.control.utils.LogUtil;
import cn.ibona.t1.common.model.data_center.client.IDataClient;
import cn.ibona.t1.common.model.data_center.client.VolleyDataClient;
import cn.ibona.t1.common.model.shared_pref.BaseSettings;

/**
 * 基础application
 */
public class BaseApplication extends Application {

    private static BaseApplication mInstance;
    public static String processName;

    IDataClient<String> dataClient;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        processName = getProcessName(this, android.os.Process.myPid());
        String packageName = getPackageName();
        LogUtil.i("启动应用 processName = " + processName + "; applicationPackageName = " + packageName);


        //在本地和远端进程都初始化一个数据客户端
        dataClient = new VolleyDataClient();

        if (packageName.equals(processName)) {
            BaseSettings.init(this);//初始化共享参数
        }
    }
    /**
     * 获取数据客户端
     */
    public IDataClient<String> getDataClient() {
        return dataClient;
    }



    public static void setApplication(BaseApplication paramMoaApplication) {
        mInstance = paramMoaApplication;
    }

    public static String getProcessName(Context paramContext, int paramInt) {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo;
        List localList = ((ActivityManager) paramContext.getSystemService(ACTIVITY_SERVICE)).getRunningAppProcesses();
        if (localList == null)
            return null;
        Iterator localIterator = localList.iterator();
        do {
            if (!(localIterator.hasNext()))
                return null;
            localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo) localIterator.next();
        }
        while (localRunningAppProcessInfo.pid != paramInt);
        return localRunningAppProcessInfo.processName;
    }

}
