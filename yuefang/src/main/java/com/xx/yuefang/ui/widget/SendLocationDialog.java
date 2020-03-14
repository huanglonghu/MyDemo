package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.xx.yuefang.R;
import com.xx.yuefang.strategy.Callback;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.util.RudenessScreenHelper;

public class SendLocationDialog extends Dialog {
    private Context context;

    public SendLocationDialog(Context context, ClickSureListener clickSureListener) {
        super(context);
        this.context = context;
        View view = View.inflate(context, R.layout.layout_send_location, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSureListener.clickSure();
                dismiss();
            }
        });
        setContentView(view);
        setCancelable(true);
    }

    @Override
    public void show() {
        super.show();
        float width = RudenessScreenHelper.pt2px(context, 685);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = (int) width;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.dimAmount = 0.6f;
        getWindow().setWindowAnimations(R.style.popupStyle);
        getWindow().setAttributes(layoutParams);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


}
