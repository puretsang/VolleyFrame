package cn.ibona.t1.common.model.net.volley_ex;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.File;

/**
 * Created by busylee on 2015/11/21.
 */
public class IbonaVolley extends Volley {

    public static RequestQueue newRequestQueue(Context context, HttpStack stack, int maxDiskCacheBytes ,boolean exflag,boolean reFlag) {
        File cacheDir = new File(context.getCacheDir(), "volley");
        String userAgent = "volley/0";

        try {
            String network = context.getPackageName();
            PackageInfo queue = context.getPackageManager().getPackageInfo(network, 0);
            userAgent = network + "/" + queue.versionCode;
        } catch (PackageManager.NameNotFoundException var7) {
            ;
        }

        if(stack == null) {
            if(Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else {
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }

        BasicNetwork network1 = new BasicNetwork((HttpStack)stack);
        RequestQueue queue1;
        if(maxDiskCacheBytes <= -1) {
            queue1 = new RequestQueue(new IbonaVolleyCache(cacheDir,exflag,reFlag), network1);
        } else {
            queue1 = new RequestQueue(new IbonaVolleyCache(cacheDir, maxDiskCacheBytes,exflag,reFlag), network1);
        }

        queue1.start();
        return queue1;
    }


    public static RequestQueue newRequestQueue(Context context, HttpStack stack, int maxDiskCacheBytes) {
       return newRequestQueue(context, stack, maxDiskCacheBytes, false,false);
    }
}
