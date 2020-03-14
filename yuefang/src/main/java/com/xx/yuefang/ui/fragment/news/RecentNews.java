package com.xx.yuefang.ui.fragment.news;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.GetNewsList;
import com.xx.yuefang.bean.NewsList;
import com.xx.yuefang.databinding.FragmentRecentNewsBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.ui.adapter.RecentNewsAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.customview.ScrollRefreshListView;
import com.xx.yuefang.ui.fragment.main.HeadLineFragment;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class RecentNews extends BaseFragment implements ScrollRefreshListView.RefreshData {

    private FragmentRecentNewsBinding binding;
    private List<NewsList.DataBeanX.DataBean> data;
    private RecentNewsAdapter newsListAdapter;
    private GetNewsList getNewsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent_news, container, false);
            binding.lvRecentNews.setRefreshData(this);
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {
        refreshData(1);
        binding.lvRecentNews.setPage(1);
    }


    @Override
    public void initView() {
        data = new ArrayList<>();
        getNewsList = new GetNewsList();
        getNewsList.setLimit(20);
        getNewsList.setNewsInfoType(2);
        newsListAdapter = new RecentNewsAdapter(getContext(), data, R.layout.list_news_item);
        binding.lvRecentNews.setAdapter(newsListAdapter);
    }

    @Override
    public void initlisten() {
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                refreshData(1);
            }
        });
    }

    public void toSearch(String keyword) {
        getNewsList.setTitle(keyword);
        getNewsList.setPage(1);
        requestData(getNewsList);
    }

    @Override
    public void refreshData(int page) {
        getNewsList.setPage(page);
        requestData(getNewsList);
    }

    private void requestData(GetNewsList getNewsList) {
        HttpUtil.getInstance().getNewsList(getNewsList).subscribe(
                str -> {
                    NewsList newsList = GsonUtil.fromJson(str, NewsList.class);
                    if (getNewsList.getPage() == 1) {
                        data.clear();
                        newsListAdapter.clearView();
                    }
                    NewsList.DataBeanX newsListData = newsList.getData();
                    if (newsListData != null) {
                        List<NewsList.DataBeanX.DataBean> list = newsListData.getData();
                        if (list != null && list.size() > 0) {
                            data.addAll(list);
                            newsListAdapter.notifyDataSetChanged();
                            binding.lvRecentNews.setLoading(true);
                            if (binding.tvEmpty.isShown()) {
                                binding.tvEmpty.setVisibility(View.GONE);
                            }
                        } else {
                            binding.lvRecentNews.setLoading(false);
                            if (!TextUtils.isEmpty(getNewsList.getTitle()) && getNewsList.getPage() == 1) {
                                binding.lvRecentNews.removefoot();
                                binding.tvEmpty.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        binding.lvRecentNews.setLoading(false);
                        if (!TextUtils.isEmpty(getNewsList.getTitle()) && getNewsList.getPage() == 1) {
                            binding.tvEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                    newsListAdapter.notifyDataSetChanged();
                }
        );
    }


}
