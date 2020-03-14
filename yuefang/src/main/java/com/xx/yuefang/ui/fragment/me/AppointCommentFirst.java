package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointItemView;
import com.xx.yuefang.bean.DPtBean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.AppointCommentDetailFirstBinding;
import com.xx.yuefang.databinding.ItemCommentHeadBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.RudenessScreenHelper;

import java.util.List;

public class AppointCommentFirst extends BaseFragment {

    private AppointCommentDetailFirstBinding binding;
    private int id;
    private UserBean userBean;
    private DPtBean.DataBean dataBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.appoint_comment_detail_first, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
        }
        getCommentDetail();
    }

    private void getCommentDetail() {
        HttpUtil.getInstance().getCommentDetailById(id).subscribe(
                str -> {
                    DPtBean commentBean = GsonUtil.fromJson(str, DPtBean.class);
                    dataBean = commentBean.getData();
                    if (dataBean != null) {
                        String uContent = dataBean.getUContent();
                        float uScore = dataBean.getUScore();
                        float sScore = dataBean.getSScore();
                        String sContent = dataBean.getSContent();
                        String uAvatar = dataBean.getUAvatar();
                        String sAvatar = dataBean.getSAvatar();
                        AppointItemView appointItemView = initAppointItem(dataBean);
                        binding.setBean(appointItemView);
                        if (userBean.getUserType() == 4) {
                            if (TextUtils.isEmpty(uContent)) {
                                setToComment(appointItemView);
                            } else {
                                setContent1(uAvatar, uScore, uContent, binding.container1);
                            }
                            if (TextUtils.isEmpty(dataBean.getSContent())) {
                                setEmpty(binding.container2);
                            } else {
                                setContent1(sAvatar, sScore, sContent, binding.container2);
                            }
                        } else if (userBean.getUserType() == 3 || userBean.getUserType() == 6) {
                            if (TextUtils.isEmpty(sContent)) {
                                setToComment(appointItemView);
                            } else {
                                setContent1(sAvatar, sScore, sContent, binding.container1);
                            }
                            if (TextUtils.isEmpty(uContent)) {
                                setEmpty(binding.container2);
                            } else {
                                setContent1(uAvatar, uScore, uContent, binding.container2);
                            }
                        }
                    }
                }
        );
    }

    private void setToComment(AppointItemView appointItemView) {
        TextView tvComment = (TextView) getLayoutInflater().inflate(R.layout.tv_tocomment, binding.container1, false);
        binding.container1.addView(tvComment);
        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointCommentSecond appointCommentSecond = new AppointCommentSecond();
                appointCommentSecond.setData(appointItemView, userBean.getUserType(), dataBean);
                Presenter.getInstance().step2fragment(appointCommentSecond, "appointSecond");
            }
        });
    }

    private void setEmpty(ViewGroup parent) {
        ImageView imageView = new ImageView(getContext());
        int width = (int) RudenessScreenHelper.pt2px(getContext(), 260);
        int height = (int) RudenessScreenHelper.pt2px(getContext(), 210);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.empty2);
        parent.addView(imageView);
    }

    private void setContent1(String picture, float score, String content, ViewGroup parent) {
        ItemCommentHeadBinding itemBind1 = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_comment_head, binding.container1, false);
        itemBind1.ratingBar.setRating(score);
        itemBind1.content.setText(content);
        String url = ImagUtil.handleUrl(picture);
        Glide.with(getContext()).load(url).centerCrop().apply(RequestOptions.bitmapTransform(new CircleCrop())).apply(new RequestOptions().error(R.drawable.default_chat_head)).into(itemBind1.head);

        parent.addView(itemBind1.getRoot());
    }


    private AppointItemView initAppointItem(DPtBean.DataBean dataBean) {
        AppointItemView appointItemView = new AppointItemView();
        appointItemView.setAddress(dataBean.getAddress());
        appointItemView.setAveragePrice(dataBean.getAveragePrice() + "");
        appointItemView.setConstructionArea(dataBean.getConstructionArea());
        appointItemView.setPremisesName(dataBean.getPremisesName());
        appointItemView.setPropertyType(dataBean.getPropertyType());
        appointItemView.setRegion(dataBean.getRegion());
        appointItemView.setId(id);
        String picture = dataBean.getPicture();
        String url = ImagUtil.handleUrl(picture);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                    imageBean -> {
                        Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                        binding.appointItem.iv.setBackground(drawable);
                    }
            );
        }
        List<String> characteristics = dataBean.getCharacteristics();
        for (int i = 0; i < characteristics.size() + 1; i++) {
            if (i < 3) {
                TextView item;
                if (i == 0) {
                    item = (TextView) View.inflate(getContext(), R.layout.layout_character1, null);
                    item.setText(dataBean.getState());
                } else {
                    item = (TextView) View.inflate(getContext(), R.layout.layout_character2, null);
                    item.setText(characteristics.get(i - 1));
                }
                binding.appointItem.llexpend.addView(item, i);
            }
        }
        return appointItemView;
    }

    @Override
    public void initView() {
        userBean = UserOption.getInstance().querryUser();
        if (userBean.getUserType() == 4) {
            binding.tvObject.setText("销售员点评");
        } else if (userBean.getUserType() == 3 || userBean.getUserType() == 6) {
            binding.tvObject.setText("客户点评");
        }
    }


    @Override
    public void initlisten() {
    }

}
