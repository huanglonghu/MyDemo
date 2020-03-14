package com.xx.yuefang.ui.fragment.house;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.HouseMessage;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentHouseMesssageBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.HousesMessageAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;
import org.json.JSONObject;

public class HouseMessageFragment extends BaseFragment {

    private FragmentHouseMesssageBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_house_messsage, container, false);
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    private PremisesDetail.DataBean dataBean;

    @Override
    public void initData() {
        dataBean = (PremisesDetail.DataBean) getArguments().getSerializable("data");
        binding.setData(dataBean);
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            binding.setUserType(userBean.getUserType());
        }
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            PremisesDetail.DataBean dataBean = (PremisesDetail.DataBean) bundle.getSerializable("data");
            HttpUtil.getInstance().getMoreHouseMessageById(dataBean.getId()).subscribe(
                    str -> {
                        HouseMessage houseMessage = GsonUtil.fromJson(str, HouseMessage.class);
                        HouseMessage.DataBean data = houseMessage.getData();
                        if (data != null) {
                            binding.setPresenter(Presenter.getInstance());
                            HousesMessageAdapter housesMessageAdapter = new HousesMessageAdapter(getActivity(), data);
                            binding.houseMessageLv.setAdapter(housesMessageAdapter);
                        }
                    }
            );
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {

    }

    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
    }

    @Override
    public void onStop() {
        super.onStop();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
    }
}
