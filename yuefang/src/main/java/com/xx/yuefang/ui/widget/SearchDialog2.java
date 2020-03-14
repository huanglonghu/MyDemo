package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.DialogSearch2Binding;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.datepicker.TimePickerDialog;
import com.xx.yuefang.ui.datepicker.config.PickerConfig;
import com.xx.yuefang.ui.datepicker.data.Type;
import com.xx.yuefang.ui.datepicker.data.WheelCalendar;
import com.xx.yuefang.ui.datepicker.listener.OnDateSetListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SearchDialog2 extends Dialog {
    private int conditionType=1;

    public SearchDialog2(AppCompatActivity activity, ClickSureListener clickSureListener) {
        super(activity);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater inflater = LayoutInflater.from(activity);
        DialogSearch2Binding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_search2, null, false);
        setContentView(binding.getRoot());
        String[] datas = {"楼盘名称", "预约人名称", "预约人电话", "业务员名称", "业务员电话"};
        //条件类(1楼盘名称 2预约人名称 3预约人电话 4业务员名称 5业务员电话
        binding.title.setText(datas[0]);
        binding.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerWindow spinnerWindow = new SpinnerWindow(activity, datas, new ClickSureListener() {
                    @Override
                    public void click(int position) {
                        binding.title.setText(datas[position]);
                        conditionType = position + 1;
                    }
                });
                spinnerWindow.showAsDropDown(binding.title, 0, 5);
            }
        });
        binding.selectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(binding.startDate, activity);
            }
        });
        binding.selectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(binding.endDate, activity);
            }
        });
        binding.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                String condition = binding.content.getText().toString();
                String startTime = binding.startDate.getText().toString();
                String endTime = binding.endDate.getText().toString();
                clickSureListener.querry(conditionType, condition, startTime, endTime);
            }
        });
    }

    private void selectDate(TextView tv, AppCompatActivity activity) {
        PickerConfig pickerConfig = new PickerConfig();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate = dateFormat.parse("1970-1-1");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = new Date();
        pickerConfig.mMinCalendar = new WheelCalendar(startDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, 2);
        Date date = calendar.getTime();
        pickerConfig.mMaxCalendar = new WheelCalendar(date.getTime());
        TimePickerDialog timePickerDialog = new TimePickerDialog.Builder()
                .PickerConfig(pickerConfig)
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(String date) {
                        tv.setText(date);
                    }
                }).build();
        timePickerDialog.show(activity.getSupportFragmentManager(), "year_month_day");
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = YueFangApplication.getApplication().getWindowHeight() / 2;
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setAttributes(layoutParams);
    }
}
