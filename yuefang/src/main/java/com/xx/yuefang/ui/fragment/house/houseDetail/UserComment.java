package com.xx.yuefang.ui.fragment.house.houseDetail;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.Comment;
import com.xx.yuefang.bean.CommentList;
import com.xx.yuefang.bean.UserCommentResponse;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.EmptyLayout2Binding;
import com.xx.yuefang.databinding.FragmentCommentListBinding;
import com.xx.yuefang.databinding.ListCommentItemBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.CommentListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class UserComment extends BaseFragment {

    private FragmentCommentListBinding binding;
    private CommentListAdapter commentListAdapter;
    private UserObserver<EventData> userObserver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment_list, container, false);
            binding.setPresenter(Presenter.getInstance());
            initView();
            initlisten();
        }
        initData();
        return binding.getRoot();
    }

    private int id;

    public void setDatas(int id) {
        this.id = id;
    }

    @Override
    public void initData() {
        HttpUtil.getInstance().getCommentList(id).subscribe(
                str -> {
                    CommentList commentList = GsonUtil.fromJson(str, CommentList.class);
                    CommentList.DataBeanX commentData = commentList.getData();
                    if (commentData != null) {
                        List<Comment> list = commentData.getData();
                        if (list != null && list.size() > 0) {
                            premisesComments.clear();
                            commentListAdapter.clearView();
                            premisesComments.addAll(list);
                            commentListAdapter.notifyDataSetChanged();
                        } else {
                            showEmpty();
                        }
                    } else {
                        showEmpty();
                    }
                }
        );
    }

    private void showEmpty() {
        View root = binding.getRoot();
        View view = ((ViewStub) root.findViewById(R.id.view_stub)).inflate();
        EmptyLayout2Binding emptyLayout2Binding = DataBindingUtil.findBinding(view);
        emptyLayout2Binding.setTip("暂无评论");
    }

    private List<Comment> premisesComments;

    @Override
    public void initView() {
        premisesComments = new ArrayList<>();
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            binding.setUserType(userBean.getUserType());
        }
        commentListAdapter = new CommentListAdapter(getContext(), premisesComments, R.layout.list_comment_item, new ClickSureListener() {
            @SuppressLint("CheckResult")
            @Override
            public void rise(ListCommentItemBinding binding, int position) {
                HttpUtil.getInstance().commentRise(premisesComments.get(position).getId()).subscribe(
                        str -> {
                            //刷新时间
                            Comment comment = premisesComments.get(position);
                            boolean isRise = comment.isIsRise();
                            if (isRise) {
                                comment.setRise(comment.getRise() - 1);
                            } else {
                                comment.setRise(comment.getRise() + 1);
                            }
                            comment.setIsRise(!isRise);
                            premisesComments.set(position, comment);
                            binding.setCommentBean(comment);
                        }
                );
            }
        });
        binding.commentList.setAdapter(commentListAdapter);
    }

    @Override
    public void initlisten() {
        binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentOn commentOn = new CommentOn();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                commentOn.setArguments(bundle);
                Presenter.getInstance().step2fragment(commentOn, "commentOn");
            }
        });

        if (userObserver == null) {
            userObserver = new UserObserver<EventData>() {
                @Override
                public void onUpdate(UserObservable<EventData> observable, EventData data) {
                    if (data.getEventType() == UserObservable.TYPE_USER_COMMENT_SUCCESS) {
                        Bundle bundle = data.getData();
                        String str = bundle.getString("commentResponse");
                        UserCommentResponse userCommentResponse = GsonUtil.fromJson(str, UserCommentResponse.class);
                        Comment comment = userCommentResponse.getData();
                        premisesComments.add(0, comment);
                        commentListAdapter.clearView();
                        commentListAdapter.notifyDataSetChanged();
                        binding.commentList.scrollTo(0, 0);
                    }
                }
            };
            UserObservable.getInstance().register(userObserver);
        }

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
