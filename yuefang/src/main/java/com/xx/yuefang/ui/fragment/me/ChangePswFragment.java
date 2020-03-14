package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.ChangePsw;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentEditPswBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;

import cn.jpush.im.android.api.JMessageClient;

public class ChangePswFragment extends BaseFragment {

    private FragmentEditPswBinding binding;
    private ChangePsw changePsw;
    private UserBean userBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_psw, container, false);
        binding.setPresenter(Presenter.getInstance());
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        userBean = UserOption.getInstance().querryUser();
        changePsw = new ChangePsw();
        if (userBean != null) {
            String avatar = userBean.getAvatar();
            String url = ImagUtil.handleUrl(avatar);
            if (!TextUtils.isEmpty(url)) {
                RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                        imageBean -> {
                            Drawable drawable = ImagUtil.circle(imageBean.getBitmap());
                            binding.head.setImageDrawable(drawable);
                        }
                );
            }
            changePsw.setId(userBean.getId());
            binding.setChangePsw(changePsw);
        }

    }

    @Override
    public void initView() {
    }

    @Override
    public void initlisten() {
        binding.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = changePsw.getOldPassword();
                if (TextUtils.isEmpty(oldPassword)) {
                    Toast.makeText(getContext(), "请填写原密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (oldPassword.length() < 6 || oldPassword.length() > 15) {
                    Toast.makeText(getContext(), "原密码输入错误,请输入6-15位数原密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = changePsw.getPassword();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "请填写新密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6 || password.length() > 15) {
                    Toast.makeText(getContext(), "新密码输入错误,请输入6-15位数新密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(binding.surePsw.getText().toString())) {
                    Toast.makeText(getContext(), "请确认新密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!changePsw.getPassword().equals(binding.surePsw.getText().toString())) {
                    Toast.makeText(getContext(), "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtil.getInstance().changePsw(changePsw, userBean.getUserType()).subscribe(
                        str -> {
                            Toast.makeText(getContext(), "密码修改成功,请重新登录", Toast.LENGTH_SHORT).show();
                            UserOption.getInstance().delteUser();
                            JMessageClient.logout();
                            UserObservable.getInstance().notifyObservers(new EventData(UserObservable.TYPE_LOGINOUT));
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.popBackStackImmediate("changePsw",1);
                            Presenter.getInstance().step2Fragment("login");
                        }
                );
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.log("==========changePsw=======onDestroyView()====");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.log("==========changePsw=======onDestroy()====");
    }
}
