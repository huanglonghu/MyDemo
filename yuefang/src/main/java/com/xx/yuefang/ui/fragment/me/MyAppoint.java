package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.GetReservationBody;
import com.xx.yuefang.bean.ReservationNumResponse;
import com.xx.yuefang.databinding.FragmentMyappointBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.NewsPageAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.widget.SearchDialog2;
import com.xx.yuefang.util.GsonUtil;

import java.util.ArrayList;

public class MyAppoint extends LazyLoadFragment {
    private FragmentMyappointBinding binding;
    private ArrayList<BaseFragment> fragments;
    private String[] titles;
    private ArrayList<String> originalTitles = new ArrayList<>();
    private SearchDialog2 searchDialog2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_myappoint, container, false);
            binding.setPresenter(Presenter.getInstance());
            initView();
            initData();
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {
        GetReservationBody getReservationBody = new GetReservationBody();
        getAppointNum(getReservationBody);
    }

    public void getAppointNum(GetReservationBody getReservationBody) {
        HttpUtil.getInstance().getReservationNum(getReservationBody).subscribe(
                str -> {
                    ReservationNumResponse reservationNumResponse = GsonUtil.fromJson(str, ReservationNumResponse.class);
                    ReservationNumResponse.DataBean data = reservationNumResponse.getData();
                    if (data != null) {
                        int cancelledNum = data.getCancelledNum();
                        int completedNum = data.getCompletedNum();
                        int inProgressNum = data.getInProgressNum();
                        int waitingNum = data.getWaitingNum();
                        titles[0] = originalTitles.get(0) + "(" + waitingNum + ")";
                        titles[1] = originalTitles.get(1) + "(" + inProgressNum + ")";
                        titles[2] = originalTitles.get(2) + "(" + completedNum + ")";
                        titles[3] = originalTitles.get(3) + "(" + cancelledNum + ")";
                        binding.tab.setViewPager(binding.vp);
                    }
                }
        );
    }

    @Override
    public void initView() {
        originalTitles.add("待接单");
        originalTitles.add("进行中");
        originalTitles.add("已完成");
        originalTitles.add("已取消");
        titles = new String[4];
        for (int i = 0; i < originalTitles.size(); i++) {
            titles[i] = originalTitles.get(i);
        }
        binding.vp.setOffscreenPageLimit(4);
        fragments = new ArrayList<>();
        // 1待接单 3已接单 6已完成 2用户取消 4销售取消 5用户爽约 6已接待 7成交8确认成交
        for (int i = 0; i < titles.length; i++) {
            AppointPage appointPage = new AppointPage();
            Bundle bundle = new Bundle();
            ArrayList<Integer> state = new ArrayList<>();
            switch (i) {
                case 0:
                    state.add(1);
                    break;
                case 1:
                    state.add(3);
                    break;
                case 2:
                    state.add(6);
                    state.add(7);
                    state.add(8);
                    break;
                case 3:
                    state.add(2);
                    state.add(4);
                    state.add(5);
                    break;
            }
            bundle.putIntegerArrayList("state", state);
            appointPage.setArguments(bundle);
            fragments.add(appointPage);
        }
        NewsPageAdapter pageAdapter = new NewsPageAdapter(getChildFragmentManager(), fragments, titles);
        binding.vp.setAdapter(pageAdapter);
        binding.vp.setOffscreenPageLimit(1);
        binding.tab.setViewPager(binding.vp);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int reservationType = bundle.getInt("reservationType");
            switch (reservationType) {
                case 1:
                    binding.vp.setCurrentItem(0);
                    break;
                case 3:
                    binding.vp.setCurrentItem(1);
                    break;
                case 6:
                case 7:
                case 8:
                    binding.vp.setCurrentItem(2);
                    break;
                case 2:
                case 4:
                case 5:
                    binding.vp.setCurrentItem(3);
                    break;
            }
        }
    }


    @Override
    public void initlisten() {
        binding.employeeToolbar.search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int currentItem = binding.vp.getCurrentItem();
                if (searchDialog2 == null) {
                    searchDialog2 = new SearchDialog2((AppCompatActivity) getActivity(), new ClickSureListener() {
                        @Override
                        public void querry(int condionType, String condition, String startTime, String endTime) {
                            for (int i = 0; i < fragments.size(); i++) {
                                AppointPage appoint = (AppointPage) fragments.get(i);
                                if (i == currentItem) {
                                    appoint.search(condionType, condition, startTime, endTime, true);
                                } else {
                                    appoint.search(condionType, condition, startTime, endTime, false);
                                }
                            }
                        }
                    });
                }
                searchDialog2.show();
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
}
