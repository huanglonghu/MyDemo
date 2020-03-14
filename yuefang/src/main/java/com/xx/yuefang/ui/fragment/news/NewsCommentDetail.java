package com.xx.yuefang.ui.fragment.news;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.CurrentCommentBean;
import com.xx.yuefang.bean.NewsCommentDetailResponse;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.LayoutHeadContainerBinding;
import com.xx.yuefang.databinding.LayoutNewscommentDetailBinding;
import com.xx.yuefang.databinding.NewscommentDetailHeadBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.NewsCommentDetailAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.widget.ReplayDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class NewsCommentDetail extends BaseFragment {

    private LayoutNewscommentDetailBinding binding;
    private ArrayList<NewsCommentDetailResponse.DataBean.NewsInfoCommentDtosBeanX> datas;
    private NewsCommentDetailAdapter newsCommentDetailAdapter;
    private NewsCommentDetailResponse.DataBean data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_newscomment_detail, container, false);
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
        HttpUtil.getInstance().getNewsCommentDetailById(id).subscribe(
                str -> {
                    setData(str);
                }
        );
    }

    private void setData(String str) {
        datas.clear();
        newsCommentDetailAdapter.clearView();
        NewsCommentDetailResponse newsCommentDetailResponse = GsonUtil.fromJson(str, NewsCommentDetailResponse.class);
        data = newsCommentDetailResponse.getData();
        binding.setBean(data);
        if (data != null) {
            initHeadData();
            List<NewsCommentDetailResponse.DataBean.NewsInfoCommentDtosBeanX> newsInfoCommentDtos = data.getNewsInfoCommentDtos();
            if (newsInfoCommentDtos != null && newsInfoCommentDtos.size() > 0) {
                datas.addAll(newsInfoCommentDtos);
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
        LogUtil.log("============userName============="+data.getUserName());
        datas.add(0, bean);
    }

    @Override
    public void initView() {
        datas = new ArrayList<>();
        newsCommentDetailAdapter = new NewsCommentDetailAdapter(getContext(), datas, R.layout.list_newscommet_detail_item, binding.lvNewsCommentReplay,1);
        binding.lvNewsCommentReplay.setAdapter(newsCommentDetailAdapter);
    }

    private UserObserver userObserver;

    @Override
    public void initlisten() {

        binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplayDialog replayDialog = new ReplayDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void click(EditText e) {
                        HttpUtil.getInstance().replayComment(data.getId(), e.getText().toString()).subscribe(
                                str -> {
                                    Toast.makeText(getContext(), "回复成功", Toast.LENGTH_SHORT).show();
                                    initData();
                                }
                        );
                    }
                }, "回复" + data.getUserName());
                replayDialog.show();
            }

        });

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
