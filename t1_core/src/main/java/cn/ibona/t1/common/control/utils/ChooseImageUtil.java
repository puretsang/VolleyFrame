package cn.ibona.t1.common.control.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by qun on 15/11/13.
 */
public class ChooseImageUtil {
    public final static int REQUEST_CODE_Gallery = 100;//图库返回
    public final static int REQUEST_CODE_CAMERA = 200;//相机返回
    public final static int PHOTO_RESULT_CODE = 300;//相机返回
    public final static String FILE_PATH_CACHE = "FILE_PATH_CACHE";

    Context context;

    /**
     * 图片选择帮助类，处理图片选择和返回数据处理
     * 会使用掉两个REQUEST_CODE
     * {@link ChooseImageUtil#REQUEST_CODE_CAMERA}，{@link ChooseImageUtil#REQUEST_CODE_Gallery}
     */
    public ChooseImageUtil(Context context) {
        this.context = context;
    }

    /**
     * 从相机选择图片
     * @see #onActivityResult(int, int, Intent)
     */

    public void chooseFromCamera(Fragment fragment) {
        Intent intent = getChooseFromCameraIntent();
        fragment.startActivityForResult(intent, REQUEST_CODE_CAMERA);

    }

    File file;
    private Intent getChooseFromCameraIntent() {
        file =  setImageFile();
        return IntentUtil.buildChooseImageFromCameraIntent(file);
    }
/** 拍照的相片不能 context.getFilesDir().getAbsolutePath() 是错误的
 * 要用这个 Environment.getExternalStorageDirectory().getAbsolutePath()
 * */
    private File setImageFile(){
        String dir = context.getFilesDir().getAbsolutePath();//  /data/data/cn.ibona.t1/files
        File filePath =  new File(dir, System.currentTimeMillis()+".jpg");
        return filePath;
    }


    /**
     * 从图库中选择图片
     */
    public void chooseFromGallery(Fragment fragment) {
        Intent intent = getChooseFromGalleryIntent();
        fragment.startActivityForResult(intent, REQUEST_CODE_Gallery);
    }

    /**
     * 从图库中选择图片
     */
    public void chooseFromGallery(Activity activity) {
        Intent intent = getChooseFromGalleryIntent();
        activity.startActivityForResult(intent, REQUEST_CODE_Gallery);
    }

    private Intent getChooseFromGalleryIntent() {
        return IntentUtil.buildChooseImageFromGalleryIntent();
    }

    /**
     * 处理请求后的返回数据
     * return 文件路径
     */
    public String onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_Gallery) {
            String filepath = getFilePathFromGalleyResult(data);
            return filepath;
        } else if (requestCode == REQUEST_CODE_CAMERA) {
            String filepath = getFilePathFromPhotoResult();
            return filepath;
        }
        return null;
    }

    private String getFilePathFromPhotoResult() {
        //判断方法待斟酌。。。
        //除以1024是个浮点型，再加1转换为int整形，data为空只好根据文件大小判断是否有拍照
        try {
            if (((file.length() / 1024) + 1) > 10) {
                String filepath = file.getPath();
                return filepath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFilePathFromGalleyResult(Intent data) {
        if (data != null) {
            String filepath;
            if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT){
                filepath = ImageUri.getPath(context, data.getData());
            } else {
                filepath = ImageUri.selectImage(context, data);
            }
            return filepath;
        }
        return null;
    }

}
