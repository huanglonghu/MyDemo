package com.xx.yuefang.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AgentBean;
import com.xx.yuefang.bean.Appoint;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.bean.ReservationDay;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.LayoutOnekeyyuefangBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.datepicker.TimePickerDialog;
import com.xx.yuefang.ui.datepicker.config.PickerConfig;
import com.xx.yuefang.ui.datepicker.data.Type;
import com.xx.yuefang.ui.datepicker.data.WheelCalendar;
import com.xx.yuefang.ui.datepicker.listener.OnDateSetListener;
import com.xx.yuefang.ui.fragment.house.SelectSaleManFragment;
import com.xx.yuefang.ui.widget.datepicker.DatePicker;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class OneKeyYueFangDialog extends Dialog {
    private LayoutOnekeyyuefangBinding binding;
    private int maxDay;
    private AppCompatActivity activity;

    public OneKeyYueFangDialog(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
    }

    public void show(PremisesDetail.DataBean dataBean) {
        super.show();
        Appoint appoint = new Appoint();
        appoint.setPremisesBaseId(dataBean.getId());
        appoint.setIsPickUp(1);
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.layout_onekeyyuefang, null, false);
        UserBean userBean = UserOption.getInstance().querryUser();
        if(userBean!=null){
            String phoneNumber = userBean.getPhoneNumber();
            binding.mobile.setText(phoneNumber);
        }
        binding.setAppoint(appoint);
        binding.man.setChecked(true);
        binding.premisesName.setText(dataBean.getPremisesName());
        binding.setDialog(this);
        setContentView(binding.getRoot());

        binding.changeMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.mobile.isEnabled()) {
                    binding.mobile.setText(userBean.getPhoneNumber());
                    binding.mobile.setEnabled(false);
                    binding.yzmLayout.setVisibility(View.GONE);
                    binding.changeMobile.setText("修改手机号");
                    binding.selectArea.setVisibility(View.GONE);
                } else {
                    binding.mobile.setText("");
                    binding.mobile.setEnabled(true);
                    binding.yzmLayout.setVisibility(View.VISIBLE);
                    binding.changeMobile.setText("返回");
                    binding.selectArea.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerConfig pickerConfig = new PickerConfig();
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.setTime(new Date());
                long minTime = mCalendar.getTimeInMillis();
                pickerConfig.mMinCalendar = new WheelCalendar(minTime);
                mCalendar.add(Calendar.DAY_OF_MONTH, +maxDay);
                long maxTime = mCalendar.getTimeInMillis();
                pickerConfig.mMaxCalendar = new WheelCalendar(maxTime);
                TimePickerDialog timePickerDialog = new TimePickerDialog.Builder()
                        .PickerConfig(pickerConfig)
                        .setType(Type.YEAR_MONTH_DAY)
                        .setCallBack(new OnDateSetListener() {
                            @Override
                            public void onDateSet(String date) {
                                binding.selectTime.setText(date);
                            }
                        }).build();
                timePickerDialog.show(activity.getSupportFragmentManager(), "year_month_day");
            }
        });
        binding.getYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.mobile.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(activity, "请先输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.getArea() != 0) {
                    if (binding.getArea() == 1) {
                        phoneNumber = "852" + phoneNumber;
                    } else if (binding.getArea() == 2) {
                        phoneNumber = "853" + phoneNumber;
                    }
                }
                appoint.setPhoneNumber(phoneNumber);
                HttpUtil.getInstance().getYzm(appoint.getPhoneNumber(), 4).subscribe(
                        str -> {
                            countDownTime();
                        }
                );
            }
        });


        HttpUtil.getInstance().getAppointDay(dataBean.getDeveloperId()).subscribe(
                str -> {
                    ReservationDay reservationDay = GsonUtil.fromJson(str, ReservationDay.class);
                    maxDay = reservationDay.getData();

                }
        );
        String[] personArray = {"1人", "2-3人", "3-5人", "5人以上", "待定"};
        binding.next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.mobile.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(activity, "请先输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.getArea() != 0) {
                    if (binding.getArea() == 1) {
                        phoneNumber = "852" + phoneNumber;
                    } else if (binding.getArea() == 2) {
                        phoneNumber = "853" + phoneNumber;
                    }
                }
                appoint.setPhoneNumber(phoneNumber);
                if (binding.yzmLayout.isShown() && TextUtils.isEmpty(appoint.getCode())) {
                    Toast.makeText(activity, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String userCall = appoint.getUserCall();
                if (TextUtils.isEmpty(userCall)) {
                    Toast.makeText(activity, "请输入称呼", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.man.isChecked()) {
                    userCall = userCall + "先生";
                } else {
                    userCall = userCall + "女士";
                }
                appoint.setUserCall(userCall);
                appoint.setLookHouseNumber(personArray[binding.getPersonNum()]);

                if (TextUtils.isEmpty(appoint.getReservationTime())) {
                    Toast.makeText(activity, "请输入预约时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtil.getInstance().getAgent(dataBean.getId()).subscribe(
                        str -> {
                            JSONObject jo = new JSONObject(str);
                            int errcode = jo.getInt("Errcode");
                            if (errcode == 0) {
                                AgentBean agentBean = GsonUtil.fromJson(str, AgentBean.class);
                                AgentBean.DataBean data = agentBean.getData();
                                int id = data.getId();
                                appoint.setSalespersonId(id);
                                HttpUtil.getInstance().appoint(appoint).subscribe(
                                        str2 -> {
                                            dismiss();
                                            AppointSuccessDialog appointSuccessDialog = new AppointSuccessDialog(getContext());
                                            appointSuccessDialog.show();
                                        }
                                );
                            } else if (errcode == 5008) {
                                dismiss();
                                SelectSaleManFragment selectSaleManFragment = new SelectSaleManFragment();
                                Bundle bundle = new Bundle();
                                String s = GsonUtil.toJson(appoint);
                                bundle.putInt("premisesId", dataBean.getId());
                                bundle.putInt("type", 1);
                                bundle.putString("appoint",s);
                                selectSaleManFragment.setArguments(bundle);
                                Presenter.getInstance().step2fragment(selectSaleManFragment, "selectSaleMan");
                            }
                        }
                );


            }
        });
        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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

    public void selectWay(int way) {
        binding.setSelectPosition(way);
        binding.getAppoint().setIsPickUp(way + 1);
    }

    public void toggleArea(int area) {
        binding.setArea(area);
    }

    public void onYearMonthDayPicker(Activity activity) {
        DatePicker picker = new DatePicker(activity);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());
        picker.setContentPadding(20, 20);
        picker.setRangeStart(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH));
        picker.setSelectedItem(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH));
        mCalendar.add(Calendar.DAY_OF_MONTH, +maxDay);
        picker.setRangeEnd(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH));

        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                binding.selectTime.setText(year + "-" + month + "-" + day);
            }
        });
        picker.show();
    }

    public void countDownTime() {
        binding.getYzm.setTextColor(Color.GRAY);
        binding.getYzm.setEnabled(false);
        Flowable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        binding.getYzm.setText("重新获取 " + String.valueOf(60 - aLong) + " 秒");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        binding.getYzm.setText("获取验证码");
                        binding.getYzm.setEnabled(true);
                    }
                })
                .subscribe();
    }

    public void selectPersonNum(int position) {
        binding.setPersonNum(position);
    }

}
