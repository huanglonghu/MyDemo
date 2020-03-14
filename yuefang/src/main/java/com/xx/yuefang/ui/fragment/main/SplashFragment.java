package com.xx.yuefang.ui.fragment.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.SystemSetup;
import com.xx.yuefang.database.SystemDataOption;
import com.xx.yuefang.database.entity.SystemData;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.widget.PrivacyDialog;
import com.xx.yuefang.util.GsonUtil;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashFragment extends BaseFragment {

    private View view;
    private TextView tvTime;
    private PrivacyDialog privacyDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_splash, container, false);
        SharedPreferences sp = getActivity().getSharedPreferences("policy", Context.MODE_PRIVATE);
        boolean isReadPolicy = sp.getBoolean("isReadPolicy", false);
        if (!isReadPolicy) {
            if(privacyDialog == null){
                privacyDialog = new PrivacyDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        sp.edit().putBoolean("isReadPolicy", true).commit();
                        initData();
                        initlisten();
                    }
                });
            }
            privacyDialog.show();
        } else {
            initData();
            initlisten();
        }
        return view;
    }

    @Override
    public void initData() {
        HttpUtil.getInstance().getSystemSetup().subscribe(
                str -> {
                    SystemSetup systemSetup = GsonUtil.fromJson(str, SystemSetup.class);
                    SystemSetup.DataBean data = systemSetup.getData();
                    SystemData systemData = new SystemData();
                    systemData.setHotKeywords(data.getHotKeywords());
                    systemData.setId((long) data.getId());
                    systemData.setSalespersonQuickChat(data.getSalespersonQuickChat());
                    systemData.setUserQuickChat(data.getUserQuickChat());
                    SystemDataOption.getInstance().insertSystemData(systemData);
                }
        );
    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {
        ViewStub vs = view.findViewById(R.id.vs);
        tvTime = (TextView) vs.inflate();
        Disposable subscribe = Observable.interval(0, 1, TimeUnit.SECONDS, Schedulers.io())
                .take(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        time -> {
                            tvTime.setText("跳过 " + (3 - time) + "s");
                            if (time == 2) {
                                YueFangApplication.getApplication().setCount(1);
                                Presenter.getInstance().step2Fragment("Main");
                            }
                        }
                );
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subscribe != null) {
                    subscribe.dispose();
                }
                YueFangApplication.getApplication().setCount(1);
                Presenter.getInstance().step2Fragment("Main");
            }
        });
    }
}
