package com.xx.yuefang.ui.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xx.yuefang.util.LogUtil;

import java.util.List;

public class MyViewPager extends ViewPager {
    private List<ImageView> images;
    private boolean stopLoopTag = true;
    private Handler mHandler;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public List<ImageView> getImages() {
        return images;
    }

    public void setImages(List<ImageView> images) {
        this.images = images;
    }

    /**
     * 处理轮播的Handler
     */

    /**
     * 开启轮播
     */
    public void openLoop() {
        if (stopLoopTag) {
            stopLoopTag = false;
            initLoop();
        }
    }

    public void initLoop() {
        if (mHandler == null) {
            mHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == 10 && !stopLoopTag) {
                        setCurrentItem(msg.arg1);
                    } else {
                        mHandler.removeMessages(10);
                    }
                    return true;
                }
            });
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (!stopLoopTag) {
                        Message message = Message.obtain();
                        message.what = 10;
                        message.arg1 = getCurrentItem() + 1;
                        mHandler.sendMessage(message);
                        mHandler.postDelayed(this, 5000);
                    }
                }
            };
            mHandler.postDelayed(runnable, 100);
        }
    }


    public void exit() {
        stopLoopTag = true;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(true);
            mHandler = null;
        }
    }


}
