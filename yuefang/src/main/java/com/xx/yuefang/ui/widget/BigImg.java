package com.xx.yuefang.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.xx.yuefang.R;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.customview.MyImageView;

public class BigImg extends PopupWindow {

    private Bitmap bitmap;

    public BigImg(Context context, Bitmap bitmap) {
        super(context);
        this.bitmap = bitmap;
        setAnimationStyle(R.style.popupStyle2);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        int windownWidth = YueFangApplication.getApplication().getWindownWidth();
        int i = (windownWidth / width) * height;
        MyImageView imageView = new MyImageView(context);
        imageView.setImageBitmap(bitmap);
        imageView.initListener();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        setContentView(imageView);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setFocusable(true);
    }

}
