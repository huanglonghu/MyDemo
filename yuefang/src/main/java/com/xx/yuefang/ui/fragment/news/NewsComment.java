package com.xx.yuefang.ui.fragment.news;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.NewsCommentBean;
import com.xx.yuefang.bean.NewsCommentResponse;
import com.xx.yuefang.bean.NewsDetailBean;
import com.xx.yuefang.databinding.NewsCommentBinding;
import com.xx.yuefang.databinding.NewscommentHeaderBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.NewsCommentAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.customview.ScrollRefreshListView;
import com.xx.yuefang.ui.widget.ReplyWindow;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.RudenessScreenHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsComment extends BaseFragment implements ScrollRefreshListView.RefreshData {

    private NewsCommentBinding binding;
    private int id;
    private NewsCommentAdapter newsCommentAdapter;
    private ArrayList<NewsCommentBean> datas;
    private ReplyWindow replyWindow;
    private UserObserver<EventData> userObserver;
    private NewscommentHeaderBinding headerBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.news_comment, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        id = getArguments().getInt("id");
        binding.lvNewsComment.setRefreshData(this);
        refreshData(1);
    }

    @Override
    public void initView() {
        datas = new ArrayList<>();
        //刷新数据
        replyWindow = new ReplyWindow(getContext(), new ClickSureListener() {
            @Override
            public void click(String content, int type, int commentId) {
                if (type == 1) {
                    HttpUtil.getInstance().addNewsComment(id, content).subscribe(
                            str -> {
                                Toast.makeText(getContext(), "发送成功", Toast.LENGTH_SHORT).show();
                                //刷新数据
                                JSONObject jsonObject = new JSONObject(str);
                                JSONObject data = jsonObject.getJSONObject("Data");
                                NewsCommentBean newsCommentBean = GsonUtil.fromJson(data.toString(), NewsCommentBean.class);
                                datas.add(0, newsCommentBean);
                                headerBinding.setCount(datas.size());
                                newsCommentAdapter.notifyDataSetChanged();
                                replyWindow.replySuccess();
                            }
                    );
                } else if (type == 2) {
                    HttpUtil.getInstance().replayComment(commentId, content).subscribe(
                            str -> {
                                Toast.makeText(getContext(), "回复成功", Toast.LENGTH_SHORT).show();
                                refreshData(1);
                                replyWindow.replySuccess();
                            }
                    );
                }
            }
        });
        newsCommentAdapter = new NewsCommentAdapter(getContext(), datas, R.layout.list_newscommet_item, replyWindow);
        binding.lvNewsComment.setAdapter(newsCommentAdapter);
        headerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.newscomment_header, binding.lvNewsComment, false);
        NewsDetailBean.DataBean newsBean = (NewsDetailBean.DataBean) getArguments().getSerializable("newsInfo");
        headerBinding.setBean(newsBean);
        binding.lvNewsComment.addHeaderView(headerBinding.getRoot());
    }

    @Override
    public void initlisten() {
        userObserver = new UserObserver<EventData>() {
            @Override
            public void onUpdate(UserObservable<EventData> observable, EventData data) {
                switch (data.getEventType()) {
                    case UserObservable.TYPE_KEYBOARD_OUT:
                        Bundle bundle = data.getData();
                        int height = bundle.getInt("height");
                        float v = RudenessScreenHelper.pt2px(YueFangApplication.getApplication(), 320);
                        if (height < 200) {
                            keyboardHeight = 0;
                            if (replyWindow.isShowing()) {
                                replyWindow.update(0, 0, -1, -1);
                            }
                        } else {
                            keyboardHeight = height;
                            replyWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, height);
                            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                            lp.alpha = 0.6f;
                            getActivity().getWindow().setAttributes(lp);
                        }
                        break;
                }
            }
        };
        UserObservable.getInstance().register(userObserver);

        replyWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
                boolean empty = TextUtils.isEmpty(replyWindow.getHint());
                if (keyboardHeight > 0 && !empty) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyWindow.hint("输入评论", 1, 0);
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        });


        binding.zw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.getInstance().back();
            }
        });

    }

    private int keyboardHeight;


    @Override
    public void refreshData(int page) {
        if (page == 1 && datas.size() > 0) {
            datas.clear();
        }
        HttpUtil.getInstance().getNewsCommentList(id, page).subscribe(
                str -> {
                    NewsCommentResponse newsCommentResponse = GsonUtil.fromJson(str, NewsCommentResponse.class);
                    NewsCommentResponse.DataBeanX data = newsCommentResponse.getData();
                    if (data != null) {
                        List<NewsCommentBean> list = data.getData();
                        if (list != null && list.size() > 0) {
                            datas.addAll(list);
                            binding.lvNewsComment.setLoading(true);
                        } else {
                            binding.lvNewsComment.setLoading(false);
                        }
                        headerBinding.setCount(data.getCount());
                        newsCommentAdapter.notifyDataSetChanged();
                    } else {
                        binding.lvNewsComment.setLoading(false);
                    }
                }
        );
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (userObserver != null) {
            UserObservable.getInstance().unregister(userObserver);
            userObserver = null;
            if (replyWindow != null) {
                replyWindow.dismiss();
                replyWindow = null;
            }
        }
    }


}
