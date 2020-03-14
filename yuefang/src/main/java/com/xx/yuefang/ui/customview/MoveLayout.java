package com.xx.yuefang.ui.customview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.util.LogUtil;

public class MoveLayout extends LinearLayout {

    private float lastY;
    private float curY;

    public MoveLayout(Context context) {
        super(context);
    }

    public MoveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
////        curY = event.getRawY();
////        switch (event.getAction()) {
////            case MotionEvent.ACTION_DOWN:
////                lastY = event.getRawY();
////                break;
////            case MotionEvent.ACTION_MOVE:
////                move(curY - lastY);
////                lastY = curY;
////                break;
////            case MotionEvent.ACTION_UP:
////                break;
////        }
////        return true;
//
//    }


    public void move(float dy) {
        int windowHeight = YueFangApplication.getApplication().getWindowHeight();
        int height = getHeight();
        LogUtil.log((windowHeight - height) + "==========dy==========" +getY());
        if (getY()+dy>= windowHeight - height) {
            layout(getLeft() , (int) (getTop() + dy), getLeft() + getWidth(), (int) (getTop() + getHeight() + dy));
        }
    }


}
