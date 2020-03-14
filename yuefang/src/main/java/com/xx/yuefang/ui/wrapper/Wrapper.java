package com.xx.yuefang.ui.wrapper;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.xx.yuefang.bean.PremisesDetail;

import java.util.HashMap;

public abstract class Wrapper extends LinearLayout {

    public Context context;

    public HashMap<String, Bitmap> bitmaps;

    public Wrapper(Context context){
        super(context);
        this.context = context;
        bitmaps = new HashMap<>();
        View view = initView();
        addView(view);
    }


    public Wrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        bitmaps = new HashMap<>();
        View view = initView();
        addView(view);
    }


    abstract View initView();

    public abstract void initData(PremisesDetail.DataBean data);


}
