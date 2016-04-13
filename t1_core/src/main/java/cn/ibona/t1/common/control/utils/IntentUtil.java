package cn.ibona.t1.common.control.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * 用于创建各种intent
 * Created by qun on 15/11/2.
 */
public class IntentUtil {

    /**
     * 获得返回应用主页面的Intent，同时关闭其他所有页面。
     * 主页面也就是应用开启的第一个页面。
     * @return 用于跳转的Intent
     * @deprecated
     */
    public static Intent buildGoHomeIntent(Context context){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return  intent;
    }

    /**
     * 创建相机选择图片的Intent,使用context.getFilesDir()作为路径
     */
    public static Intent buildChooseImageFromCameraIntent(Context context) {
      File file =  setImageFile(context);
        return buildChooseImageFromCameraIntent(file);
    }

    private static File setImageFile(Context context){
        String dir = context.getFilesDir().getAbsolutePath();
        File file =  new File(dir, System.currentTimeMillis()+".jpg");
        return file;
    }
    /**
     * 创建相机选择图片的Intent
     * @param saveFile 图片保存的文件索引
     * @return 一个跳转到系统相机，获取图片的Intent
     */
    public static Intent buildChooseImageFromCameraIntent(File saveFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveFile));
        return intent;
    }

    /**
     * 创建从图库中选择图片的Intent
     * @return 一个跳转到系统画廊，获取图片的Intent
     */
    public static Intent buildChooseImageFromGalleryIntent() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        return intent;
    }

    /**
     * 创建一个跳转到照相机拍照获取图片的Intent，文件路径为context.getFilesDir()
     * @return 一个跳转到照相机拍照获取图片的Intent
     *
     * @deprecated  use {@link #buildChooseImageFromCameraIntent(Context)}
     */
    @Deprecated
    public static Intent chooseFromCameraIntent(Context context){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File fileUri =setImageFile(context);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        return intent;
    }

    /**
     * 判断提供action，能被正常执行，有符合条件的接收者。
     * @param context 使用application context
     * @return
     */
    public static boolean isIntentAvailable(Context context, String action) {
        final Intent intent = new Intent(action);
        return isIntentAvailable(context, intent);
    }

    /**
     * 判断提供的Intent是否有可用，能被正常执行，有符合条件的接收者。
     * @param context 使用application context
     * @return
     */
    private static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
