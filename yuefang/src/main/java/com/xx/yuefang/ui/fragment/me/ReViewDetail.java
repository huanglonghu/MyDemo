package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.ReviewDetailResponse;
import com.xx.yuefang.bean.UpdateReviewBody;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.FragmentReviewDetailBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;

public class ReViewDetail extends BaseFragment {


    private FragmentReviewDetailBinding binding;
    private UpdateReviewBody updateReviewBody;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review_detail, container, false);
        binding.setPresenter(Presenter.getInstance());
        initData();
        initlisten();
        return binding.getRoot();
    }


    @Override
    public void initData() {
        int id = getArguments().getInt("id");
        updateReviewBody = new UpdateReviewBody();
        updateReviewBody.setId(id);
        HttpUtil.getInstance().getReviewDetailById(id).subscribe(
                str -> {
                    ReviewDetailResponse reviewDetailResponse = GsonUtil.fromJson(str, ReviewDetailResponse.class);
                    ReviewDetailResponse.DataBean data = reviewDetailResponse.getData();
                    String avatar = data.getAvatar();
                    String salespersonAvatar = data.getSalespersonAvatar();
                    setImg(avatar, binding.head);
                    setImg(salespersonAvatar, binding.head2);
                    binding.setData(data);
                }
        );

    }


    private void setImg(String avater, ImageView imageView) {
        String url = ImagUtil.handleUrl(avater);
        Glide.with(getContext()).load(url).centerCrop().apply(RequestOptions.bitmapTransform(new CircleCrop())).apply(new RequestOptions().error(R.drawable.default_chat_head)).into(imageView);
    }


    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {

        binding.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateReviewBody.setState(2);
                HttpUtil.getInstance().updateRevieweState(updateReviewBody).subscribe(
                        str -> {
                            initData();
                        }
                );

            }
        });

        binding.refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.expendLayout.setVisibility(View.VISIBLE);
            }
        });

        binding.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refuseReason = binding.refuseReason.getText().toString();
                if (TextUtils.isEmpty(refuseReason)) {
                    Toast.makeText(getContext(), "请输入拒绝原因", Toast.LENGTH_SHORT).show();
                    return;
                }
                updateReviewBody.setRefuseReason(refuseReason);
                if (binding.cb.isChecked()) {
                    updateReviewBody.setState(4);
                } else {
                    updateReviewBody.setState(3);
                }
                HttpUtil.getInstance().updateRevieweState(updateReviewBody).subscribe(
                        str -> {
                            initData();
                        }
                );
            }
        });

    }
}
