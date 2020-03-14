package com.xx.yuefang.ui.fragment.house.houseDetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.xx.yuefang.R;
import com.xx.yuefang.constant.HttpParam;
import com.xx.yuefang.databinding.FragmentPictureDetailBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.ImagePreViewAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.LogUtil;

public class PictureDetail extends BaseFragment {

    private FragmentPictureDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_picture_detail, container, false);
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        String[] urls = getArguments().getStringArray("urls");
        for (int i=0;i<urls.length;i++){
            String url = urls[i];
            if(!url.contains("http:")){
              urls[i]=HttpParam.baseUrl+"/"+url;
            }
        }
        binding.setCount(urls.length);
        ImagePreViewAdapter imagePreViewAdapter = new ImagePreViewAdapter(getContext(), urls);
        binding.viewPager.setAdapter(imagePreViewAdapter);
        int index = getArguments().getInt("index");
        binding.viewPager.setCurrentItem(index);
    }

    @Override
    public void initView() {}

    @Override
    public void initlisten() {

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                binding.setIndex(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });

        binding.back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Presenter.getInstance().back();
                }
                return false;
            }
        });
    }
}
