package cn.ibona.t1.common.control.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import cn.ibona.t1.common.BaseApplication;

/**
 * Created by qun on 15/11/26.
 */
public class AndroidUtils {

    /**
     * 获取运营商
     * TODO
     */
    private static String getOperator(Context paramContext)
    {
        /** 获取SIM卡的IMSI码
         * SIM卡唯一标识：IMSI 国际移动用户识别码（IMSI：International Mobile Subscriber Identification Number）是区别移动用户的标志，
         * 储存在SIM卡中，可用于区别移动用户的有效信息。IMSI由MCC、MNC、MSIN组成，其中MCC为移动国家号码，由3位数字组成，
         * 唯一地识别移动客户所属的国家，我国为460；MNC为网络id，由2位数字组成，
         * 用于识别移动客户所归属的移动网络，中国移动为00，中国联通为01,中国电信为03；MSIN为移动客户识别码，采用等长11位数字构成。
         * 唯一地识别国内GSM移动通信网中移动客户。所以要区分是移动还是联通，只需取得SIM卡中的MNC字段即可
         */
        String str = ((TelephonyManager)paramContext.getSystemService(Context.TELEPHONY_SERVICE)).getSimOperator();
        if (str != null)
        {
            if ((str.equals("46000")) || (str.equals("46002")))
                return "中国移动";
//                return paramContext.getString(2131560075);
            if (str.equals("46001"))
                return "中国联通";
//                return paramContext.getString(2131561462);
            if (str.equals("46003"))
                return "中国电信";
//                return paramContext.getString(2131561307);
        }
        return "";
    }

    /**
     * 进入一般Activity的过度动画
     * //TODO
     */
    public static void enter(Activity paramActivity)
    {
        //TODO
//        if (paramActivity != null)
//            paramActivity.overridePendingTransition(2130968600, 2130968601);
    }

    /**
     * 进入FragmentActivity的过度动画
     * //TODO
     */
    public static void enter(FragmentActivity paramFragmentActivity)
    {
        //TODO
//        if (paramFragmentActivity != null)
//            paramFragmentActivity.overridePendingTransition(2130968600, 2130968601);
    }

    /**
     * 退出一般Activity的过度动画
     * //TODO
     */
    public static void exit(Activity paramActivity)
    {
        //TODO
//        if (paramActivity != null)
//            paramActivity.overridePendingTransition(2130968601, 2130968602);
    }

    /**
     * 退出FragmentActivity的过度动画
     * //TODO
     */
    public static void exit(FragmentActivity paramFragmentActivity)
    {
        //TODO
//        if (paramFragmentActivity != null)
//            paramFragmentActivity.overridePendingTransition(2130968601, 2130968602);
    }

    /**
     * 退出activity的透明度渐变动画
     * //TODO
     */
    public static void exitWithAlpha(Activity paramActivity)
    {
        //TODO
//        if (paramActivity != null)
//            paramActivity.overridePendingTransition(2130968584, 2130968586);
    }

    /**
     *  图片退出的过度动画
     *  //TODO
     */
    public static void imageExit(Activity paramActivity)
    {
        //TODO
//        if (paramActivity != null)
//            paramActivity.overridePendingTransition(0, 2130968589);
    }

    /**
     * 获取显示的宽高
     */
    public static Point getDisplay(Context paramContext)
    {
        WindowManager localWindowManager = (WindowManager)paramContext.getSystemService(Context.WINDOW_SERVICE);
        if (localWindowManager == null)
            return new Point();
        Display localDisplay = localWindowManager.getDefaultDisplay();
        Point localPoint = new Point();
        try
        {
            localDisplay.getSize(localPoint);
            return localPoint;
        }
        catch (NoSuchMethodError localNoSuchMethodError)
        {
            localPoint.x = localDisplay.getWidth();
            localPoint.y = localDisplay.getHeight();
        }
        return localPoint;
    }

    /**
     * 获取显示的宽高,根据资源Metrics
     */
    public static Point getDisplayMetrics(Resources paramResources)
    {
        DisplayMetrics localDisplayMetrics = paramResources.getDisplayMetrics();
        Point localPoint = new Point();
        localPoint.x = localDisplayMetrics.widthPixels;
        localPoint.y = localDisplayMetrics.heightPixels;
        return localPoint;
    }

    /**
     * 获取手机上的第一个activity
     */
    public static ComponentName getFirstActivityOnMobile(Context paramContext)
    {
        ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService(Context.ACTIVITY_SERVICE);
        try
        {
            List localList = localActivityManager.getRunningTasks(1);
            if (localList == null || localList.get(0) == null) return null;
            ComponentName localComponentName = ((ActivityManager.RunningTaskInfo) localList.get(0)).topActivity;
            return localComponentName;
        }
        catch (Exception localException)
        {
            Log.i("AndroidUtils", Log.getStackTraceString(localException));
            return null;
        }
    }

    /**
     * 获取手机imei
     */
    public static String getImei()
    {
        String str;
        try
        {
            str = ((TelephonyManager) BaseApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            return str;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return "";
    }

    /**
     * 获取meta值
     */
    public static String getMetaValue(Context paramContext, String paramString)
    {
        if ((paramContext == null) || (paramString == null))
            return null;
        Bundle localBundle;
        try
        {
            PackageManager packageManager = paramContext.getPackageManager();
            ApplicationInfo localApplicationInfo = packageManager.getApplicationInfo(paramContext.getPackageName(),
                    PackageManager.GET_META_DATA);
            localBundle = localApplicationInfo.metaData;
            String str = localBundle.getString(paramString);
            return str;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
            Log.i("AndroidUtils", Log.getStackTraceString(localNameNotFoundException));
            return null;
        }
    }

    /**
     * 获取网络类型
     * TODO
     */
    public static String getNetworkType(Context paramContext)
    {
        if (paramContext == null)
            return "";
        NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if ((localNetworkInfo == null) || (!(localNetworkInfo.isConnected())))
            return "网络未连接";
//            return paramContext.getString(); // TODO
        int type = localNetworkInfo.getType();
        int subtype = localNetworkInfo.getSubtype();
        if (type == ConnectivityManager.TYPE_WIFI)
            return "wifi网络";
//            return paramContext.getString(  ); // TODO
        if ((subtype == TelephonyManager.NETWORK_TYPE_GPRS) || (subtype == TelephonyManager.NETWORK_TYPE_EDGE))
            return getOperator(paramContext) + "2G";
        return getOperator(paramContext) + "3G";
    }

    /**
     * 获取SVN版本名称
     * TODO
     */
    public static String getSVNVersionName()
    {
//        return "43290";//TODO
        return "";
    }

    /**
     * 获取顶部activity名称
     */
    public static String getTopActivityName(Context paramContext)
    {
        List localList = ((ActivityManager)paramContext.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
        if ((localList != null) && (!(localList.isEmpty())) && (localList.get(0) != null))
            return ((ActivityManager.RunningTaskInfo)localList.get(0)).topActivity.getClassName();
        return null;
    }

    /**
     * 获取版本名称
     */
    public static String getVersionName(Context paramContext) {
        PackageManager localPackageManager = paramContext.getPackageManager();
        try {
            PackageInfo localPackageInfo = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0);
            if (localPackageInfo == null) return null;
            String str = localPackageInfo.versionName;
            return str;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            Log.i("getPackageInfo", Log.getStackTraceString(localNameNotFoundException));
            return null;
        } catch (RuntimeException localRuntimeException) {
            Log.i("getPackageInfo", Log.getStackTraceString(localRuntimeException));
        }
        return null;
    }

    /**
     * 安装apk
     */
    public static void installApk(Context paramContext, File paramFile)
    {
        if (!(paramFile.exists()))
        {
            System.out.println("apk file not found");
            return;
        }
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setDataAndType(Uri.fromFile(paramFile), "application/vnd.android.package-archive");
        paramContext.startActivity(localIntent);
    }

    /**
     * 是否带到后台
     */
    public static boolean isApplicationBroughtToBackground(Context paramContext)
    {
        ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (localActivityManager != null)
            try
            {
                List localList = localActivityManager.getRunningTasks(1);
                if ((localList == null) || (localList.isEmpty()) || (localList.get(0) == null))
                    return false;
                boolean bool = ((ActivityManager.RunningTaskInfo)localList.get(0)).topActivity.getPackageName().equals(paramContext.getPackageName());
                if (bool)
                    return false;
                return true;
            }
            catch (SecurityException localSecurityException)
            {
                Log.i("AndroidUtils", Log.getStackTraceString(localSecurityException));
            }
        return false;
    }

    /**
     * APP是否处于后台
     */
    public static boolean isBackground(Context paramContext)
    {
        List localList = ((ActivityManager)paramContext.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
        if (localList != null)
        {
            ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo;
            Iterator localIterator = localList.iterator();
            while (localIterator.hasNext()){
                localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
                if(localRunningAppProcessInfo != null
                        && localRunningAppProcessInfo.processName.equals(paramContext.getPackageName()) ){
                    if (localRunningAppProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND)
                    {
                        Log.i(String.format("Background App:", new Object[0]), localRunningAppProcessInfo.processName);
                        return true;
                    }else{
                        Log.i(String.format("Foreground App:", new Object[0]), localRunningAppProcessInfo.processName);
                    }
                }
            }
        }
        return false;
    }

    /**
     * 处于后台或者是service
     */
    public static boolean isBackgroundOrService(Context paramContext)
    {
        List localList = ((ActivityManager)paramContext.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
        if (localList != null) {
            ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo;
            Iterator localIterator = localList.iterator();
            while (localIterator.hasNext()) {
                localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo) localIterator.next();
                if (localRunningAppProcessInfo.processName.equals(paramContext.getPackageName())) {

                    if (localRunningAppProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND
                            || localRunningAppProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE) {
                        Log.i(String.format("Background App:", new Object[0]), localRunningAppProcessInfo.processName);
                        return true;
                    } else {
                        Log.i(String.format("Foreground App:", new Object[0]), localRunningAppProcessInfo.processName);
                    }
                }
            }
        }
        return false;
    }

    /**
     * GPS是否开启
     */
    public static boolean isGpsEnable(Context paramContext)
    {
        boolean bool;
        try
        {
            bool = ((LocationManager)paramContext.getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);
            return bool;
        }
        catch (SecurityException localSecurityException)
        {
        }
        return false;
    }

    /**
     * 在UI线程运行
     */
    public static void runUiThread(Activity paramActivity, Runnable paramRunnable)
    {
        if ((paramRunnable == null) || (paramActivity == null) || (paramActivity.isFinishing()))
            return;
        Looper localLooper = paramActivity.getMainLooper();
        if (Thread.currentThread().getId() == localLooper.getThread().getId())
        {
            paramRunnable.run();
            return;
        }
        new Handler(localLooper).post(paramRunnable);
    }

    /**
     * 获取App包 信息版本号
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }


}
