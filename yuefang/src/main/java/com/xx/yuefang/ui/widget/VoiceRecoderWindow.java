package com.xx.yuefang.ui.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.xx.yuefang.R;
import com.xx.yuefang.databinding.LayoutVoiceRecoderBinding;
import com.xx.yuefang.util.RudenessScreenHelper;

public class VoiceRecoderWindow extends PopupWindow {


    private LayoutVoiceRecoderBinding binding;

    public VoiceRecoderWindow(Context context) {
        setOutsideTouchable(false);
        setFocusable(false);
        float w = RudenessScreenHelper.pt2px(context, 350);
        setWidth((int) w);
        setHeight((int) w);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_voice_recoder,null, false);
        setContentView(binding.getRoot());

    }

    public void show(View view) {
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    public void setDb(double db) {
        int v = (int) (db / 10);
      binding.db.setImageLevel(v - 3);
    }
}
