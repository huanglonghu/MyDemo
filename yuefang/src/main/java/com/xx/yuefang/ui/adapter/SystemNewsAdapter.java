package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.SystemNews;
import com.xx.yuefang.databinding.ListSystemnewsItemBinding;
import com.xx.yuefang.databinding.SystemnewsTv1Binding;
import com.xx.yuefang.databinding.SystemnewsTv2Binding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.fragment.house.houseDetail.PremisesCommentDetail2;
import com.xx.yuefang.ui.fragment.house.houseDetail.QADetail;
import com.xx.yuefang.ui.fragment.me.AppointCommentFirst;
import com.xx.yuefang.ui.fragment.me.ReViewDetail;
import com.xx.yuefang.ui.fragment.news.NewsCommentDetail2;
import java.util.List;

public class SystemNewsAdapter extends BaseListAdapter {
    private int userType;

    public SystemNewsAdapter(Context context, List datas, int res, int userType) {
        super(context, datas, res);
        this.userType = userType;
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        ListSystemnewsItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
        binding.setAdapter(this);
        SystemNews.DataBeanX.DataBean bean = (SystemNews.DataBeanX.DataBean) datas.get(position);
        int newsType = bean.getNewsType();
        String content = null;

        switch (newsType) {
            case 1:
                content = bean.getContext();
                break;
            case 2:
                content = bean.getContext() + "回复了您的楼盘评论";
                break;
            case 3:
                if (userType == 3 || userType == 2) {
                    content = bean.getContext() + "对您的楼盘提问了";
                } else if (userType == 4) {
                    content = bean.getContext() + "回复了您的楼盘提问";
                }
                break;
            case 4:
                content = bean.getContext() + "点评了您的预约";
                break;
            case 5:
                content = bean.getContext() + "回复了您的评论";
                break;
            case 6:
                content = bean.getContext() + "申请了一条绑定/解绑记录，请点击查看";
                break;
        }
        if (newsType != 1) {
            SystemnewsTv2Binding binding2 = DataBindingUtil.inflate(layoutInflater, R.layout.systemnews_tv2, binding.chatMessage, false);
            binding2.setContent(content);
            binding.chatMessage.addView(binding2.getRoot());
        } else {
            SystemnewsTv1Binding binding1 = DataBindingUtil.inflate(layoutInflater, R.layout.systemnews_tv1, binding.chatMessage, false);
            binding1.setContent(content);
            binding.chatMessage.addView(binding1.getRoot());
        }
        binding.setNews(bean);
        binding.setContent(content);
        View view = binding.getRoot();
        view.setTag(bean);
        binding.chatMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    skip(bean);
                }
                return false;
            }
        });
        return view;
    }


    public void skip(SystemNews.DataBeanX.DataBean bean) {
        switch (bean.getNewsType()) {
            case 2://跳转到评论详情
            {
                PremisesCommentDetail2 comment_replay = new PremisesCommentDetail2();
                Bundle bundle = new Bundle();
                bundle.putInt("id", bean.getRelatedId());
                comment_replay.setArguments(bundle);
                Presenter.getInstance().step2fragment(comment_replay, "commentReplay2");
            }
            break;
            case 3://跳转到问答详情
            {
                int relatedId = bean.getRelatedId();
                QADetail replayQuestion = new QADetail();
                Bundle bundle = new Bundle();
                bundle.putInt("id", relatedId);
                replayQuestion.setArguments(bundle);
                Presenter.getInstance().step2fragment(replayQuestion, "replay");
            }
            break;
            case 4: {
                AppointCommentFirst commentDetail = new AppointCommentFirst();
                Bundle bundle = new Bundle();
                bundle.putInt("id", bean.getRelatedId());
                commentDetail.setArguments(bundle);
                Presenter.getInstance().step2fragment(commentDetail, "commentDetail");
            }
            break;
            case 5:
            {
                NewsCommentDetail2 newsCommentDetail = new NewsCommentDetail2();
                Bundle bundle = new Bundle();
                bundle.putInt("id", bean.getRelatedId());
                newsCommentDetail.setArguments(bundle);
                Presenter.getInstance().step2fragment(newsCommentDetail, "newsCommentDetail2");
            }
                break;
            case 6:
                ReViewDetail reViewDetail = new ReViewDetail();
                int id = bean.getRelatedId();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                reViewDetail.setArguments(bundle);
                Presenter.getInstance().step2fragment(reViewDetail, "reviewDetail");
                break;

        }
    }

}
