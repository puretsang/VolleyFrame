package cn.ibona.t1.common.model.net.image;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;

import java.util.HashMap;

import cn.ibona.t1.common.model.net.request.RequestQueueProvider;

/**
 *
 */
public class ImageLoaderProvider {

    private static HashMap<Integer, ImageLoader> mLoaders = new HashMap<>();
    private static int DEFAULT_IMG_LOADER_KEY = 0;

    public static ImageLoader getDefImageLoader(Context context) {
        ImageLoader ret = mLoaders.get(DEFAULT_IMG_LOADER_KEY);
        if(ret==null){
            ret = new ImageLoader(RequestQueueProvider.getDefRequestQueue(context),
                    ImageCacheProvider.getDefCache());
            mLoaders.put(DEFAULT_IMG_LOADER_KEY, ret);
        }
        return ret;
    }
}
