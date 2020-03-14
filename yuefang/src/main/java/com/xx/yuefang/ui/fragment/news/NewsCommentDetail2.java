package com.xx.yuefang.ui.fragment.news;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.CurrentCommentBean;
import com.xx.yuefang.bean.NewsCommentDetailResponse;
import com.xx.yuefang.databinding.LayoutNewscommentDetail2Binding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.NewsCommentDetailAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.GsonUtil;
import java.util.ArrayList;

public class NewsCommentDetail2 extends BaseFragment {

    private LayoutNewscommentDetail2Binding binding;
    private ArrayList<NewsCommentDetailResponse.DataBean.NewsInfoCommentDtosBeanX> datas;
    private NewsCommentDetailAdapter newsCommentDetailAdapter;
    private NewsCommentDetailResponse.DataBean data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_newscomment_detail2, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        int id = arguments.getInt("id");
        HttpUtil.getInstance().getNewsInfoCommentByReplyId(id).subscribe(
                str -> {
                    setData(str);
                }
        );
    }

    private void setData(String str) {
        NewsCommentDetailResponse newsCommentDetailResponse = GsonUtil.fromJson(str, NewsCommentDetailResponse.class);
        data = newsCommentDetailResponse.getData();
        if (data != null) {
            CurrentCommentBean currentComment = data.getCurrentComment();
            initHeadData();
            if (currentComment != null) {
                String s = GsonUtil.toJson(currentComment);
                NewsCommentDetailResponse.DataBean.NewsInfoCommentDtosBeanX beanX = GsonUtil.fromJson(s, NewsCommentDetailResponse.DataBean.NewsInfoCommentDtosBeanX.class);
                datas.add(beanX);
                newsCommentDetailAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initHeadData() {
        NewsCommentDetailResponse.DataBean.NewsInfoCommentDtosBeanX bean = new NewsCommentDetailResponse.DataBean.NewsInfoCommentDtosBeanX();
        bean.setIsRise(data.isIsRise());
        bean.setAvatar(data.getAvatar());
        bean.setComment(data.isComment());
        bean.setContent(data.getContent());
        bean.setCreationTime(data.getCreationTime());
        bean.setId(data.getId());
        bean.setRise(data.getRise());
        bean.setUserName(data.getUserName());
        datas.add(0, bean);
    }

    @Override
    public void initView() {
        datas = new ArrayList<>();
        newsCommentDetailAdapter = new NewsCommentDetailAdapter(getContext(), datas, R.layout.list_newscommet_detail_item, binding.lvNewsCommentReplay,2);
        binding.lvNewsCommentReplay.setAdapter(newsCommentDetailAdapter);
    }

    private UserObserver userObserver;

    @Override
    public void initlisten() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (userObserver != null) {
            UserObservable.getInstance().unregister(userObserver);
            userObserver = null;
        }
    }


}
