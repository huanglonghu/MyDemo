package com.xx.yuefang.ui.fragment.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.FragmentHeadlineBinding;
import com.xx.yuefang.ui.adapter.NewsPageAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.fragment.news.RecentNews;
import com.xx.yuefang.ui.fragment.news.RecommendNews;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;

public class HeadLineFragment extends LazyLoadFragment {

    private FragmentHeadlineBinding binding;
    private ArrayList<BaseFragment> fragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_headline, null, false);
            setNeedReloadData(true);
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {}

    @Override
    public void initView() {
        fragments = new ArrayList<>();
        String[] titles = {"约房推荐", "最新资讯"};
        RecommendNews recommendNews = new RecommendNews();
        RecentNews recentNews = new RecentNews();
        fragments.add(recommendNews);
        fragments.add(recentNews);
        NewsPageAdapter newsPageAdapter = new NewsPageAdapter(getChildFragmentManager(), fragments, titles);
        binding.vp.setAdapter(newsPageAdapter);
        binding.vp.setCurrentItem(0, true);
        binding.tab.setViewPager(binding.vp);
        binding.search.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    @Override
    public void initlisten() {
        binding.searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchStr = binding.search.getText().toString();
                int currentItem = binding.vp.getCurrentItem();
                if (currentItem == 0) {
                    RecommendNews recommendNews = (RecommendNews) fragments.get(0);
                    recommendNews.toSearch(searchStr);
                } else if (currentItem == 1) {
                    RecentNews recentNews = (RecentNews) fragments.get(1);
                    recentNews.toSearch(searchStr);
                }
            }
        });


    }


    @Override
    protected void loadData() {
       for (int i=0;i<fragments.size();i++){
           fragments.get(i).initData();
       }
    }


}
