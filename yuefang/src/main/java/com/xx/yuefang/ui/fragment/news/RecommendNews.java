package com.xx.yuefang.ui.fragment.news;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.ADList;
import com.xx.yuefang.bean.GetNewsList;
import com.xx.yuefang.bean.NewsList;
import com.xx.yuefang.databinding.FragmentRecommendBinding;
import com.xx.yuefang.databinding.VpBinnerBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.AdClickListener;
import com.xx.yuefang.ui.adapter.RecommendNewsAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.customview.MyViewPager;
import com.xx.yuefang.ui.customview.ScrollRefreshListView;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.util.BannerUtil;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class RecommendNews extends BaseFragment implements ScrollRefreshListView.RefreshData {

    private FragmentRecommendBinding binding;
    private List<NewsList.DataBeanX.DataBean> data;
    private RecommendNewsAdapter newsListAdapter;
    private VpBinnerBinding binnerBinding;
    private GetNewsList getNewsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend, container, false);
            binding.lvRecommendNews.setRefreshData(this);
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {
        binding.lvRecommendNews.setPage(1);
        binding.lvRecommendNews.removefoot();
        refreshData(1);
        initHead(binnerBinding.adContainer);
    }

    private int selectPosition;

    @Override
    public void initView() {
        getNewsList = new GetNewsList();
        getNewsList.setLimit(20);
        getNewsList.setNewsInfoType(1);
        data = new ArrayList<>();
        newsListAdapter = new RecommendNewsAdapter(getContext(), data, R.layout.list_news_item);
        binding.lvRecommendNews.setAdapter(newsListAdapter);
        binnerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.vp_binner, binding.lvRecommendNews, false);
        binnerBinding.adContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                int count = binnerBinding.circleLayout.getChildCount();
                int position = i % count;
                View child2 = binnerBinding.circleLayout.getChildAt(selectPosition);
                child2.setSelected(false);
                View child = binnerBinding.circleLayout.getChildAt(position);
                child.setSelected(true);
                selectPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        binding.lvRecommendNews.addHeaderView(binnerBinding.getRoot());
    }

    @Override
    public void initlisten() {
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                initData();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    public void toSearch(String keyword) {
        binding.lvRecommendNews.removefoot();
        getNewsList.setTitle(keyword);
        getNewsList.setPage(1);
        requestData(getNewsList);

    }


    private void initHead(MyViewPager viewPager) {
        HttpUtil.getInstance().getAdList().subscribe(
                str -> {
                    ADList adList = GsonUtil.fromJson(str, ADList.class);
                    List<ADList.DataBean> data = adList.getData();
                    if (data != null && data.size() > 0) {
                        String[] urls = new String[data.size()];
                        if (binnerBinding.circleLayout.getChildCount() != 0) {
                            binnerBinding.circleLayout.removeAllViews();
                        }
                        for (int i = 0; i < data.size(); i++) {
                            urls[i] = data.get(i).getPicture();
                            ImageView imageView = new ImageView(getContext());
                            if (i == 0) {
                                imageView.setSelected(true);
                            }
                            imageView.setImageResource(R.drawable.bg_banner_circle);
                            binnerBinding.circleLayout.addView(imageView);
                        }
                        BannerUtil bannerUtil = new BannerUtil();
                        bannerUtil.initBanner(urls, getContext(), viewPager, new AdClickListener() {
                                    @Override
                                    public void click(int position) {
                                        ADList.DataBean dataBean = data.get(position);
                                        int adInfoType = dataBean.getAdInfoType();
                                        switch (adInfoType) {
                                            case 1://楼盘
                                                HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("id",Integer.parseInt(dataBean.getJumpLink()));
                                                bundle.putBoolean("isFirst",true);
                                                bundle.putBoolean("initData",true);
                                                houseDetailFragment.setArguments(bundle);
                                                Presenter.getInstance().step2fragment(houseDetailFragment, "houseDetailFragment");

                                                break;
                                            case 2://跳进资讯详情
                                                NewsDeatil newsDeatil = new NewsDeatil();
                                                Bundle bundle1 = new Bundle();
                                                bundle1.putInt("id", Integer.parseInt(dataBean.getJumpLink()));
                                                newsDeatil.setArguments(bundle1);
                                                Presenter.getInstance().step2fragment(newsDeatil, "newsDeatil");
                                                break;
                                            case 3://链接
                                                String jumpLink = dataBean.getJumpLink();
                                                Link link = new Link();
                                                Bundle bundle2 = new Bundle();
                                                bundle2.putString("title", dataBean.getAdName());
                                                bundle2.putString("url", jumpLink);
                                                link.setArguments(bundle2);
                                                Presenter.getInstance().step2fragment(link, "link");
                                                break;
                                        }
                                    }
                                }
                                , true);
                    }
                });
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
                    NewsList.DataBeanX newList = newsList.getData();
                    if (data != null) {
                        List<NewsList.DataBeanX.DataBean> list = newList.getData();
                        if (list != null && list.size() > 0) {
                            data.addAll(list);
                            binding.lvRecommendNews.setLoading(true);
                            newsListAdapter.notifyDataSetChanged();
                            if (binding.tvEmpty.isShown()) {
                                binding.tvEmpty.setVisibility(View.GONE);
                            }
                        } else {
                            binding.lvRecommendNews.setLoading(false);
                            if (!TextUtils.isEmpty(getNewsList.getTitle()) && getNewsList.getPage() == 1) {
                                binding.lvRecommendNews.removefoot();
                                binding.tvEmpty.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        binding.lvRecommendNews.setLoading(false);
                        if (!TextUtils.isEmpty(getNewsList.getTitle()) && getNewsList.getPage() == 1) {
                            binding.tvEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                    newsListAdapter.notifyDataSetChanged();

                }
        );
    }
}
