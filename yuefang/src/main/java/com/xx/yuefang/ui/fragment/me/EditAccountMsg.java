package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AccountBean;
import com.xx.yuefang.constant.HttpParam;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentEditAccountmsgBinding;
import com.xx.yuefang.databinding.LayoutEditNicknameBinding;
import com.xx.yuefang.databinding.LayoutEditPhonenumber1Binding;
import com.xx.yuefang.databinding.LayoutEditPhonenumber2Binding;
import com.xx.yuefang.databinding.LayoutEditPhonenumber3Binding;
import com.xx.yuefang.handler.UpdateAccountMsg;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class EditAccountMsg extends BaseFragment {

    private FragmentEditAccountmsgBinding binding;
    private UpdateAccountMsg.Builder builder;
    private AccountBean accountBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_accountmsg, container, false);
        binding.setPresenter(Presenter.getInstance());
        initData();
        initView();
        initlisten();
        return binding.getRoot();
    }

    private String key;
    private String value;
    private String name;

    public interface EditAccountCallBack {

        void editCompleted(AccountBean accountBean);

    }

    private EditAccountCallBack editAccountCallBack;

    public void setEditAccountCallBack(EditAccountCallBack editAccountCallBack) {
        this.editAccountCallBack = editAccountCallBack;
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        accountBean = (AccountBean) bundle.getSerializable("accountBean");
        name = accountBean.getName();
        key = accountBean.getKey();
        value = accountBean.getValue();
        builder = new UpdateAccountMsg.Builder();
        StringBuffer title = new StringBuffer();
        title.append("修改" + name);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = null;
        if (this.name.equals("手机号码")) {
            if (!TextUtils.isEmpty(value)) {
                LayoutEditPhonenumber1Binding binding1 = DataBindingUtil.inflate(layoutInflater, R.layout.layout_edit_phonenumber1, binding.cotainer, false);
                String settingphone = settingphone(this.value);
                String str = "请输入" + settingphone + "收到的短信验证码";
                binding1.setHint(str);
                view = binding1.getRoot();
                binding1.getYzm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpUtil.getInstance().getYzm(EditAccountMsg.this.value, 5).subscribe(
                                str -> {
                                    countDownTime(binding1.getYzm);
                                }
                        );
                    }
                });
                binding1.next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String yzm = binding1.yzm.getText().toString();
                        if (TextUtils.isEmpty(yzm)) {
                            Toast.makeText(getContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("Code", yzm);
                        builder.map(map);
                        LayoutEditPhonenumber2Binding binding4 = DataBindingUtil.inflate(layoutInflater, R.layout.layout_edit_phonenumber2, binding.cotainer, false);
                        binding4.setFragment(EditAccountMsg.this);
                        binding.cotainer.removeAllViews();
                        binding.cotainer.addView(binding4.getRoot());
                    }
                });
            } else {
                LayoutEditPhonenumber3Binding binding3 = DataBindingUtil.inflate(layoutInflater, R.layout.layout_edit_phonenumber3, binding.cotainer, false);
                binding3.sendYzm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phoneNumber = binding3.phoneNumber.getText().toString();
                        if (TextUtils.isEmpty(phoneNumber)) {
                            Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int area = binding3.getArea();
                        if (area != 0) {
                            if (area == 1) {
                                phoneNumber = "852" + phoneNumber;
                            } else if (area == 2) {
                                phoneNumber = "853" + phoneNumber;
                            }
                        }
                        HttpUtil.getInstance().getYzm(phoneNumber, 5).subscribe(
                                str -> {
                                    countDownTime(binding3.sendYzm);
                                }
                        );
                    }
                });
                binding3.save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phoneNumber = binding3.phoneNumber.getText().toString();
                        if (TextUtils.isEmpty(phoneNumber)) {
                            Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int area = binding3.getArea();
                        if (area != 0) {
                            if (area == 1) {
                                phoneNumber = "852" + phoneNumber;
                            } else if (area == 2) {
                                phoneNumber = "853" + phoneNumber;
                            }
                        }
                        String yzm = binding3.etYzm.getText().toString();
                        if (TextUtils.isEmpty(yzm)) {
                            Toast.makeText(getContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("Code", yzm);
                        map.put(key, phoneNumber);
                        builder.map(map);
                        String finalPhoneNumber = phoneNumber;
                        builder.map(map).key(key).value(phoneNumber).completeStrategy(
                                new ClickSureListener() {
                                    @Override
                                    public void click(String str) {
                                        accountBean.setValue(finalPhoneNumber);
                                        editAccountCallBack.editCompleted(accountBean);
                                        Toast.makeText(getContext(), name + "修改成功", Toast.LENGTH_SHORT).show();
                                        Presenter.getInstance().back();
                                    }
                                }
                        ).build().update();

                    }
                });
                view = binding3.getRoot();
            }

        } else {
            LayoutEditNicknameBinding binding1 = DataBindingUtil.inflate(layoutInflater, R.layout.layout_edit_nickname, binding.cotainer, false);
            binding1.setFragment(this);
            if (key.equals("ReservationDays")) {
                binding1.etContent.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            binding1.etContent.setText(this.value);
            view = binding1.getRoot();
        }
        binding.cotainer.addView(view);
        binding.setTitle(title.toString());
    }


    private String settingphone(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initlisten() {
    }

    public void countDownTime(TextView btn) {
        btn.setEnabled(false);
        Flowable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        btn.setText("重新获取 " + String.valueOf(60 - aLong) + " 秒");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        btn.setText("获取验证码");
                        btn.setEnabled(true);

                    }
                })
                .subscribe();
    }


    public void toggleArea(View view, int area) {
        LayoutEditPhonenumber2Binding binding = DataBindingUtil.findBinding(view);
        binding.setArea(area);
    }


    public void save(View view) {
        LayoutEditPhonenumber2Binding editBinding2 = DataBindingUtil.findBinding(view);
        String phoneNumer = editBinding2.phoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumer)) {
            Toast.makeText(getContext(), "请输入" + key, Toast.LENGTH_SHORT).show();
            return;
        }
        int area = editBinding2.getArea();
        if (area != 0) {
            if (area == 1) {
                phoneNumer = "852" + phoneNumer;
            } else if (area == 2) {
                phoneNumer = "853" + phoneNumer;
            }
        }
        HashMap<String, Object> map = builder.getMap();
        map.put(key, phoneNumer);
        builder.map(map).key(key).value(phoneNumer).completeStrategy(
                new ClickSureListener() {
                    @Override
                    public void click(String str) {
                        accountBean.setValue(value);
                        editAccountCallBack.editCompleted(accountBean);
                        Toast.makeText(getContext(), name + "修改成功", Toast.LENGTH_SHORT).show();
                        Presenter.getInstance().back();
                    }
                }
        ).build().update();
    }

    public void save2(View view) {
        LayoutEditNicknameBinding editBinding1 = DataBindingUtil.findBinding(view);
        String content = editBinding1.etContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), "请输入" + key, Toast.LENGTH_SHORT).show();
            return;
        }
        UserBean userBean = UserOption.getInstance().querryUser();
        HashMap<String, Object> map = new HashMap<>();
        if (key.equals("ReservationDays")) {
            int i = Integer.parseInt(content);
            map.put(key, i);
        } else {
            map.put(key, content);
        }
        map.put("Id", userBean.getId());
        builder.map(map).key(key).value(content).completeStrategy(new ClickSureListener() {
            @Override
            public void click(String value) {
                accountBean.setValue(value);
                editAccountCallBack.editCompleted(accountBean);
                Toast.makeText(getContext(), name + "修改成功", Toast.LENGTH_SHORT).show();
                Presenter.getInstance().back();
            }
        }).build().update();
    }

    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
    }


    @Override
    public void onStop() {
        super.onStop();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
    }
}
