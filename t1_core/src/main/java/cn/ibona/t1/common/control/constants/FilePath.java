package cn.ibona.t1.common.control.constants;

import android.os.Environment;

import java.io.File;

import cn.ibona.t1.common.BaseApplication;

/**
 * Created by qun on 15/11/27.
 * 路径相关类
 */
public class FilePath {
    public static final String APK_PATH;
    public static final String CACHE_APK;
    public static final String CACHE_ATTACHMENT;
    public static final String CACHE_ERROR_LOG;
    public static final String CACHE_IMAGE;
    public static final String CACHE_ROOT_PATH;
    public static final String CACHE_ROOT_PATH_OLD = Environment.getExternalStorageDirectory() + File.separator + "sangformoa" + File.separator;
    public static final String CAMERA_PHOTO_PATH;
    public static final String IMAGE_CACHE_DIR = "thumbs";
    public static final String IMAGE_COMPRESSION_DIR = "compression";
    public static final String IMAGE_DIS_CACHE_DIR = "http";
    public static final String IMAGE_ORIGINAL_DIS_CACHE_DIR = "http_original";
    public static final String INTERNAL_CACHE_PATH;
    public static final String LOGCAT_FILE;
    public static final String LOG_DIR;
    public static final String MESSAGELIST_CACHE;
    public static final String NDK_LOG_DIR;
    public static String PRIVATE_ERROR_LOG_PATH;
    public static String PRIVATE_TEMP_ERROR_LOG_PATH;
    public static final String PUSH_DIR;
    public static String VOICE_FILE_PATH;
    public static final String WORKATTENDANCE_LOG_DIR;

    static
    {
        CACHE_ROOT_PATH = Environment.getExternalStorageDirectory() + File.separator + "t1" + File.separator;
        CACHE_IMAGE = CACHE_ROOT_PATH + "images";
        CACHE_ERROR_LOG = CACHE_ROOT_PATH + "errorlog";
        CACHE_ATTACHMENT = CACHE_ROOT_PATH + "attachment";
        CACHE_APK = CACHE_ROOT_PATH + "apk";
        MESSAGELIST_CACHE = CACHE_ROOT_PATH + "message.dat";
        LOG_DIR = CACHE_ROOT_PATH + "log";
        NDK_LOG_DIR = LOG_DIR + "/ndk.txt";
        WORKATTENDANCE_LOG_DIR = CACHE_ROOT_PATH + "workattendance";
        PUSH_DIR = CACHE_ROOT_PATH + "push";
        APK_PATH = CACHE_ROOT_PATH + "apk/t1.apk";
        LOGCAT_FILE = LOG_DIR + "/logcat.txt";
        CAMERA_PHOTO_PATH = CACHE_ROOT_PATH + "picture";
        INTERNAL_CACHE_PATH = "/data/data/" + BaseApplication.getInstance().getPackageName() + "/cache";
        PRIVATE_ERROR_LOG_PATH = "crashlog";
        PRIVATE_TEMP_ERROR_LOG_PATH = "temp_crashlog";
        VOICE_FILE_PATH = CACHE_ROOT_PATH + "voice";
    }

    /*public static final String getMessageListCache()
    {
        String str = "";
        Contact localContact1 = BaseApplication.getInstance().getLoginContact();
        if (localContact1 != null)
            str = String.valueOf(localContact1.getId());
        Contact localContact2 = BaseApplication.getInstance().getLoginContact();
        int i = 0;
        if (localContact2 != null)
            i = BaseApplication.getInstance().getLoginContact().getId();
        return MESSAGELIST_CACHE + MD5.getMd5(new StringBuilder().append("message.dat").append(i).append(str).toString());
    }*/

    public static final String getShareIcoDir()
    {
        File localFile;
        try
        {
            localFile = new File(CACHE_ROOT_PATH + "share");
            if (!(localFile.exists()))
                localFile.mkdirs();
            String str = localFile.getAbsolutePath();
            return str;
        }
        catch (Exception localException)
        {
        }
        return "";
    }
}
