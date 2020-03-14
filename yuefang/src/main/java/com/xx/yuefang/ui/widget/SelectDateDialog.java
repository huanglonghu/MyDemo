package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.LayoutSelectdateBinding;
import com.xx.yuefang.strategy.ClickSureListener;
import java.util.Calendar;
import java.util.Date;

public class SelectDateDialog extends Dialog {
    private Context context;
    private ClickSureListener clickSureListener;
    private int maxDay;

    public SelectDateDialog(Context context, ClickSureListener clickSureListener,int maxDay) {
        super(context, R.style.dialog3);
        this.context = context;
        this.clickSureListener = clickSureListener;
        this.maxDay=maxDay;
        setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
        LayoutSelectdateBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_selectdate, null, false);
        setContentView(binding.getRoot());
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());
        binding.dp.setMinDate(mCalendar.getTimeInMillis());
        mCalendar.add(Calendar.DAY_OF_MONTH,maxDay);
        binding.dp.setMaxDate(mCalendar.getTimeInMillis());
        binding.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        binding.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                int month = binding.dp.getMonth() + 1;
                int year = binding.dp.getYear();
                int dayOfMonth = binding.dp.getDayOfMonth();
                clickSureListener.clickSureOfDate(year, month, dayOfMonth);
            }
        });
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setAttributes(layoutParams);
    }


}
