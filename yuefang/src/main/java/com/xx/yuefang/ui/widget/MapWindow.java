package com.xx.yuefang.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.xx.yuefang.R;

public class MapWindow extends PopupWindow {

    public MapWindow(Context context) {
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.popupStyle);
        LayoutInflater inflater = LayoutInflater.from(context);

        View contentView = inflater.inflate(R.layout.layout_mapwindow, null, false);
        setContentView(contentView);
        setOutsideTouchable(false);
        setFocusable(false);


    }

}
