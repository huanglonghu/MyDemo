package com.xx.yuefang.catche.catcheObservable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.ImageBean;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.util.ImagUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;

public class NetworkCacheObservable extends CacheObservable {

    private RequestOptions mPreOptions = new RequestOptions()
            .skipMemoryCache(true)
            .format(DecodeFormat.PREFER_RGB_565)
            .error(R.mipmap.icon_image_error);
    @Override
    public ImageBean getDataFromCache(String url) {
        Bitmap bitmap = downloadImageByGlide(url);
        return new ImageBean(bitmap, url);
    }


    @Override
    public void putDataToCache(ImageBean image) {
    }

    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    public Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            URL imageUrl = new URL(url);
            URLConnection urlConnection = (HttpURLConnection) imageUrl.openConnection();
            inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public Bitmap downloadImage(ResponseBody body) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = body.byteStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }


    public Bitmap downloadImageByGlide(String url) {
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(YueFangApplication.getApplication()).asBitmap().load(url).diskCacheStrategy(DiskCacheStrategy.ALL).apply(mPreOptions).submit().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;

    }
}
