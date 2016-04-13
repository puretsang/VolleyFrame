package cn.ibona.t1.common.control.utils;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by qun on 15/9/23.
 */
public class BitmapUtils {



    @Deprecated
    public static int DEFAULT_BITMAP_SIZE = 100;//压缩标准尺寸

    public static int DEFAULT_BITMAP_WIDTH = 1080;//默认位图最大宽度
    public static int DEFAULT_COMPRESS_QUALITY = 20;//默认压缩质量


    /**==================== 获取 =================================*/

    /**
     * 根据宽度计算缩放比
     * @param options
     * @param reqWidth
     * @return
     */
    public static int calculateInSampleSizeByWidth(BitmapFactory.Options options,
                                            int reqWidth) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        inSampleSize = width/reqWidth;
        LogUtil.i("width=" + width + "reqWidth=" + reqWidth + "计算后的缩放比inSampleSize=" + inSampleSize);
        return inSampleSize;
    }

    /**
     * 根据原图Options 和 目标长宽，计算图片的缩放值
     * 如果图片的原始高度或者宽度大与我们期望的宽度和高度，我们需要计算出缩放比例的数值。否则就不缩放。

     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // BEGIN_INCLUDE (calculate_sample_size)
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            //屏蔽原因，压缩压太过了，出现模糊
            /**long totalPixels = width * height / inSampleSize;

            // Anything more than 2x the requested pixels we'll sample down further
            final long totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels > totalReqPixelsCap) {
                inSampleSize *= 2;
                totalPixels /= 2;
            }*/
        }
        LogUtil.i("width=" + width + "reqWidth=" + reqWidth + "计算后的缩放比inSampleSize=" + inSampleSize);
        return inSampleSize;
        // END_INCLUDE (calculate_sample_size)
    }


    /**
     * 从Resourec中加载图片
     * @param res 资源对象
     * @param resId  资源id
     * @param reqWidth 请求的宽度
     * @param reqHeight 请求的高度
     * @return 得到的图片
     */
    public static Bitmap decodeBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // BEGIN_INCLUDE (read_bitmap_dimensions)
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // END_INCLUDE (read_bitmap_dimensions)


        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 从路径中记载图片
     * @param filePath 图片完整路径
     * @param reqWidth 请求的宽度
     * @param reqHeight 请求的高度
     * @return 获得的图片
     */
    public static Bitmap decodeBitmapFromPath(String filePath, int reqWidth, int reqHeight) {

        // BEGIN_INCLUDE (read_bitmap_dimensions)
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // END_INCLUDE (read_bitmap_dimensions)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 从路径中记载图片
     * @param filePath 图片完整路径
     * @param reqWidth 请求的宽度
     * @return 获得的图片
     */
    public static Bitmap decodeBitmapFromPathByWidth(String filePath, int reqWidth) {

        // BEGIN_INCLUDE (read_bitmap_dimensions)
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSizeByWidth(options, reqWidth);
        // END_INCLUDE (read_bitmap_dimensions)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 从路径中记载图片
     * @param filePath 图片完整路径
     * @return 获得的图片
     */
    public static Bitmap decodeBitmapFromPath(String filePath){
        return decodeBitmapFromPathByWidth(filePath, DEFAULT_BITMAP_WIDTH);
    }

    /**
     * 从描述符号中获取图片
     * @param fileDescriptor 文件描述符
     * @param reqWidth 请求的宽度
     * @param reqHeight 请求的高度
     * @return
     */
    public static Bitmap decodeBitmapFromDescriptor(
            FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    /**====================获取图片，自动处理旋转问题 =================================*/
    /**
     * 从路径中解析图片并自动旋转
     */
    private static Bitmap decodeBitmapFromPathAutoRotate(String imagePath) {
        int rotateAngleRaw = getImageRotateAngleRaw(imagePath);
        Bitmap bitmap = BitmapUtils.decodeBitmapFromPath(imagePath);
        return rotateBitmap(bitmap, rotateAngleRaw, false);
    }

    /**
     * 从路径中获取图片的旋转信息，并旋转提供的位图
     */
    private static Bitmap decodeBitmapFromPathAutoRotate(String imagePath, Bitmap bitmap, boolean recycleOld) {
        int rotateAngleRaw = getImageRotateAngleRaw(imagePath);
        return rotateBitmap(bitmap, rotateAngleRaw, recycleOld);
    }

    /**
     * 处理图片旋转。。。
     * @param image 需要旋转的图片
     * @param rotateAngleRaw 旋转的真实角度角度<br/>
     * {@link ExifInterface#ORIENTATION_ROTATE_90 }<br/>
     * {@link ExifInterface#ORIENTATION_ROTATE_180 }<br/>
     * {@link ExifInterface#ORIENTATION_ROTATE_270 }<br/>
     */
    public static Bitmap rotateBitmap(Bitmap image, int rotateAngleRaw, boolean recycleOld) {

        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postRotate(rotateAngleRaw);
        Bitmap ret  = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);
        if(recycleOld) image.recycle();//如果需要，回收旧图
        return ret;
    }

    /**
     * 获取图片真实旋转角度
     */
    public static int getImageRotateAngleRaw(String filepath){
        int angleExif = getImageRotateAngleExif(filepath);
        return rotateAngleExif2Raw(angleExif);
    }

    /**
     * 将Exif旋转角度转化为真实旋转角度
     * @param rotateAngleExif 图片信息中得方向标志
     * @return 真实旋转角度
     *
     *
     * @see #getImageRotateAngleRaw(String)
     *
     * @see ExifInterface#ORIENTATION_ROTATE_90
     * @see ExifInterface#ORIENTATION_ROTATE_180
     * @see ExifInterface#ORIENTATION_ROTATE_270
     */
    public static int rotateAngleExif2Raw(int rotateAngleExif){
        int rotateAngleRaw = 0;
        if (rotateAngleExif == ExifInterface.ORIENTATION_ROTATE_90) {
            rotateAngleRaw= 90;
        } else if (rotateAngleExif == ExifInterface.ORIENTATION_ROTATE_180) {
            rotateAngleRaw = 180;
        } else if (rotateAngleExif == ExifInterface.ORIENTATION_ROTATE_270) {
            rotateAngleRaw = 270;
        }
        return rotateAngleRaw;
    }

    /**
     * 获取图片旋转度,角度以ExifInterface常量表示。
     * Reads Exif tags from the specified JPEG file.
     * @param filepath 图片路径
     * @return 图片的旋转角度的Exif值， 如果获取不到，默认返回{@link ExifInterface#ORIENTATION_NORMAL}
     *
     * @see #getImageRotateAngleRaw(String)
     * @see ExifInterface#ORIENTATION_ROTATE_90
     * @see ExifInterface#ORIENTATION_ROTATE_180
     * @see ExifInterface#ORIENTATION_ROTATE_270
     */
    public static int getImageRotateAngleExif(String filepath){
        ExifInterface exifInterface= null;
        try {
            exifInterface = new ExifInterface(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            return ExifInterface.ORIENTATION_NORMAL;
        }
        //获取图片的旋转角度
        return exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
    }

    /**==================== 压缩 =================================*/

    /**
     * 压缩图片，根据目标值压缩。
     * @deprecated 无法达到预期效果，压缩后的图片再进行保存，比预想的要大很多。
     * @see #compressAndSave(Bitmap, String, int)
     */
    @Deprecated
    public static Bitmap compress(Bitmap src, int startQuality, int tagSize) {

        ByteArrayOutputStream outStrean = new ByteArrayOutputStream();
        int quality = startQuality;
        src.compress(Bitmap.CompressFormat.JPEG, quality, outStrean);//质量压缩方法，这里100表示不压缩
        try {
            outStrean.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while ( outStrean.size() > tagSize * 1024) {  //循环判断如果压缩后图片是否大于默认尺寸,大于继续压缩

            quality = quality>30 ? quality-20 : 10;//大于30的时候 -30， 直接改为10
            outStrean.reset();//重置baos即清空baos
            src.compress(Bitmap.CompressFormat.JPEG, quality, outStrean);
            try {
                outStrean.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //如果小于等于0，则直接退出
            if(quality<=10) break;
        }

        ByteArrayInputStream inStream = new ByteArrayInputStream(outStrean.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(inStream, null, null);//把ByteArrayInputStream数据生成图片
        LogUtil.i( "压缩后的图片尺寸="+  (outStrean.size() / 1024) + "K"  + " quality =" +quality );
        return bitmap;
    }

    /**
     * 压缩图片，根据压缩参数直接压缩
     * @deprecated 无法达到预期效果，压缩后的图片再进行保存，比预想的要大很多。
     * @see #compressAndSave(Bitmap, String, int)
     */
    public static Bitmap compress(Bitmap image, int quality) {

        ByteArrayOutputStream outStrean = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, quality, outStrean);//质量压缩方法
        ByteArrayInputStream inStream = new ByteArrayInputStream(outStrean.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(inStream, null, null);//把ByteArrayInputStream数据生成图片
        LogUtil.i( "压缩后的图片尺寸="+  (outStrean.size() / 1024) + "K"  + " quality =" +quality );
        return bitmap;
    }

    /**
     * 压缩图片，目标压缩值为{@link #DEFAULT_BITMAP_SIZE} ，单位k
     * @deprecated 无法达到预期效果，压缩后的图片再进行保存，比预想的要大很多。
     * @see #compressAndSave(Bitmap, String)
     */
    @Deprecated
    public static Bitmap compress(Bitmap bitmap) {
        return compress(bitmap, 100, DEFAULT_BITMAP_SIZE);
    }

    /**==================== 保存 =================================*/

    /**
     * 保存位图,同时根据质量指数进行压缩
     */
    public static boolean saveBitmap(Bitmap bitmap, File file, int quality) {
        if (bitmap == null) return false;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 保存位图,同时根据质量指数进行压缩
     */
    public static boolean saveBitmap(Bitmap bitmap, String absPath,  int quality) {
        return saveBitmap(bitmap, new File(absPath), quality);
    }

    /**
     * 保存位图
     */
    public static boolean saveBitmap(Bitmap bitmap, File file) {
        return saveBitmap(bitmap, file, 100);
    }

    /**
     * 保存位图
     */
    public static boolean saveBitmap(Bitmap bitmap, String absPath) {
        return saveBitmap(bitmap, new File(absPath));
    }

    /**
     * 根据默认的质量指数进行保存
     */
    public static boolean saveBitmapByDefaultQuality(Bitmap bm, String tagPath) {
        return saveBitmap(bm, tagPath, DEFAULT_COMPRESS_QUALITY);
    }


    /**==================== 压缩并保存 =================================*/



    /**
     * 压缩图片，并保存到指定路径。使用默认质量指数。
     *
     */
    public static Bitmap compressAndSave(String srcPath, String  tagPath) {

        Bitmap bm = decodeBitmapFromPath(srcPath);
        return compressAndSave(bm, tagPath);
    }

    /**
     * 压缩图片，并保存到指定路径
     */
    public static Bitmap compressAndSave(String srcPath, String  tagPath, int quality) {
        Bitmap bm = BitmapUtils.decodeBitmapFromPath(srcPath);
        return compressAndSave(bm, tagPath, quality);
    }

    /**
     * 压缩图片同时自动旋转到正确的角度，并保存到指定路径。使用默认质量指数。
     */
    public static Bitmap compressAndSaveAutoRotate(String srcPath, String  tagPath) {
        Bitmap bm = BitmapUtils.decodeBitmapFromPathAutoRotate(srcPath);
        return compressAndSave(bm, tagPath);
    }

    /**
     * 压缩图片同时自动旋转到正确的角度，并保存到指定路径。
     */
    public static Bitmap compressAndSaveAutoRotate(String srcPath, String  tagPath, int quality) {
        Bitmap bm = BitmapUtils.decodeBitmapFromPathAutoRotate(srcPath);
        return compressAndSave(bm, tagPath, quality);
    }

    /**
     * 压缩图片，并保存到指定路径。使用默认质量指数。
     */
    @Nullable
    public static Bitmap compressAndSave(Bitmap bm, String tagPath) {
        boolean succ = saveBitmapByDefaultQuality(bm, tagPath);
        if(!succ)  {
            LogUtil.e("压缩保存失败");
            return null;
        }
        LogUtil.e("压缩后的文件大小" + "bitmap byte count = " + bm.getByteCount() / 1024 + "K ,file Length=" + new File(tagPath).length() / 1024 + "K");
        bm = decodeBitmapFromPath(tagPath);
        return bm;
    }

    /**
     * 压缩图片，并保存到指定路径。
     */
    @Nullable
    public static Bitmap compressAndSave(Bitmap bm, String tagPath, int quality) {
        boolean succ = saveBitmap(bm, tagPath, quality);
        if (!succ) {
            LogUtil.e("压缩保存失败");
            return null;
        }
        LogUtil.e("压缩后的文件大小" + "bitmap byte count = " + bm.getByteCount() / 1024 + "K ,file Length=" + new File(tagPath).length() / 1024 + "K");
        bm = decodeBitmapFromPath(tagPath);
        return bm;
    }
    /**==================== 转换 =================================*/

    /**
     * convert Bitmap to byte array
     */
    public static byte[] bitmapToByte(Bitmap b) {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, o);
        return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(bitmap);
    }

    /**
     * 把bitmap转换成Base64编码String
     */
    public static String bitmapToString(Bitmap bitmap) {
        return Base64.encodeToString(bitmapToByte(bitmap), Base64.DEFAULT);
    }

    /**==================== 操作 =================================*/

    /**
     * 创建位图缩略
     * @param bitMap  原图
     * @param needRecycle 需要回收原图
     * @return
     */
    public static Bitmap createBitmapThumbnail(Bitmap bitMap, boolean needRecycle) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = 120;
        int newHeight = 120;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,
                matrix, true);
        if (needRecycle) bitMap.recycle();
        return newBitMap;
    }

    /**
     * scale image
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight, boolean recycleOld) {
        return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight(), recycleOld);
    }

    /**
     * scale image
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight, boolean recycleOld) {
        if (org == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap ret = Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
        if(recycleOld) org.recycle();//如果需要，回收旧图
        return ret;
    }

    /**
     * 转为圆角
     * @param bitmap
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, boolean recycleOld) {
        int height = bitmap.getHeight();
        int width = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        //paint.setColor(Color.TRANSPARENT);
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        if(recycleOld) bitmap.recycle();
        return output;
    }



    /**==================== 创建相关Intent =================================*/

    /**
     * 创建从手机相册获取图片的Intent，并进行切割
     * @param saveTo 保存地址
     * @param aspectX
     * @param aspectY
     * @param outputX
     * @param outputY
     * @param returnData
     * @return
     */
    public static Intent buildGalleryPickIntent(Uri saveTo, int aspectX, int aspectY,
                                                int outputX, int outputY, boolean returnData) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", saveTo);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", returnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return intent;
    }

    /**
     * 从照相机中获取图片，并切割
     * @param uriFrom
     * @param uriTo
     * @param aspectX
     * @param aspectY
     * @param outputX
     * @param outputY
     * @param returnData
     * @return
     */
    public static Intent buildImagePickIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY,
                                              int outputX, int outputY, boolean returnData) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uriFrom, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", uriTo);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", returnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return intent;
    }

    /**
     * 从照相机中获取图片，并切割
     */

    public static Intent buildCaptureIntent(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**==================== 其他 =================================*/

    /**
     * Return the byte usage per pixel of a bitmap based on its configuration.
     * @param config The bitmap configuration.
     * @return The byte usage per pixel.
     */
    private static int getBytesPerPixel(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888) {
            return 4;
        } else if (config == Bitmap.Config.RGB_565) {
            return 2;
        } else if (config == Bitmap.Config.ARGB_4444) {
            return 2;
        } else if (config == Bitmap.Config.ALPHA_8) {
            return 1;
        }
        return 1;
    }

    /**==================== 缩略图工具代理 =================================*/

    /**
     * @see ThumbnailUtils#createVideoThumbnail(String, int)
     */
    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        return ThumbnailUtils.createVideoThumbnail(filePath, kind);
    }

    /**
     * @see ThumbnailUtils#extractThumbnail(Bitmap, int, int)
     */
    public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
        return ThumbnailUtils.extractThumbnail(source, width, height);
    }

    /**
     * @see ThumbnailUtils#extractThumbnail(Bitmap, int, int, int)
     */
    public static Bitmap extractThumbnail(Bitmap source, int width, int height, int options) {
        return ThumbnailUtils.extractThumbnail(source, width, height, options);
    }
}
