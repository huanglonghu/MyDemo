package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.ui.customview.MyImageView;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.List;

public class PictureDetailPageAdapter extends PagerAdapter {
    private List<MyImageView> views;
    private boolean[] array;
    private String[] urls;
    private Context context;


    public PictureDetailPageAdapter(Context context, List<MyImageView> views, String[] urls) {
        this.context = context;
        this.views = views;
        this.urls = urls;
        array = new boolean[views.size()];

    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {

        if (array[position] == false) {
            array[position] = true;
            MyImageView imageView = views.get(position);
            String url = urls[position];
            url = ImagUtil.handleUrl(url);

            LogUtil.log(urls[position]+"====aaaazzzx=======setPrimaryItem========"+position);
            RxImageLoader.with(context).getBitmap(url).subscribe(
                    imageBean -> {
                        Bitmap bitmap = imageBean.getBitmap();
                        imageView.setImageDrawable(new BitmapDrawable(bitmap));
                        imageView.initListener();
                    }
            );
        }

    }


}
