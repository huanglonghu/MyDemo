package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.ReviewNumResponse;
import com.xx.yuefang.databinding.FragmentReviewBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.NewsPageAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.customview.switchView.SwitchView;
import com.xx.yuefang.util.GsonUtil;
import java.util.ArrayList;

public class Review extends LazyLoadFragment {

    private FragmentReviewBinding binding;
    private ArrayList<BaseFragment> fragments;
    private String[] titles;
    private ArrayList<String> originalTitles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false);
            binding.setPresenter(Presenter.getInstance());
            initView();
            initlisten();
        }
        initData();
        return binding.getRoot();
    }

    @Override
    public void initData() {
       getReviewNum();
    }

    public void getReviewNum() {
        int [] applyType;
        if (binding.switchReview.isChecked()) {
            applyType=new int[]{1,3};
        } else {
            applyType=new int[]{2};
        }
        HttpUtil.getInstance().getReviewNum(applyType).subscribe(
                str -> {
                    ReviewNumResponse reviewNumResponse = GsonUtil.fromJson(str, ReviewNumResponse.class);
                    ReviewNumResponse.DataBean data = reviewNumResponse.getData();
                    if (data != null) {
                        int ysh = data.getAuditedNum();
                        int dsh = data.getCheckPendingNum();
                        int all = dsh + ysh;
                        titles[0] = originalTitles.get(0) + "(" + all + ")";
                        titles[1] = originalTitles.get(1) + "(" + dsh + ")";
                        titles[2] = originalTitles.get(2) + "(" + ysh + ")";
                        binding.tab.setViewPager(binding.vp);
                    }
                }
        );
    }

    @Override
    public void initView() {
        originalTitles.add(0, "全部审核");
        originalTitles.add(1, "待审核");
        originalTitles.add(2, "已审核");
        titles = new String[3];
        for (int i = 0; i < originalTitles.size(); i++) {
            titles[i] = originalTitles.get(i);
        }
        binding.vp.setOffscreenPageLimit(3);
        fragments = new ArrayList<>();
        // 1待接单 3已接单 6已完成 2用户取消 4销售取消 5用户爽约 6已接待 7成交8确认成交
        for (int i = 0; i < titles.length; i++) {
            ReviewPage reviewPage = new ReviewPage();
            Bundle bundle = new Bundle();
            ArrayList<Integer> state = new ArrayList<>();
            switch (i) {
                case 0:
                    state.add(1);
                    state.add(2);
                    state.add(3);
                    state.add(4);
                    break;
                case 1:
                    state.add(1);
                    break;
                case 2:
                    state.add(2);
                    state.add(3);
                    state.add(4);
                    break;
            }
            bundle.putIntegerArrayList("state", state);
            ArrayList<Integer> applyTypes = new ArrayList<>();
            applyTypes.add(2);
            bundle.putIntegerArrayList("applyTypes", applyTypes);
            reviewPage.setArguments(bundle);
            fragments.add(reviewPage);
        }
        NewsPageAdapter pageAdapter = new NewsPageAdapter(getChildFragmentManager(), fragments, titles);
        binding.vp.setAdapter(pageAdapter);
        binding.vp.setOffscreenPageLimit(3);
        binding.tab.setViewPager(binding.vp);
    }


    @Override
    public void initlisten() {
        binding.switchReview.setOnClickCheckedListener(new SwitchView.onClickCheckedListener() {
            @Override
            public void onClick() {
                boolean checked = binding.switchReview.isChecked();
                int currentItem = binding.vp.getCurrentItem();
                for (int i = 0; i < fragments.size(); i++) {
                    ReviewPage reviewPage = (ReviewPage) fragments.get(i);
                    if (i == currentItem) {
                        reviewPage.switchChange(checked,true);
                    }else {
                        reviewPage.switchChange(checked,false);
                    }
                }


            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void loadData() {
    }


    public interface SwitchChangeListener {
        void switchChange(boolean isSwitched,boolean refreshData);
    }


}
