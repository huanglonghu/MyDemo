package com.xx.yuefang.ui.fragment.house.houseDetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.CurrentCommentBean;
import com.xx.yuefang.bean.PremisesCommentDetailBean;
import com.xx.yuefang.databinding.FragmentCommentReplay2Binding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.ReplayListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.widget.ReplayDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;
import java.util.ArrayList;
import java.util.List;

public class PremisesCommentDetail2 extends BaseFragment {

    private FragmentCommentReplay2Binding binding;
    private List<PremisesCommentDetailBean.DataBean.PremisesCommentDtosBean> premisesCommentDtos;
    private ReplayListAdapter replayListAdapter;
    private int id;
    private ReplayDialog replayDialog;
    private PremisesCommentDetailBean.DataBean data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment_replay2, container, false);
            binding.setPresenter(Presenter.getInstance());
            initView();
            initData();
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            HttpUtil.getInstance().getPremisesCommentByReplyId(id).subscribe(
                    str -> {
                        setData(str);
                    }
            );
        }
    }

    private void setData(String str) {
        PremisesCommentDetailBean bean = GsonUtil.fromJson(str, PremisesCommentDetailBean.class);
        data = bean.getData();
        CurrentCommentBean currentComment = data.getCurrentComment();
        initHeadData();
        if (currentComment != null) {
            String s = GsonUtil.toJson(currentComment);
            PremisesCommentDetailBean.DataBean.PremisesCommentDtosBean premisesCommentDtosBean = GsonUtil.fromJson(s, PremisesCommentDetailBean.DataBean.PremisesCommentDtosBean.class);
            premisesCommentDtos.add(premisesCommentDtosBean);
            replayListAdapter.notifyDataSetChanged();
        }
        binding.setCommentBean(data);
    }


    private void initHeadData() {
        PremisesCommentDetailBean.DataBean.PremisesCommentDtosBean premisesCommentDtosBean = new PremisesCommentDetailBean.DataBean.PremisesCommentDtosBean();
        premisesCommentDtosBean.setUserName(data.getUserName());
        premisesCommentDtosBean.setContent(data.getContent());
        premisesCommentDtosBean.setAvatar(data.getAvatar());
        premisesCommentDtosBean.setId(data.getId());
        premisesCommentDtosBean.setIsRise(data.isIsRise());
        premisesCommentDtosBean.setIsComment(data.isIsComment());
        premisesCommentDtosBean.setRise(data.getRise());
        premisesCommentDtosBean.setCreationTime(data.getCreationTime());
        premisesCommentDtos.add(0, premisesCommentDtosBean);
    }

    @Override
    public void initView() {
        premisesCommentDtos = new ArrayList<>();
        replayListAdapter = new ReplayListAdapter(getContext(), premisesCommentDtos, R.layout.list_comment_replay_item, binding.listReplay,2);
        binding.listReplay.setAdapter(replayListAdapter);
    }

    @Override
    public void initlisten() {}

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
