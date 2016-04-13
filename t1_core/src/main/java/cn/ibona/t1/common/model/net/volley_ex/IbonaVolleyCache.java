package cn.ibona.t1.common.model.net.volley_ex;

import com.android.volley.toolbox.DiskBasedCache;

import java.io.File;

/**
 * Created by busylee on 2015/11/21.
 */
public class IbonaVolleyCache extends DiskBasedCache {
    private boolean expired_flag = false;
    private boolean refresh_flag =false;

    public IbonaVolleyCache(File rootDirectory,boolean expired_flag,boolean refresh_flag) {
        super(rootDirectory, 5242880);
        this.expired_flag = expired_flag;
        this.refresh_flag =refresh_flag;
    }
    public IbonaVolleyCache(File rootDirectory, int maxCacheSizeInBytes,boolean expired_flag,boolean refresh_flag) {
        super(rootDirectory, maxCacheSizeInBytes);
        this.expired_flag = expired_flag;
        this.refresh_flag =refresh_flag;
    }

    public synchronized void put(String key, Entry entry) {
        Entry entry1 = new Entry(){
            @Override
            public boolean isExpired() {

                return expired_flag;
            }

            @Override
            public boolean refreshNeeded() {
                return refresh_flag;
            }
        };
        entry1.data = entry.data;
        entry1.etag = entry.etag;
        entry1.lastModified = entry.lastModified;
        entry1.responseHeaders=entry.responseHeaders;
        entry1.serverDate = entry.serverDate;
        entry1.ttl = entry.ttl;
        entry1.softTtl = entry.softTtl;
        super.put(key,entry1);
    }

    public synchronized Entry get(String key) {
        Entry entry = super.get(key);
        if (entry==null)
            return null;
        Entry entry1 = new Entry(){
            @Override
            public boolean isExpired() {

                return expired_flag;
            }

            @Override
            public boolean refreshNeeded() {
                return refresh_flag;
            }
        };
        entry1.data = entry.data;
        entry1.etag = entry.etag;
        entry1.lastModified = entry.lastModified;
        entry1.responseHeaders=entry.responseHeaders;
        entry1.serverDate = entry.serverDate;
        entry1.ttl = entry.ttl;
        entry1.softTtl = entry.softTtl;
        return entry1;
    }


}
