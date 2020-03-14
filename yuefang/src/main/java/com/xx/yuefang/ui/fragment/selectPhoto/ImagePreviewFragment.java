package com.xx.yuefang.ui.fragment.selectPhoto;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.PreviewPhotoBinding;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.ImagePreViewAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.customview.PinchImageView;
import java.util.ArrayList;

public class ImagePreviewFragment extends BaseFragment {

    private static final String EXTRA_PATHS = "paths";
    private static final String EXTRA_FINISH = "finish";
    private int mPosition;
    private PreviewPhotoBinding binding;
    private String[] urls;
    private int urlType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.preview_photo, container, false);
            initView();
            initData();
            initlisten();
        }
        return binding.getRoot();
    }

    public void initView() {
        urlType = getArguments().getInt("urlType");
        ArrayList<ImageView> views = new ArrayList<>();
        Bundle bundle = getArguments();
        urls = bundle.getStringArray("paths");
        for (int i = 0; i < urls.length; i++) {
            PinchImageView imageView = new PinchImageView(getContext());
            views.add(imageView);
        }
        ImagePreViewAdapter imagePreViewAdapter = new ImagePreViewAdapter(getContext(), urls);
        binding.viewpager.setAdapter(imagePreViewAdapter);
        int index = bundle.getInt("index");
        binding.setPath(urls[index]);
        binding.viewpager.setCurrentItem(index);
        binding.setIndex(index);
        binding.setCount(urls.length);
    }

    @Override
    public void initlisten() {

        binding.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.getInstance().back();
            }
        });

        binding.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventData eventData = new EventData(urlType);
                Bundle bundle = new Bundle();
                String path = binding.getPath();
                bundle.putString("path", path);
                eventData.setData(bundle);
                UserObservable.getInstance().notifyObservers(eventData);
                Presenter.getInstance().back();
                Presenter.getInstance().back();
            }
        });


        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int i) {
                binding.setIndex(i);
                binding.setPath(urls[i]);
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        binding.ivItemCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivItemCheck.setSelected(false);
            }
        });


    }


    public void initData() {

    }


}
