package com.xx.yuefang.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.xx.yuefang.ui.fragment.MainFragment;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.ui.fragment.main.HeadLineFragment;
import com.xx.yuefang.ui.fragment.main.MeFragment;
import com.xx.yuefang.ui.fragment.news.RecentNews;
import com.xx.yuefang.ui.fragment.news.RecommendNews;
import com.xx.yuefang.util.LogUtil;

import java.util.List;

public abstract class LazyLoadFragment extends BaseFragment {

    private boolean isVisibleToUser;
    private boolean isViewCreated;
    private boolean isDataLoaded;
    public boolean isVisibleToUser() {
        return isVisibleToUser;
    }

    public void setVisibleToUser(boolean visibleToUser) {
        isVisibleToUser = visibleToUser;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        tryLoadData();

    }

    public void isVisible2User(boolean isVisibleToUser) {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            tryLoadData();
        }
        isVisible2User(isVisibleToUser);
    }


    private boolean isParentVisible() {//判断父fragment是否可见
        Fragment parentFragment = getParentFragment();
        return parentFragment == null || (parentFragment instanceof LazyLoadFragment && ((LazyLoadFragment) parentFragment).isVisibleToUser()) ||
                parentFragment instanceof MainFragment;
    }

    // 实现具体的数据请求逻辑
    protected abstract void loadData();

    private void tryLoadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible() && (!isDataLoaded || isNeedReloadData)) {
            loadData();
            isDataLoaded = true;
            dispatchParentVisableState();
        }
    }


    private void dispatchParentVisableState() {//让子fragment加载数据
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }

        for (int i = 0; i < fragments.size(); i++) {
            Fragment child = fragments.get(i);
            if (child instanceof LazyLoadFragment && ((LazyLoadFragment) child).isVisibleToUser()) {
                ((LazyLoadFragment) child).tryLoadData();
            }
        }
    }


    public void dispatchParentHideState() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (int i = 0; i < fragments.size(); i++) {
            Fragment child = fragments.get(i);
            if (child instanceof LazyLoadFragment && ((LazyLoadFragment) child).isVisibleToUser) {
                child.setUserVisibleHint(false);
            }
        }
    }

    private boolean isNeedReloadData;

    public boolean isNeedReloadData() {
        return isNeedReloadData;
    }

    public void setNeedReloadData(boolean needReloadData) {
        isNeedReloadData = needReloadData;
    }
}
