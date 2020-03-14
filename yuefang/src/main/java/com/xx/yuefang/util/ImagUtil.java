package com.xx.yuefang.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xx.yuefang.constant.HttpParam;
import com.xx.yuefang.ui.base.YueFangApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImagUtil {


    public static String handleUrl(String path) {
        if (!TextUtils.isEmpty(path)) {
            if (!path.contains("http")) {
                path = HttpParam.baseUrl + "/" + path;
            }
            return path;
        }
        return null;
    }


    public static Drawable circle(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(YueFangApplication.getApplication().getResources(), bitmap);
        roundedBitmapDrawable.setCornerRadius(100);
        roundedBitmapDrawable.setCircular(true);
        return roundedBitmapDrawable;
    }


    public static Drawable conner(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(YueFangApplication.getApplication().getResources(), bitmap); //创建RoundedBitmapDrawable对象
        roundedBitmapDrawable.setCornerRadius(10); //设置圆角半径（根据实际需求）
        roundedBitmapDrawable.setAntiAlias(true); //设置反走样
        return roundedBitmapDrawable;
    }

    public static Drawable conner2(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(YueFangApplication.getApplication().getResources(), bitmap); //创建RoundedBitmapDrawable对象
        roundedBitmapDrawable.setCornerRadius(30); //设置圆角半径（根据实际需求）
        roundedBitmapDrawable.setAntiAlias(true); //设置反走样
        return roundedBitmapDrawable;
    }


    public static Bitmap compressImage(Bitmap image) {
        int bitmapSize = image.getRowBytes() * image.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        while (bitmapSize > (400 * 1024)) {
            image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
            bitmapSize = image.getRowBytes() * image.getHeight();
        }
        return image;
    }


    public static Bitmap getViewBitmap(View addViewContent) {
        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());
        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        return bitmap;
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        //获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        //计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        //取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        //得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }


    public static void saveBitmap(Bitmap bitmap, String bitName, Context context) {
        String fileName;
        File file;
        fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + bitName;
        file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
                MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), bitName, null);
            }
            Toast.makeText(context, "二维码已经保存到手机相册中", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        // 发送广播，通知刷新图库的显示
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
    }


}
