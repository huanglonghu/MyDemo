package com.xx.yuefang.ui.fragment.house;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.DynamicDetailBean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.FragmentDynamicBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.widget.EnterForDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

public class Dynamic extends BaseFragment {


    private FragmentDynamicBinding binding;
    private DynamicDetailBean.DataBean data;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dynamic, container, false);
        binding.setPresenter(Presenter.getInstance());
        initData();
        initlisten();
        return binding.getRoot();
    }


    @Override
    public void initData() {
        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        HttpUtil.getInstance().getDynamicDetail(id).subscribe(
                str -> {
                    DynamicDetailBean dynamicDetailBean = GsonUtil.fromJson(str, DynamicDetailBean.class);
                    data = dynamicDetailBean.getData();
                    if (data != null) {
                        String picture = data.getPicture();
                        if (!TextUtils.isEmpty(picture)) {
                            String[] pictures = picture.split(",");
                            for (int i = 0; i < pictures.length; i++) {
                                String p = pictures[i];
                                String url = ImagUtil.handleUrl(p);
                                if (!TextUtils.isEmpty(url)) {
                                    RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                                            imageBean -> {
                                                Bitmap bitmap = imageBean.getBitmap();
                                                int width = bitmap.getWidth();
                                                ImageView imageView = new ImageView(getContext());
                                                int b = YueFangApplication.getApplication().getWindownWidth();
                                                int height = bitmap.getHeight();
                                                int a = height * b / width;
                                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(b, a);
                                                imageView.setLayoutParams(layoutParams);
                                                imageView.setImageDrawable(new BitmapDrawable(bitmap));
                                                binding.imgcontainer.addView(imageView);
                                            }
                                    );



                                }
                            }

                        }

                    }
                }

        );
    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {
        binding.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterForDialog enterForDialog = new EnterForDialog(getContext(), data);
                enterForDialog.show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
    }

    @Override
    public void onStop() {
        super.onStop();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
    }
}
