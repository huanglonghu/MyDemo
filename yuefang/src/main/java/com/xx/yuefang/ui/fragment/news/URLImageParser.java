package com.xx.yuefang.ui.fragment.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.customview.URLDrawable;
import com.xx.yuefang.util.LogUtil;

public class URLImageParser implements Html.ImageGetter {
    private TextView mTextView;
    private Context context;

    public URLImageParser(Context context, TextView textView) {
        this.mTextView = textView;
        this.context = context;
    }

    @Override
    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();
        RxImageLoader.with(context).getBitmap(source).subscribe(
                imageBean -> {
                    Bitmap bitmap = imageBean.getBitmap();
                    urlDrawable.bitmap = bitmap;
                    int windownWidth = YueFangApplication.getApplication().getWindownWidth();
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    LogUtil.log(bitmap.getWidth()+"=========BBBBBB======="+windownWidth);
//                    if(width>windownWidth){
//                        height=windownWidth*height/width;
//                        width=windownWidth;
//                    }
                    urlDrawable.setBounds(0, 0, width, height);
                    mTextView.invalidate();
                    mTextView.setText(mTextView.getText());
                }
        );
        return urlDrawable;
    }
}