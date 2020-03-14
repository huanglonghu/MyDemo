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
import android.widget.Toast;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.DynamicDetailBean;
import com.xx.yuefang.bean.EnterFor;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.LayoutEnterForBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.util.ImagUtil;

public class EnterForDialog extends Dialog {
    private Context context;
    private DynamicDetailBean.DataBean data;

    public EnterForDialog(Context context, DynamicDetailBean.DataBean data) {
        super(context);
        this.context = context;
        this.data = data;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
        LayoutEnterForBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_enter_for, null, false);
        setContentView(binding.getRoot());
        EnterFor enterFor = new EnterFor();
        enterFor.setPremisesActivityId(data.getId());
        binding.setEnter(enterFor);

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userCall = enterFor.getUserCall();
                if (TextUtils.isEmpty(userCall)) {
                    Toast.makeText(context, "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phoneNumber = enterFor.getPhoneNumber();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(context, "请输入电话", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpUtil.getInstance().enterfor(enterFor).subscribe(
                        str -> {
                            Toast.makeText(context, "活动报名成功", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                );
            }
        });
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = 6 * YueFangApplication.getApplication().getWindownWidth() / 7;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setAttributes(layoutParams);
    }
}

