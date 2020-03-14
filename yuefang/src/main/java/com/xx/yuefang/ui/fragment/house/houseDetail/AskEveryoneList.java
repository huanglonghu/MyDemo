package com.xx.yuefang.ui.fragment.house.houseDetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.QABean;
import com.xx.yuefang.bean.QAList;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentAskEveryoneListBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.AskEveryoneListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;
import java.util.ArrayList;
import java.util.List;

public class AskEveryoneList extends BaseFragment {

    private FragmentAskEveryoneListBinding binding;
    private AskEveryoneListAdapter askEveryoneListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ask_everyone_list, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }


    private List<QABean> questionsAnswers;
    private int id;

    public void setDatas(int id) {
        this.id = id;
    }

    @Override
    public void initData() {
        HttpUtil.getInstance().getQaList(id, null).subscribe(
                str -> {
                    QAList qaList = GsonUtil.fromJson(str, QAList.class);
                    QAList.QABeanX data = qaList.getData();
                    if (data != null) {
                        questionsAnswers.clear();
                        questionsAnswers.addAll(data.getData());
                        askEveryoneListAdapter.notifyDataSetChanged();
                    }
                }
        );
    }

    @Override
    public void initView() {
        questionsAnswers = new ArrayList<>();
        askEveryoneListAdapter = new AskEveryoneListAdapter(getContext(), questionsAnswers, R.layout.list_ask_everyone_item);
        binding.qaList.setAdapter(askEveryoneListAdapter);

        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            binding.setUserType(userBean.getUserType());
        }
    }

    @Override
    public void initlisten() {
        binding.ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean userBean = UserOption.getInstance().querryUser();
                if(userBean!=null){
                    AskQuestion askQuestion = new AskQuestion();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    bundle.putString("name", "");
                    askQuestion.setArguments(bundle);
                    Presenter.getInstance().step2fragment(askQuestion, "askQuestion");
                }else {
                    Presenter.getInstance().step2Fragment("login");
                }
            }
        });

        binding.qaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                QADetail replayQuestion = new QADetail();
                Bundle bundle = new Bundle();
                bundle.putInt("id", questionsAnswers.get(position).getId());
                replayQuestion.setArguments(bundle);
                Presenter.getInstance().step2fragment(replayQuestion, "replay");
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),true);
    }

    @Override
    public void onStop(){
        super.onStop();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),false);
    }

    @Override
    public void onKeyDown() {
        super.onKeyDown();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStackImmediate("houseDetailContainer",0);
    }
}
