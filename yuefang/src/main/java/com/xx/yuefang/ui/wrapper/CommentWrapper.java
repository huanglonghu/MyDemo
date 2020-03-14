package com.xx.yuefang.ui.wrapper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.Comment;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.WrapperCommentBinding;
import com.xx.yuefang.databinding.WrapperCommentMemberBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.fragment.house.houseDetail.CommentOn;
import com.xx.yuefang.ui.fragment.house.houseDetail.UserComment;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class CommentWrapper extends Wrapper {

    private WrapperCommentBinding binding;

    public CommentWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    View initView() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wrapper_comment, this, false);
        return binding.getRoot();
    }

    @Override
    public void initData(PremisesDetail.DataBean data) {
        List<Comment> premisesComments = data.getPremisesComments();
        binding.setData(data);
        if (premisesComments != null && premisesComments.size() > 0) {
            binding.llExpend.removeAllViews();
            for (int i = 0; i < premisesComments.size(); i++) {
                if (i < 2) {
                    WrapperCommentMemberBinding b = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wrapper_comment_member, binding.llExpend, false);
                    Comment premisesCommentsBean = premisesComments.get(i);
                    String avatar = premisesCommentsBean.getAvatar();
                    String url = ImagUtil.handleUrl(avatar);
                    Glide.with(getContext()).load(url).centerCrop().apply(RequestOptions.bitmapTransform(new CircleCrop())).apply(new RequestOptions().error(R.drawable.default_chat_head)).into(b.iv);

                    b.setCommentBean(premisesCommentsBean);
                    binding.llExpend.addView(b.getRoot());
                }
            }
        }
        initListener(data);
    }

    private void initListener(PremisesDetail.DataBean data) {
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            binding.setUserType(userBean.getUserType());
        }
        binding.comment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.getUserType() == 4 || binding.getUserType() == 0) {
                    CommentOn commentOn = new CommentOn();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", data.getId());
                    bundle.putString("name", data.getPremisesName());
                    commentOn.setArguments(bundle);
                    Presenter.getInstance().step2fragment(commentOn, "commentOn");
                } else {
                    UserComment userComment = new UserComment();
                    userComment.setDatas(data.getId());
                    Presenter.getInstance().step2fragment(userComment, "userComment");
                }

            }
        });
        binding.lookAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserComment userComment = new UserComment();
                userComment.setDatas(data.getId());
                Presenter.getInstance().step2fragment(userComment, "userComment");
            }
        });
        binding.llExpend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserComment userComment = new UserComment();
                userComment.setDatas(data.getId());
                Presenter.getInstance().step2fragment(userComment, "userComment");
            }
        });
    }


}
