package com.xx.yuefang.ui.fragment.house.houseDetail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.QABean;
import com.xx.yuefang.bean.QaDetail;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentQaReplayBinding;
import com.xx.yuefang.databinding.HeadLayout3Binding;
import com.xx.yuefang.databinding.ListQaReplayItemBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.BaseListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.widget.ReplayDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class QADetail extends BaseFragment {
    private FragmentQaReplayBinding binding;
    private List<QABean.QuestionsAnswersDtosBean> list;
    private ReplayListAdapter adapter;
    private int id;
    private ReplayDialog replayDialog;
    private HeadLayout3Binding headLayout3Binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qa_replay, container, false);
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
        getWdById();
    }

    private void getWdById() {
        HttpUtil.getInstance().querryWdById(id).subscribe(
                str2 -> {
                    QaDetail qaDetail = GsonUtil.fromJson(str2, QaDetail.class);
                    QABean data = qaDetail.getData();
                    if (data != null) {
                        list.clear();
                        list.addAll(data.getQuestionsAnswersDtos());
                        adapter.clearView();
                        adapter.notifyDataSetChanged();
                        binding.setQaBean(data);
                        headLayout3Binding.setQaBean(data);
                    }
                }
        );
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        adapter = new ReplayListAdapter(getContext(), list, R.layout.list_qa_replay_item);
        binding.listReplay.setAdapter(adapter);
        headLayout3Binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.head_layout3, null, false);
        binding.listReplay.addHeaderView(headLayout3Binding.getRoot());
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            headLayout3Binding.setUserType(userBean.getUserType());
        }
    }

    @Override
    public void initlisten() {
        headLayout3Binding.replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replayDialog = new ReplayDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void click(EditText e) {
                        HttpUtil.getInstance().replay(id, e.getText().toString()).subscribe(
                                str -> {
                                    e.setText("");
                                    getWdById();
                                }
                        );
                    }
                }, "优质评论会被优先展示");
                replayDialog.show();
            }
        });
    }

    private class ReplayListAdapter extends BaseListAdapter {

        public ReplayListAdapter(Context context, List<QABean.QuestionsAnswersDtosBean> datas, int res) {
            super(context, datas, res);
        }

        @Override
        protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
            ListQaReplayItemBinding itemBinding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
            QABean.QuestionsAnswersDtosBean replay = (QABean.QuestionsAnswersDtosBean) datas.get(position);
            itemBinding.setReplayBean(replay);
            String avatar = replay.getAvatar();
            String url = ImagUtil.handleUrl(avatar);
            Glide.with(getContext()).load(url).centerCrop().apply(RequestOptions.bitmapTransform(new CircleCrop())).apply(new RequestOptions().error(R.drawable.default_chat_head)).into(itemBinding.iv);
            View root = itemBinding.getRoot();
            return root;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (replayDialog != null) {
            replayDialog.dismiss();
            replayDialog = null;
        }


    }
}
