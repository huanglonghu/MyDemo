package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.GetSellerList;
import com.xx.yuefang.databinding.DialogSearch1Binding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.YueFangApplication;

public class SearchDialog1 extends Dialog {
    private int conditionType = 1;

    public SearchDialog1(AppCompatActivity activity, ClickSureListener clickSureListener) {
        super(activity);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater inflater = LayoutInflater.from(activity);
        DialogSearch1Binding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_search1, null, false);
        setContentView(binding.getRoot());
        String[] datas = {"楼盘名称", "员工名称", "员工电话"};
        binding.title.setText(datas[0]);
        binding.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerWindow spinnerWindow = new SpinnerWindow(activity, datas, new ClickSureListener() {
                    @Override
                    public void click(int position) {
                        binding.title.setText(datas[position]);
                        conditionType = position + 1;
                    }
                });
                spinnerWindow.showAsDropDown(binding.title, 0, 5);
            }
        });

        binding.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.etContent.getText().toString();
                clickSureListener.search(conditionType, content);
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
