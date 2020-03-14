package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.xx.yuefang.R;
import com.xx.yuefang.databinding.LayoutAppointSuccessBinding;
import com.xx.yuefang.ui.base.YueFangApplication;

public class AppointSuccessDialog extends Dialog {
    private Context context;

    public AppointSuccessDialog(Context context) {
        super(context);
        this.context = context;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
        LayoutAppointSuccessBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_appoint_success, null, false);
        setContentView(binding.getRoot());

        binding.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = 7*YueFangApplication.getApplication().getWindownWidth() / 8;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setAttributes(layoutParams);
    }
}

