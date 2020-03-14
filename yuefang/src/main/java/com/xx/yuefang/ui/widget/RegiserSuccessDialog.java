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
import com.xx.yuefang.databinding.DialogRegiserSuccessBinding;
import com.xx.yuefang.databinding.LayoutTip2Binding;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.util.RudenessScreenHelper;

public class RegiserSuccessDialog extends Dialog {

    private DialogRegiserSuccessBinding binding;
    private Context context;

    public RegiserSuccessDialog(Context context) {
        super(context);
        this.context=context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_regiser_success, null, false);
        binding.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        setContentView(binding.getRoot());
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        int w = (int) RudenessScreenHelper.pt2px(context, 555);
        int h = (int) RudenessScreenHelper.pt2px(context, 563);
        layoutParams.width = w;
        layoutParams.height = h;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setWindowAnimations(R.style.popupStyle);
        getWindow().setAttributes(layoutParams);
    }


}
