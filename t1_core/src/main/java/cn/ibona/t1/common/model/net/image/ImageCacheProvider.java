package cn.ibona.t1.common.model.net.image;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import java.util.HashMap;

/**
 *
 */
public class ImageCacheProvider {

    private static HashMap<Integer, ImageCache> mCaches = new HashMap<>();
    private static int DEFAUTLE_IMG_CAHCE_KEY = 0;

    public static ImageCache getDefCache(){
        ImageCache ret = mCaches.get(DEFAUTLE_IMG_CAHCE_KEY);
        if(ret==null){
            ret = new BitmapLruCache();
            mCaches.put(DEFAUTLE_IMG_CAHCE_KEY, ret);
        }
        return ret;
    }
}
