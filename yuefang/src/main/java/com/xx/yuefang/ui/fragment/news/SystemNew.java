package com.xx.yuefang.ui.fragment.news;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.SystemNews;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentSystemnewsBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.SystemNewsAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.GsonUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SystemNew extends BaseFragment {

    private FragmentSystemnewsBinding binding;
    private SystemNewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_systemnews, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        return binding.getRoot();
    }

    private void getSystemNews() {
        HttpUtil.getInstance().getSystemNews().subscribe(
                str -> {
                    SystemNews systemNews = GsonUtil.fromJson(str, SystemNews.class);
                    SystemNews.DataBeanX data = systemNews.getData();
                    list.clear();
                    list.addAll(data.getData());
                    Collections.reverse(list);
                    adapter.notifyDataSetChanged();
                }
        );
    }


    @Override
    public void initData() {
        HttpUtil.getInstance().isReadSystemNews().subscribe();
        getSystemNews();
    }

    private List<com.xx.yuefang.bean.SystemNews.DataBeanX.DataBean> list;


    @Override
    public void initView() {
        list = new ArrayList<>();
        UserBean userBean = UserOption.getInstance().querryUser();
        adapter = new SystemNewsAdapter(getContext(), list, R.layout.list_systemnews_item, userBean.getUserType());
        binding.listSystemnews.setAdapter(adapter);
    }

    @Override
    public void initlisten() {}


}
