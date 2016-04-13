package cn.ibona.t1.common.control.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 网络工具类
 * @author 吴志群
 */
public class NetworkUtil {

	static String TAG = NetworkUtil.class.getSimpleName();
	
	/**
	 *  是否存在网络连接
	 */
	public static boolean isNetworkConnected(Context context) { 
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {  
	            return mNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	/**
	 * WIFI是否连接
	 */
	public static boolean isWifiConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mWiFiNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
	        if (mWiFiNetworkInfo != null) {  
	            return mWiFiNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	/**
	 * 手机网络是否连接
	 */
	public static boolean isMobileConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mMobileNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
	        if (mMobileNetworkInfo != null) {  
	            return mMobileNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	/**
	 * 当前网络类型
	 */
	public static int getConnectedType(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {  
	            return mNetworkInfo.getType();  
	        }  
	    }  
	    return -1;  
	}
	
    /**
     * 在URL后连接参数，主要是GET方式时使用
     */
    public static String connectParams(String url, Map<String, String> map){
        String realurl = url;

        if(map==null) return realurl;

        StringBuffer param = new StringBuffer();
        int i = 0;
        String para;
        for (String key : map.keySet()) {
            if(map.get(key)==null) continue;
            if (i==0 && realurl.indexOf('?')==-1)
                param.append("?");
            else
                param.append("&");

//            param.append(key).append("=").append(map.get(key));
            try {
                para = URLEncoder.encode(map.get(key), "utf-8");
                param.append(key).append("=").append(para);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }
        realurl += param;
        Log.i(TAG, "路径: " + realurl);
        return realurl;
    }
}


