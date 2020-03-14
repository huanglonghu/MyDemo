package com.xx.yuefang.ui.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.databinding.LayoutReplyBinding;
import com.xx.yuefang.strategy.ClickSureListener;

public class ReplyWindow extends PopupWindow {

    private LayoutReplyBinding binding;

    public ReplyWindow(Context context, ClickSureListener clickSureListener) {
        setFocusable(true);
        setOutsideTouchable(true);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.RED));
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_reply, null, false);
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.commentContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                clickSureListener.click(content, type, commentId);
            }
        });
        setContentView(binding.getRoot());
    }


    private int type;
    private int commentId;
    private String hint;

    public void hint(String hint, int type, int commentId) {
        this.hint = hint;
        this.type = type;
        this.commentId = commentId;
        if (binding != null) {
            binding.commentContent.setHint(hint);
        }
        binding.commentContent.requestFocus();
    }

    public void replySuccess() {
        this.hint = "";
        if (binding != null) {
            binding.commentContent.setText("");
            binding.commentContent.setHint("");
        }
        dismiss();
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
