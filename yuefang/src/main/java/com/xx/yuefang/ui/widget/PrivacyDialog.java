package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.xx.yuefang.R;
import com.xx.yuefang.constant.HttpParam;
import com.xx.yuefang.databinding.LayoutPolicyBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.fragment.news.Link2;

public class PrivacyDialog extends Dialog {

    private LayoutPolicyBinding binding;

    public PrivacyDialog(Context context, ClickSureListener clickSureListener) {
        super(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_policy, null, false);
        binding.content.setText(R.string.policyContent1);
        Resources resources = context.getResources();
        String policy = resources.getString(R.string.policy);
        SpannableString clickString = new SpannableString(policy);
        clickString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Link2 link = new Link2();
                Bundle bundle = new Bundle();
                bundle.putString("title", "约房隐私政策");
                bundle.putString("url", HttpParam.baseUrl + "/protocol.html");
                link.setArguments(bundle);
                Presenter.getInstance().step2fragment(link, "link2");
                dismiss();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                int color = resources.getColor(R.color.colorPrimary);
                ds.setColor(color);//设置颜色
            }
        }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.content.append(clickString);

        binding.content.append("和");
        String pollicy2 = resources.getString(R.string.policy2);
        SpannableString clickString2 = new SpannableString(pollicy2);
        clickString2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Link2 link = new Link2();
                Bundle bundle = new Bundle();
                bundle.putString("title", "约房用户服务协议");
                bundle.putString("url", HttpParam.baseUrl + "/serviceprotocol.html");
                link.setArguments(bundle);
                Presenter.getInstance().step2fragment(link, "link2");
                dismiss();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                int color = resources.getColor(R.color.colorPrimary);
                ds.setColor(color);//设置颜色
            }
        }, 0, clickString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.content.append(clickString2);


        String policyContent2 = resources.getString(R.string.policyContent2);
        binding.content.append(new SpannableString(policyContent2) + "\n" + "\n");
        String policyContent3 = resources.getString(R.string.policyContent3);
        binding.content.append(policyContent3);


        binding.content.setHighlightColor(Color.TRANSPARENT);
        binding.content.setMovementMethod(LinkMovementMethod.getInstance());
        binding.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                clickSureListener.clickSure();
            }
        });
        binding.refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                context.startActivity(home);
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
        layoutParams.width = 8 * YueFangApplication.getApplication().getWindownWidth() / 10;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setWindowAnimations(R.style.popupStyle);
        getWindow().setAttributes(layoutParams);
    }

}
