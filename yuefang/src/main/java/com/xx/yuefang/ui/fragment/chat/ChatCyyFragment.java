package com.xx.yuefang.ui.fragment.chat;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.xx.yuefang.R;
import com.xx.yuefang.database.SystemDataOption;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.SystemData;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.ChatBottomCyyBinding;
import com.xx.yuefang.ui.adapter.CyyListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.house.Chat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatCyyFragment extends BaseFragment {

    private ChatBottomCyyBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_bottom_cyy, container, false);
        initView();
        return binding.getRoot();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ChatBottomMainFragment cbmf = (ChatBottomMainFragment) getParentFragment();
        UserBean userBean = UserOption.getInstance().querryUser();
        ArrayList<String> cyyList = new ArrayList<>();
        SystemData systemData = SystemDataOption.getInstance().querrySystemData();
        if (userBean.getUserType() == 3) {
            String userQuickChat = systemData.getSalespersonQuickChat();
            if (!TextUtils.isEmpty(userQuickChat)) {
                String[] split = userQuickChat.split("\\|");
                List<String> strings = Arrays.asList(split);
                cyyList.addAll(strings);
            }
        } else if (userBean.getUserType() == 4) {
            String salespersonQuickChat = systemData.getUserQuickChat();
            if (!TextUtils.isEmpty(salespersonQuickChat)) {
                String[] split = salespersonQuickChat.split("\\|");
                List<String> strings = Arrays.asList(split);
                cyyList.addAll(strings);
            }
        }
        CyyListAdapter cyyListAdapter = new CyyListAdapter(getContext(), cyyList, 0);
        binding.lvCyy.setAdapter(cyyListAdapter);
        binding.lvCyy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cyy = cyyList.get(position);
                cbmf.setCyy(cyy);
            }
        });

    }

    @Override
    public void initlisten() {}


}
