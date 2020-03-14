package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.customview.MyImageView;

public class ImgScaleWindow extends Dialog {


    public ImgScaleWindow(Context context, Drawable drawable) {
        super(context);
        int windownWidth = YueFangApplication.getApplication().getWindownWidth();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(windownWidth, windownWidth);
        MyImageView imageView = new MyImageView(context);
        imageView.setImageDrawable(drawable);
        imageView.setLayoutParams(layoutParams);
        imageView.initListener();
        setContentView(imageView);
        setCancelable(true);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        getWindow().setAttributes(layoutParams);
    }
}
