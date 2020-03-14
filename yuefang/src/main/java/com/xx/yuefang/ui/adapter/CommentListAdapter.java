package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.Comment;
import com.xx.yuefang.bean.NewsItemBean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.LayoutReplyComment2Binding;
import com.xx.yuefang.databinding.LayoutReplyCommentBinding;
import com.xx.yuefang.databinding.ListCommentItemBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.fragment.house.houseDetail.PremisesCommentDetail;
import com.xx.yuefang.ui.fragment.news.NewsCommentDetail;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class CommentListAdapter extends BaseListAdapter {
    private ClickSureListener clickSureListener;

    public CommentListAdapter(Context context, List<Comment> datas, int res, ClickSureListener clickSureListener) {
        super(context, datas, res);
        this.clickSureListener = clickSureListener;
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        ListCommentItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
        Comment commentsBean = (Comment) datas.get(position);
        binding.setCommentBean(commentsBean);
        String avatar = commentsBean.getAvatar();
        String url = ImagUtil.handleUrl(avatar);
        List<Comment.PremisesCommentDtosBean> premisesCommentDtos = commentsBean.getPremisesCommentDtos();
        if (premisesCommentDtos != null && premisesCommentDtos.size() > 0) {
            LayoutReplyComment2Binding itemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_reply_comment2, binding.containerReply, false);
            Comment.PremisesCommentDtosBean premisesCommentDtosBean = premisesCommentDtos.get(0);
            itemBinding.setBean(premisesCommentDtosBean);
            binding.containerReply.addView(itemBinding.getRoot());
            itemBinding.setCount(commentsBean.getNumber());
            itemBinding.lookAllReply.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        PremisesCommentDetail comment_replay = new PremisesCommentDetail();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", commentsBean.getId());
                        comment_replay.setArguments(bundle);
                        Presenter.getInstance().step2fragment(comment_replay, "commentReplay");
                    }
                    return false;
                }
            });
        }
        Glide.with(context).load(url).centerCrop().apply(RequestOptions.bitmapTransform(new CircleCrop())).apply(new RequestOptions().error(R.drawable.default_chat_head)).into(binding.iv);
        binding.rise.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    clickSureListener.rise(binding, position);
                }
                return false;
            }
        });

        binding.comment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    PremisesCommentDetail comment_replay = new PremisesCommentDetail();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", commentsBean.getId());
                    comment_replay.setArguments(bundle);
                    Presenter.getInstance().step2fragment(comment_replay, "commentReplay");
                }
                return false;
            }
        });
        return binding.getRoot();
    }

}
