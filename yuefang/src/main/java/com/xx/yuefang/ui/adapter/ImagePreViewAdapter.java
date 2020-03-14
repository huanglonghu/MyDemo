package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xx.yuefang.R;
import com.xx.yuefang.ui.customview.PinchImageView;
import java.util.LinkedList;

public class ImagePreViewAdapter extends PagerAdapter {

    private Context mContext;
    private String[] mPathList;
    private RequestOptions mPreOptions = new RequestOptions()
            .skipMemoryCache(true)
            .error(R.mipmap.icon_image_error);

    LinkedList<PinchImageView> viewCache = new LinkedList<>();

    public ImagePreViewAdapter(Context context, String[] pathList) {
        this.mContext = context;
        this.mPathList = pathList;
    }

    @Override
    public int getCount() {
        return mPathList == null ? 0 : mPathList.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PinchImageView imageView;
        if (viewCache.size() > 0) {
            imageView = viewCache.remove();
            imageView.reset();
        } else {
            imageView = new PinchImageView(mContext);
        }
        try {
            Glide.with(mContext).load(mPathList[position]).apply(mPreOptions).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        PinchImageView imageView = (PinchImageView) object;
        container.removeView(imageView);
        viewCache.add(imageView);
    }
}
