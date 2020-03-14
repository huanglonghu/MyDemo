package com.xx.yuefang.ui.fragment.house;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentHousedetailBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.customview.ObservableScrollView;
import com.xx.yuefang.ui.wrapper.LocationEnvironmenWrapper;
import com.xx.yuefang.ui.wrapper.Wrapper;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.RudenessScreenHelper;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

public class HouseDetailFragment extends LazyLoadFragment implements ObservableScrollView.OnObservableScrollViewListener {

    private FragmentHousedetailBinding binding;
    private PremisesDetail.DataBean data;
    private LinearLayout containerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            setNeedReloadData(true);
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_housedetail, container, false);
            binding.setPresenter(Presenter.getInstance());
            initView();
            initlisten();
            UserBean userBean = UserOption.getInstance().querryUser();
            if (userBean != null) {
                binding.setUserType(userBean.getUserType());
            }
            Bundle bundle = getArguments();
            boolean isFirst = bundle.getBoolean("isFirst");
            if (isFirst) {
                addView();
            }
            boolean initData = bundle.getBoolean("initData");
            if (initData) {
                loadData();
            }
        }
        return binding.getRoot();
    }


    public void addView() {
        if (!binding.vs.isInflated()) {
            ViewStub vs = binding.vs.getViewStub();
            containerView = (LinearLayout) vs.inflate();
        }
    }


    public void initData() {
        binding.houseDetailToolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HouseDetailContainer parentFragment = (HouseDetailContainer) getParentFragment();
                parentFragment.onKeyDown();
            }
        });
    }

    @Override
    public void initView() {
        //获取标题栏高度
        ViewTreeObserver viewTreeObserver = binding.top.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.top.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = binding.top.getHeight() - binding.houseDetailToolbar.rl.getHeight();//这里取的高度应该为图片的高度-标题栏
                //注册滑动监听
                binding.srcollView.setOnObservableScrollViewListener(HouseDetailFragment.this);
            }
        });

        if (Presenter.getInstance().hasNotchScreen(getActivity())) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(binding.houseDetailToolbar.rl.getLayoutParams());
            int v = (int) RudenessScreenHelper.dp2px(getContext(), 50);
            layoutParams.height = 100 + v;
            binding.houseDetailToolbar.rl.setLayoutParams(layoutParams);
        }
    }

    private int mHeight;

    @Override
    public void initlisten() {
    }

    private int top;

    @Override
    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
        this.top = t;
        changeTop(t);
    }

    private void changeTop(int t) {
        if (t <= 0) {
            //顶部图处于最顶部，标题栏透明
            binding.setShow(false);
            binding.houseDetailToolbar.rl.setBackgroundColor(Color.argb(0, 247, 247, 247));
        } else if (t > 0 && t < mHeight) {
            //滑动过程中，渐变
            binding.setShow(false);
            float scale = (float) t / mHeight;//算出滑动距离比例
            float alpha = (255 * scale);//得到透明度
            binding.houseDetailToolbar.rl.setBackgroundColor(Color.argb((int) alpha, 247, 247, 247));
        } else {
            //过顶部图区域，标题栏定色
            if (!binding.getShow()) {
                binding.setShow(true);
                binding.houseDetailToolbar.rl.setBackgroundColor(Color.argb(255, 247, 247, 247));
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
    }


    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
        changeTop(top);
        if (binding != null) {
            if (containerView != null) {
                LocationEnvironmenWrapper wrapper = containerView.findViewById(R.id.map);
                wrapper.onResume();
            }
        }
    }

    private boolean isAret;

    @Override
    protected void loadData() {
        isVisible2User(true);
        boolean inflated = binding.vs.isInflated();
        if (!inflated) {
            addView();
        }
        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        HttpUtil.getInstance().getPremisesDetailById(id).subscribe(
                str -> {
                    PremisesDetail premisesDetail = GsonUtil.fromJson(str, PremisesDetail.class);
                    data = premisesDetail.getData();
                    if (data != null) {
                        //从收藏、
                        isAret = bundle.getBoolean("isAret");
                        if (isAret && binding.getBean() == null) {
                            Presenter.getInstance().oneKeyYueFang(data);
                        }
                        int childCount = containerView.getChildCount();
                        binding.top.initData(data);
                        for (int i = 0; i < childCount; i++) {
                            Wrapper wrapper = (Wrapper) containerView.getChildAt(i);
                            wrapper.initData(data);
                        }
                        binding.setBean(data);
                    }
                }
        );


    }


    @Override
    public void isVisible2User(boolean isVisibleToUser) {
        if (binding != null) {
            if (!isVisibleToUser) {
                binding.top.stopPlayInVisiable();
            } else {
                binding.top.startPlayVisiable();
            }
        }
    }

}
