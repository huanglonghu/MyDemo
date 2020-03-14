package com.xx.yuefang.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.strategy.AdClickListener;
import com.xx.yuefang.ui.adapter.BannerAdapter;
import com.xx.yuefang.ui.customview.MyViewPager;

import java.util.ArrayList;

public class BannerUtil {

    private ArrayList<ImageView> images;

    public void initBanner(String[] urls, Context context, MyViewPager viewPager, AdClickListener adClickListener, boolean isconner) {
        images = new ArrayList<>();
        if (urls.length == 2) {
            String[] array = new String[3];
            for (int i = 0; i < 3; i++) {
                if (i == 2) {
                    array[i] = urls[0];
                } else {
                    array[i] = urls[i];
                }
            }
            urls = array;
        }
        for (int x = 0; x < urls.length; x++) {
            ImageView iv = new ImageView(context);
            ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
            layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
            iv.setLayoutParams(layoutParams);
            String url = urls[x];
            url = ImagUtil.handleUrl(url);
            if (!TextUtils.isEmpty(url)) {
                if (isconner) {
                    RxImageLoader.with(context).getBitmap(url).subscribe(
                            imageBean -> {
                                Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                                iv.setBackground(drawable);
                            }
                    );
                } else {
                    RxImageLoader.with(context).load(url).into(iv, 1);
                }
            }
            int finalX = x;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adClickListener.click(finalX);
                }
            });
            iv.setTag(x);
            images.add(iv);
        }
        if (urls.length > 1) {
            BannerAdapter bannerAdapter = new BannerAdapter(images);
            viewPager.setAdapter(bannerAdapter);
            viewPager.setCurrentItem(3000);//设置当前position，不设置开始无法右滑
            viewPager.setImages(images);
        }

    }


}


