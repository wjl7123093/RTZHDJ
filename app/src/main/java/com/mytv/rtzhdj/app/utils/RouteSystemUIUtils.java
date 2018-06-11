package com.mytv.rtzhdj.app.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import java.io.File;

/**
 * 跳转到 系统界面
 * Created by wangjl on 2018/5/4.
 */
public class RouteSystemUIUtils {

    /**
     * 拍照
     *
     * @param path 照片存放的路径
     */
    public static void captureImage(Activity activity, int requestCode) {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            doTakePhotoIn7(new File(Environment.getExternalStorageDirectory(), "image.jpg").getAbsolutePath(),
                    activity, requestCode);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg")));
            activity.startActivityForResult(intent, requestCode);
        }*/

        //取消严格模式  FileProvider 【解决 7.0 以上拍照崩溃的 bug】
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg")));
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 从图库中选取图片
     */
    public static void selectImage(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        activity.startActivityForResult(intent, requestCode);
    }

    //在Android7.0以上拍照
    private static void doTakePhotoIn7(String path, Activity activity, int requestCode) {
        Uri mCameraTempUri;
        try {
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            values.put(MediaStore.Images.Media.DATA, path);
            mCameraTempUri = activity.getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePhoto(requestCode, mCameraTempUri, activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void takePhoto(int requestCode, Uri uri, Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (uri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        }
        activity.startActivityForResult(intent, requestCode);
    }

}
