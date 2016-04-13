package cn.ibona.t1.common.model.net.image;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import cn.ibona.t1.common.control.utils.SDKVersionUtils;

public class BitmapLruCache implements ImageCache {

    private static final String TAG = BitmapLruCache.class.getSimpleName();

    // Default memory cache size in kilobytes
    private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 5; // 5MB

    private LruCache<String, Bitmap> mCache;
    int memCacheSize = DEFAULT_MEM_CACHE_SIZE;

    private Set<SoftReference<Bitmap>> mReusableBitmaps;

    public BitmapLruCache() {

        setMemCacheSizePercent(0.25f);// Set memory cache to 25% of app memory

        if (SDKVersionUtils.hasHoneycomb()) {
            mReusableBitmaps =
                    Collections.synchronizedSet(new HashSet<SoftReference<Bitmap>>());
        }

        mCache = new LruCache<String, Bitmap>(memCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                final int bitmapSize = getBitmapSize(value) / 1024;
                return bitmapSize == 0 ? 1 : bitmapSize;

            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                // The removed entry is a standard BitmapDrawable
                if (SDKVersionUtils.hasHoneycomb()){
                    // We're running on Honeycomb or later, so add the bitmap
                    // to a SoftReference set for possible use with inBitmap later
                    mReusableBitmaps.add(new SoftReference<Bitmap>(oldValue));
                }
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static int getBitmapSize(Bitmap value) {
        Bitmap bitmap = value;

        // From KitKat onward use getAllocationByteCount() as allocated bytes can potentially be
        // larger than bitmap byte count.
        if (SDKVersionUtils.hasKitKat()) {
            return bitmap.getAllocationByteCount();
        }

        if (SDKVersionUtils.hasHoneycombMR1()) {
            return bitmap.getByteCount();
        }

        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * Sets the memory cache size based on a percentage of the max available VM memory.
     * Eg. setting percent to 0.2 would set the memory cache to one fifth of the available
     * memory. Throws {@link IllegalArgumentException} if percent is < 0.01 or > .8.
     * memCacheSize is stored in kilobytes instead of bytes as this will eventually be passed
     * to construct a LruCache which takes an int in its constructor.
     *
     * This value should be chosen carefully based on a number of factors
     * Refer to the corresponding Android Training class for more discussion:
     * http://developer.android.com/training/displaying-bitmaps/
     *
     * @param percent Percent of available app memory to use to size memory cache
     */
    private void setMemCacheSizePercent(float percent) {
        if (percent < 0.01f || percent > 0.8f) {
            throw new IllegalArgumentException("setMemCacheSizePercent - percent must be "
                    + "between 0.01 and 0.8 (inclusive)");
        }
        memCacheSize = Math.round(percent * Runtime.getRuntime().maxMemory() / 1024);
    }
}
