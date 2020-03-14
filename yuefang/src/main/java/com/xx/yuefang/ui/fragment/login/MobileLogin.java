package com.xx.yuefang.ui.fragment.login;

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
import com.xx.yuefang.bean.LoginBody;
import com.xx.yuefang.bean.LoginResult;
import com.xx.yuefang.constant.HttpParam;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentLoginMobileBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.news.Link2;
import com.xx.yuefang.util.GsonUtil;
import java.util.concurrent.TimeUnit;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MobileLogin extends BaseFragment {

    private FragmentLoginMobileBinding binding;
    private LoginBody loginBody;
    private LoginFragment loginFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_mobile, container, false);
        loginBody = new LoginBody();
        binding.setLogin(this);
        binding.setLoginBody(loginBody);
        loginFragment = (LoginFragment) getParentFragment();
        initlisten();
        return binding.getRoot();
    }

    public void toggleArea(int area) {
        binding.setArea(area);
    }

    @Override
    public void initData() {}

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.etPhone.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.getArea() != 0) {
                    if (binding.getArea() == 1) {
                        phoneNumber = "852" + phoneNumber;
                    } else if (binding.getArea() == 2) {
                        phoneNumber = "853" + phoneNumber;
                    }
                }
                loginBody.setPhoneNumber(phoneNumber);
                if (TextUtils.isEmpty(loginBody.getCode())) {
                    Toast.makeText(getContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!binding.cb.isChecked()) {
                    Toast.makeText(getContext(), "请勾选用户协议", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpUtil.getInstance().login(loginBody).subscribe(
                        str -> {
                            Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                            LoginResult loginResult = GsonUtil.fromJson(str, LoginResult.class);
                            LoginResult.DataBean data = loginResult.getData();
                            if (data != null) {
                                UserOption.getInstance().delteUser();
                                UserBean userBean = new UserBean();
                                userBean.setToken(data.getToken());
                                userBean.setUserType(data.getUserType());
                                userBean.setNickName(data.getNickName());
                                userBean.setId((long) data.getId());
                                UserOption.getInstance().addUser(userBean);
                                UserObservable.getInstance().notifyObservers(new EventData(UserObservable.TYPE_LOGIN_SUCCESS));
                                getActivity().getSupportFragmentManager().popBackStack("Main", 0);
                            }
                        }
                );
            }
        });

        binding.sendYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.etPhone.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.getArea() != 0) {
                    if (binding.getArea() == 1) {
                        phoneNumber = "852" + phoneNumber;
                    } else if (binding.getArea() == 2) {
                        phoneNumber = "853" + phoneNumber;
                    }
                }
                loginBody.setPhoneNumber(phoneNumber);
                HttpUtil.getInstance().getYzm(phoneNumber, 2).subscribe(
                        str -> {
                            countDownTime();
                        }
                );
            }
        });


        binding.accountLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFragment.toggle(1);
            }
        });


        binding.privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Link2 link = new Link2();
                Bundle bundle = new Bundle();
                bundle.putString("title", "约房隐私政策");
                bundle.putString("url", HttpParam.baseUrl + "/protocol.html");
                link.setArguments(bundle);
                Presenter.getInstance().step2fragment(link, "link2");
            }
        });
        binding.serviceArgument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Link2 link = new Link2();
                Bundle bundle = new Bundle();
                bundle.putString("title", "约房用户服务协议");
                bundle.putString("url", HttpParam.baseUrl + "/serviceprotocol.html");
                link.setArguments(bundle);
                Presenter.getInstance().step2fragment(link, "link2");
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
