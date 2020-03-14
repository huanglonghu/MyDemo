package com.xx.yuefang.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.xx.yuefang.util.LogUtil;

import java.util.List;

public class BannerAdapter extends PagerAdapter {

    private List<ImageView> images;


    public BannerAdapter(List<ImageView> images) {
        this.images = images;

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %=images.size();
        if (position < 0){
            position = images.size() + position;
        }
        ImageView imageView = images.get(position);
        ViewParent vp = imageView.getParent();
        if (vp != null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;

    }




}
