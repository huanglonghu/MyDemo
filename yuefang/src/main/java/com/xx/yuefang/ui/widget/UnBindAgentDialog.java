package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.LayoutCancleAppointBinding;
import com.xx.yuefang.databinding.LayoutUnbindAgentBinding;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.CancleReasonAdapter;
import com.xx.yuefang.ui.base.YueFangApplication;

import java.util.Arrays;
import java.util.List;

public class UnBindAgentDialog extends Dialog {
    private Context context;
    private LayoutUnbindAgentBinding binding;
    private ClickSureListener clickSureListener;

    public UnBindAgentDialog(Context context, ClickSureListener clickSureListener) {
        super(context);
        this.context = context;
        this.clickSureListener = clickSureListener;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_unbind_agent, null, false);
        binding.setDialog(this);
        setContentView(binding.getRoot());
        binding.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = binding.etReason.getText().toString();
                if (TextUtils.isEmpty(reason)) {
                    Toast.makeText(getContext(), "请选择取消绑定原因", Toast.LENGTH_SHORT).show();
                    return;
                }
                clickSureListener.click(reason);
                dismiss();
            }
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = 7 * YueFangApplication.getApplication().getWindownWidth() / 8;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setAttributes(layoutParams);
    }

}
