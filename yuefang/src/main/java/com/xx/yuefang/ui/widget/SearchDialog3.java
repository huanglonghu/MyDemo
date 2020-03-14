package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.GetHouseResourcesList;
import com.xx.yuefang.bean.GetSellerList;
import com.xx.yuefang.databinding.DialogSearch1Binding;
import com.xx.yuefang.databinding.DialogSearch3Binding;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.YueFangApplication;

public class SearchDialog3 extends Dialog {
    public SearchDialog3(AppCompatActivity activity,ClickSureListener clickSureListener) {
        super(activity);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater inflater = LayoutInflater.from(activity);
        DialogSearch3Binding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_search3, null, false);
        setContentView(binding.getRoot());
        binding.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String premisesName = binding.content.getText().toString();
                clickSureListener.search3(premisesName);
                dismiss();
            }
        });

    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = YueFangApplication.getApplication().getWindowHeight() / 2;
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setAttributes(layoutParams);
    }
}
