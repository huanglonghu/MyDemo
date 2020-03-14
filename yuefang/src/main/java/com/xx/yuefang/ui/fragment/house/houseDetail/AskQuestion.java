package com.xx.yuefang.ui.fragment.house.houseDetail;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.databinding.FragmentAskQuestionBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

public class AskQuestion extends BaseFragment {


    private FragmentAskQuestionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ask_question, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {}

    private int id;

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
    }

    @Override
    public void initlisten() {
        binding.publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.content.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(getContext(), "请输入提问内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtil.getInstance().ask(id, content).subscribe(
                        str -> {
                            Toast.makeText(getContext(), "提问成功", Toast.LENGTH_SHORT).show();
                            AskEveryoneList askEveryone= new AskEveryoneList();
                            askEveryone.setDatas(id);
                            Presenter.getInstance().step2fragment(askEveryone,"askEveryone");
                        }
                );
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),true);
    }

    @Override
    public void onStop() {
        super.onStop();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),false);
    }
}
