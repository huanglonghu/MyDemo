package com.xx.yuefang.ui.fragment.login;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.BackPsw;
import com.xx.yuefang.databinding.FragmentBackpswBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class BackPswFragment extends BaseFragment {

    private FragmentBackpswBinding binding;
    private BackPsw backPsw;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_backpsw, container, false);
        binding.setPresenter(Presenter.getInstance());
        int type = getArguments().getInt("type");
        binding.setType(type);
        backPsw = new BackPsw();
        binding.setBackPsw(backPsw);
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {


    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(backPsw.getPhoneNumber())) {
                    Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(backPsw.getCode())) {
                    Toast.makeText(getContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = backPsw.getPassword();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6 || password.length() > 15) {
                    Toast.makeText(getContext(), "请输入6-15位数新密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpUtil.getInstance().backPsw(backPsw).subscribe(
                        str -> {
                            JSONObject jo = new JSONObject(str);
                            int errcode = jo.getInt("Errcode");
                            if (errcode == 0) {
                                Toast.makeText(getContext(), "新密码设置成功", Toast.LENGTH_SHORT).show();
                                Presenter.getInstance().back();
                            } else {
                                String message = jo.getString("Message");
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
        binding.sendYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(backPsw.getPhoneNumber())) {
                    Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtil.getInstance().getYzm(backPsw.getPhoneNumber(), 3).subscribe(
                        str -> {
                            countDownTime();
                        }
                );
            }
        });
    }


    public void countDownTime() {
        binding.sendYzm.setEnabled(false);
        Flowable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        binding.sendYzm.setText("重新发送 " + String.valueOf(60 - aLong) + " 秒");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        binding.sendYzm.setText("获取验证码");
                        binding.sendYzm.setEnabled(true);

                    }
                })
                .subscribe();
    }


}
