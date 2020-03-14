package com.xx.yuefang.ui.fragment.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.RegisterBody;
import com.xx.yuefang.databinding.FragmentLoginBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.MainFragment;
import com.xx.yuefang.util.SoftHideKeyBoardUtil;
import com.xx.yuefang.util.ThridPartLogin;
import java.util.ArrayList;

public class LoginFragment extends BaseFragment {
    private FragmentLoginBinding binding;
    private ArrayList<BaseFragment> fragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setPresenter(Presenter.getInstance());
        binding.setFragment(this);
        ThridPartLogin.getInstance().initParam(this, null);
        binding.setThridPartLogin(ThridPartLogin.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {}

    @Override
    public void initView() {
        fragments = new ArrayList<>();
        PswLogin pswLogin = new PswLogin();
        MobileLogin mobileLogin = new MobileLogin();
        fragments.add(mobileLogin);
        fragments.add(pswLogin);
        SoftHideKeyBoardUtil.setToggle(true);
        getChildFragmentManager().beginTransaction().replace(R.id.login_container, mobileLogin).commit();
    }

    public void toggle(int index) {
        getChildFragmentManager().beginTransaction().replace(R.id.login_container, fragments.get(index)).commit();
    }

    @Override
    public void initlisten() {
        binding.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                RegisterBody registerBody = new RegisterBody();
                registerFragment.setRegisterBody(registerBody);
                Presenter.getInstance().step2fragment(registerFragment, "register");
            }
        });


    }

    @Override
    public void onKeyDown() {
        MainFragment main = (MainFragment) Presenter.getInstance().getFragment("Main");
        int position = main.getPosition();
        if (position == 3) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStackImmediate("login", 1);
            Presenter.getInstance().back();
        } else {
            Presenter.getInstance().back();
        }
    }
};
