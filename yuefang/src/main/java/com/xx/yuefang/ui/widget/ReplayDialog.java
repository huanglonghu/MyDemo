package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.DialogReplayBinding;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.util.LogUtil;

public class ReplayDialog extends Dialog {

    private DialogReplayBinding binding;

    public ReplayDialog(@NonNull Context context, ClickSureListener clickSureListener,String hint) {
        super(context, R.style.dialog3);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_replay, null, false);
        binding.setHint(hint);
        binding.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.content.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(context, "请输入回答内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                clickSureListener.click(binding.content);
                dismiss();
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                binding.content.setFocusable(false);
                binding.content.setFocusableInTouchMode(false);
                binding.content.clearFocus();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                boolean active = imm.isActive();
                if(!active){
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        });

        setCancelable(false);
        setCanceledOnTouchOutside(true);
        setContentView(binding.getRoot());
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = YueFangApplication.getApplication().getWindownWidth();
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.dimAmount = 0.5f;
        getWindow().setWindowAnimations(R.style.popupStyle);
        getWindow().setAttributes(layoutParams);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        binding.content.setFocusable(true);
        binding.content.setFocusableInTouchMode(true);
        binding.content.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

}
