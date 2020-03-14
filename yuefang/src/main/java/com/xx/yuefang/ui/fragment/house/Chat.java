package com.xx.yuefang.ui.fragment.house;

import android.content.Intent;
import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AgentBean;
import com.xx.yuefang.bean.CustomMessage;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentChatBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.ChatListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.chat.ChatBottomMainFragment;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.SoftHideKeyBoardUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class Chat extends BaseFragment {

    private FragmentChatBinding binding;
    private Conversation mConversation;
    private ChatListAdapter chatListAdapter;
    private List<Message> allMessage;
    private UserBean userBean;
    private AgentBean.DataBean data;
    private UserObserver<EventData> userObserver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
            initlisten();
            initView();
            initData();
            SoftHideKeyBoardUtil.setToggle(true);
        }
        return binding.getRoot();
    }

    private String converationId;

    private String converstaionName;

    @Override
    public void initData() {
        userBean = UserOption.getInstance().querryUser();
        int userType = userBean.getUserType();
        binding.setUserType(userType);
        Bundle bundle = getArguments();
        converstaionName = bundle.getString("converstaionName");
        converationId = bundle.getString("converationId");
        binding.salePersonName.setText(converstaionName);
        UserInfo myInfo = JMessageClient.getMyInfo();
        if (myInfo == null) {
            JMessageClient.login(userBean.getUserType() + "__" + userBean.getId(), "123456", new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        initConversationData(bundle);
                    }
                }
            });
        } else {
            initConversationData(bundle);
        }

        if (userType == 4) {
            String[] array = converationId.split("__");
            String a1 = array[0];
            String a2 = array[1];
            int salePersonType = Integer.parseInt(a1);
            int salePersonId = Integer.parseInt(a2);
            HttpUtil.getInstance().getAgentMsg(salePersonId, salePersonType).subscribe(
                    str -> {
                        AgentBean agentBean = GsonUtil.fromJson(str, AgentBean.class);
                        data = agentBean.getData();
                    }
            );
        }
        JMessageClient.registerEventReceiver(this);
    }

    private void initConversationData(Bundle bundle) {
        mConversation = Conversation.createSingleConversation(converationId);
        sendCustomMsg(bundle);
        mConversation.resetUnreadCount();
        initConvListAdapter();
    }


    public void onEventMainThread(MessageEvent event) {
        //获取事件发生的会话对象
        Message message = event.getMessage();
        String fromID = message.getFromID();
        if (fromID.equals(converationId)) {
            allMessage.add(message);
            chatListAdapter.notifyDataSetChanged();
        }

    }

    private void sendCustomMsg(Bundle bundle) {
        int id = bundle.getInt("premieseId");
        if (id != 0) {
            HttpUtil.getInstance().getPremisesMsg(id).subscribe(
                    str -> {
                        CustomMessage customMessage = GsonUtil.fromJson(str, CustomMessage.class);
                        CustomMessage.DataBean data = customMessage.getData();
                        HashMap<String, String> msgMap = new HashMap<>();
                        msgMap.put("ListPicture", data.getListPicture());
                        msgMap.put("Address", data.getAddress());
                        msgMap.put("UnitPrice", data.getUnitPrice());
                        msgMap.put("PremisesName", data.getPremisesName());
                        msgMap.put("ConstructionArea", data.getConstructionArea());
                        msgMap.put("Id", data.getId() + "");
                        sendMsg(mConversation.createSendCustomMessage(msgMap));
                    }
            );
        }
    }

    public void sendTextMessage(String sendContent) {
        sendMsg(mConversation.createSendTextMessage(sendContent));
    }

    private void sendMsg(Message message) {
        allMessage.add(message);
        chatListAdapter.notifyDataSetChanged();
        JMessageClient.sendMessage(message);
    }

    public void sendVoiceMessage(String filePath, int d) {
        try {
            File file = new File(filePath);
            sendMsg(mConversation.createSendVoiceMessage(file, d));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendImgMessage(String filePath) {
        try {
            File file = new File(filePath);
            ImageContent imageContent = new ImageContent(file);

            sendMsg(mConversation.createSendMessage(imageContent));
        } catch (Exception e) {

        }
    }

    public void sendLocationMessage(double latitude, double longitude, String addrStr) {
        Message locationMessage = mConversation.createLocationMessage(latitude, longitude, 18, addrStr);
        allMessage.add(locationMessage);
        JMessageClient.sendMessage(locationMessage);

    }

    @Override

    public void initView() {
        ChatBottomMainFragment chatBottomMainFragment = new ChatBottomMainFragment();
        chatBottomMainFragment.bindToContentView(binding.contentView);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.bottom_container, chatBottomMainFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void initlisten() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onKeyDown();
            }
        });
        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.getPhoneNumber()));//跳转到拨号界面
                getContext().startActivity(intent);
            }
        });
        binding.salePersonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step2SalepersonDetail();
            }
        });
        binding.lvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //判断底部视图是否开启
                if (onBottomCancleListener != null) {
                    onBottomCancleListener.bottomCancle();
                }
                return false;
            }
        });

        if (userObserver == null) {
            userObserver = new UserObserver<EventData>() {
                @Override
                public void onUpdate(UserObservable<EventData> observable, EventData data) {
                    if (data.getEventType() == UserObservable.TYPE_SELECT_LOCATION) {
                        Bundle bundle = data.getData();
                        double[] locations = bundle.getDoubleArray("location");
                        String adress = bundle.getString("address");
                        String name = bundle.getString("name");
                        LogUtil.log(name + "============sendLocation==========");
                        sendLocationMessage(locations[0], locations[1], name + "|" + adress);
                    } else if (data.getEventType() == UserObservable.TYPE_SELECT_PHOTO_CHAT) {
                        Bundle bundle = data.getData();
                        String path = bundle.getString("path");
                        sendImgMessage(path);

                    }
                }
            };
            UserObservable.getInstance().register(userObserver);
        }

    }


    public interface OnBottomCancleListener {

        void bottomCancle();
    }

    private OnBottomCancleListener onBottomCancleListener;


    public void setOnBottomCancleListener(OnBottomCancleListener onBottomCancleListener) {
        this.onBottomCancleListener = onBottomCancleListener;
    }

    private void step2SalepersonDetail() {
        if (data != null) {
            SalePersonDetail salePersonDetail = new SalePersonDetail();
            Bundle bundle = new Bundle();
            bundle.putSerializable("agent", data);
            salePersonDetail.setArguments(bundle);
            Presenter.getInstance().step2fragment(salePersonDetail, "salePersonDetail");
        }
    }


    private void initConvListAdapter() {
        HashMap<Integer, Long> map = new HashMap<>();
        allMessage = new ArrayList<>();
        if (mConversation != null) {
            List<Message> list = mConversation.getAllMessage();
            if (list != null && list.size() > 0) {
                allMessage.addAll(list);
            }
        }
        for (int i = 0; i < allMessage.size(); i++) {
            Message message = allMessage.get(i);
            long createTime = message.getCreateTime();
            map.put(i, createTime);
        }
        chatListAdapter = new ChatListAdapter(getContext(), allMessage, 0, new ClickSureListener() {
            @Override
            public void clickSure() {
                step2SalepersonDetail();
            }
        }, map);
        if (allMessage != null) {
            binding.lvChat.setAdapter(chatListAdapter);
            binding.lvChat.setSelection(chatListAdapter.getCount() - 1);
        }
        chatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                binding.lvChat.setSelection(chatListAdapter.getCount() - 1);
            }
        });
    }

    @Override
    public void onKeyDown() {
        if (onBottomCancleListener != null) {
            onBottomCancleListener.bottomCancle();
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();
        boolean isSelectSalePerson = getArguments().getBoolean("isSelectSalePerson");
        if (isSelectSalePerson) {
            fm.popBackStack("selectSaleMan", 1);
        } else {
            fm.popBackStack();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (userObserver != null) {
            UserObservable.getInstance().unregister(userObserver);
            userObserver = null;
        }
    }


}
