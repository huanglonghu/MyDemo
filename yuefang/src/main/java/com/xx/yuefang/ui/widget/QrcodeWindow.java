package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xx.yuefang.R;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.RudenessScreenHelper;

public class QrcodeWindow extends Dialog {

    private Context context;

    public QrcodeWindow(Context context, ViewGroup viewGroup) {
        super(context);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.qrcode, viewGroup, false);
        TextView tv = view.findViewById(R.id.saveQrcode);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.qrcode);
                ImagUtil.saveBitmap(bitmap, "星熙地产", context);
            }
        });
        setContentView(view);
        setCancelable(true);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = (int) RudenessScreenHelper.pt2px(context,600);
        layoutParams.height = (int) RudenessScreenHelper.pt2px(context,700);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        getWindow().setAttributes(layoutParams);
    }
}
