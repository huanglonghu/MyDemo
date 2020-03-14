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
import android.widget.AdapterView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.LayoutCancleAppointBinding;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.CancleReasonAdapter;
import com.xx.yuefang.ui.base.YueFangApplication;

import java.util.Arrays;
import java.util.List;

public class CancleAppointDialog extends Dialog {
    private Context context;
    private LayoutCancleAppointBinding binding;
    private ClickSureListener clickSureListener;

    public CancleAppointDialog(Context context, ClickSureListener clickSureListener) {
        super(context);
        this.context = context;
        this.clickSureListener = clickSureListener;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_cancle_appoint, null, false);
        binding.setDialog(this);
        setContentView(binding.getRoot());
        binding.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        String[] reasons;

        UserBean userBean = UserOption.getInstance().querryUser();
        int userType = userBean.getUserType();
        if (userType == 4) {
            reasons = new String[]{"计划有变,下次再约", "信息填写有误", "变更销售员", "其他原因"};
        } else {
            reasons = new String[]{"正在带客", "其他原因"};
        }
        List<String> list = Arrays.asList(reasons);
        CancleReasonAdapter cancleReasonAdapter = new CancleReasonAdapter(context, list, 0);
        binding.lvResaon.setAdapter(cancleReasonAdapter);
        binding.lvResaon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cancleReasonAdapter.refreshSelectPosition(position);
            }
        });
        binding.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancleReasonAdapter.getSelectPosition() == -1) {
                    Toast.makeText(getContext(), "请选择取消预约原因", Toast.LENGTH_SHORT).show();
                    return;
                }
                clickSureListener.click(reasons[cancleReasonAdapter.getSelectPosition()]);
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
