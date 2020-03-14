package com.xx.yuefang.ui.fragment.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.SystemNews;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentNewsBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.ConservationAdapter;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.fragment.MainFragment;
import com.xx.yuefang.ui.fragment.house.Chat;
import com.xx.yuefang.ui.fragment.news.SystemNew;
import com.xx.yuefang.util.GsonUtil;
import com.meiqia.core.MQManager;
import com.meiqia.core.bean.MQMessage;
import com.meiqia.core.callback.OnGetMessageListCallback;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class NewsFragment extends LazyLoadFragment {

    private FragmentNewsBinding binding;
    private List<com.xx.yuefang.bean.SystemNews.DataBeanX.DataBean> list;
    private int newsCount1;
    private ConservationAdapter conservationAdapter;
    private List<Conversation> conversationList;
    private UserBean userBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            setNeedReloadData(true);
            conversationList = new ArrayList<>();
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, null, false);
            binding.setPresenter(Presenter.getInstance());
            initlisten();
        }
        JMessageClient.registerEventReceiver(this);
        UserInfo myInfo = JMessageClient.getMyInfo();
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            if (myInfo == null) {
                JMessageClient.login(userBean.getUserType() + "__" + userBean.getId(), "123456", new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        initConversation();
                    }
                });
            } else {
                initConversation();
            }
        }
        return binding.getRoot();
    }


    @Override
    public void initData() {
    }

    @Override
    public void initView() {
    }

    private void initConversation() {
        conversationList.clear();
        refreshChatNewsCount();
        conversationList.addAll(JMessageClient.getConversationList());
        if (conservationAdapter == null) {
            conservationAdapter = new ConservationAdapter(getContext(), conversationList, R.layout.list_conversation_item);
            binding.lvConversarion.setAdapter(conservationAdapter);
            binding.lvConversarion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Conversation conversation = conversationList.get(position);
                    Chat chat = new Chat();
                    Bundle bundle = new Bundle();
                    bundle.putString("converstaionName", conversation.getTitle());
                    bundle.putString("converationId", conversation.getTargetId());
                    chat.setArguments(bundle);
                    Presenter.getInstance().step2fragment(chat, "chat");
                }
            });
        } else {
            conservationAdapter.clearView();
            conservationAdapter.notifyDataSetChanged();
        }
    }

    private void refreshChatNewsCount() {
        showNewsCount();
    }

    @Override
    public void initlisten() {
        binding.converstation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean userBean = UserOption.getInstance().querryUser();
                if (userBean != null) {
                    Intent intent = new MQIntentBuilder(getContext())
                            // 相同的 id 会被识别为同一个顾客
                            .setCustomizedId(userBean.getId() + "")
                            .build();
                    getActivity().startActivityForResult(intent, 1001);
                } else {
                    Presenter.getInstance().step2Fragment("login");
                }
            }
        });

        binding.systemNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean userBean = UserOption.getInstance().querryUser();
                if (userBean != null) {
                    SystemNew systemNewsFragment = new SystemNew();
                    Presenter.getInstance().step2fragment(systemNewsFragment, "systemNews");
                } else {
                    Presenter.getInstance().step2Fragment("login");
                }

            }
        });

        UserObserver<EventData> userObserver = new UserObserver<EventData>() {
            @Override
            public void onUpdate(UserObservable<EventData> observable, EventData data) {
                UserBean userBean = UserOption.getInstance().querryUser();
                switch (data.getEventType()) {
                    case UserObservable.TYPE_SYSTEM_NEWS:
                        if (userBean != null) {
                            getSystemNews(userBean);
                        }
                        break;
                    case UserObservable.TYPE_CHAT_NEWS:
                        if (userBean != null) {
                            getKfNews();
                        }
                        break;
                    case UserObservable.TYPE_LOGINOUT:
                        binding.setNews(null);
                        binding.setNewsCount(0);
                        JMessageClient.logout();
                        refreshChatNewsCount();
                        if (conservationAdapter != null) {
                            conversationList.clear();
                            conservationAdapter.clearView();
                        }
                        break;
                    case UserObservable.TYPE_LOGIN_SUCCESS:
                        getSystemNews(userBean);
                        JMessageClient.login(userBean.getUserType() + "__" + userBean.getId(), "123456", new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                initConversation();
                            }
                        });
                        break;
                    case UserObservable.TYPE_DELETE_AGENT:
                        Bundle bundle = data.getData();
                        String conversationName = bundle.getString("conversationName");
                        JMessageClient.deleteSingleConversation(conversationName, "bfa1fb6c520555b907d69f34");
                        List<Conversation> conversationList = JMessageClient.getConversationList();
                        if (conservationAdapter != null) {
                            conversationList.clear();
                            refreshChatNewsCount();
                            conservationAdapter.clearView();
                            NewsFragment.this.conversationList.addAll(conversationList);
                            conservationAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        };
        UserObservable.getInstance().register(userObserver);
    }


    @Override
    protected void loadData() {
        userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            getKfNews();
            getSystemNews(userBean);
        }
    }

    private int newsCount2;


    private void getKfNews() {
        MQManager.getInstance(getContext()).getUnreadMessages(new OnGetMessageListCallback() {
            @Override
            public void onSuccess(List<MQMessage> list) {
                newsCount2 = list.size();
                binding.setNewsCount2(newsCount2);
                showNewsCount();
                if (list.size() > 0) {
                    MQMessage mqMessage = list.get(list.size() - 1);
                    binding.content2.setText(mqMessage.getContent());
                } else {
                    binding.content2.setText("");
                }
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });
    }

    private void showNewsCount() {
        newsCount3 = JMessageClient.getAllUnReadMsgCount();
        MainFragment main = (MainFragment) Presenter.getInstance().getFragment("Main");
        main.refreshNewsCount(newsCount1 + newsCount2 + newsCount3);
    }

    private void getSystemNews(UserBean userBean) {
        HttpUtil.getInstance().getSystemNews().subscribe(
                str -> {
                    SystemNews systemNews = GsonUtil.fromJson(str, SystemNews.class);
                    SystemNews.DataBeanX data = systemNews.getData();
                    if (data != null) {
                        newsCount1 = data.getCount();
                        binding.setNewsCount(newsCount1);
                        showNewsCount();
                        list = data.getData();
                        if (list.size() > 0) {
                            SystemNews.DataBeanX.DataBean dataBean = list.get(list.size() - 1);
                            String content = null;
                            switch (dataBean.getNewsType()) {
                                case 1:
                                    content = dataBean.getContext();
                                    break;
                                case 2:
                                    content = dataBean.getContext() + "回复了您的楼盘评论";
                                    break;
                                case 3:
                                    if (userBean.getUserType() == 3 || userBean.getUserType() == 2) {
                                        content = dataBean.getContext() + "对您的楼盘提问了";
                                    } else if (userBean.getUserType() == 4) {
                                        content = dataBean.getContext() + "回复了您的楼盘提问";
                                    }
                                    break;
                                case 4:
                                    content = dataBean.getContext() + "点评了您的预约";
                                    break;
                            }
                            dataBean.setContext(content);
                            binding.setNews(dataBean);
                        } else {
                            binding.content.setText("");
                        }
                    }
                }
        );
    }


    private int newsCount3;

    public void onEventMainThread(MessageEvent event) {
        Message msg = event.getMessage();
        UserInfo userInfo = (UserInfo) msg.getTargetInfo();
        String targetId = userInfo.getUserName();
        Conversation conv = JMessageClient.getSingleConversation(targetId, userInfo.getAppKey());
        refreshChatNewsCount();
        if (conv != null) {
            boolean conversationExit = conservationAdapter.isConversationExit(conv.getTargetId());
            LogUtil.log(conservationAdapter + "==============会话更新==============" + conversationExit);
            if (conversationExit) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int index = conservationAdapter.getIndexByTargetId(targetId);
                        conversationList.set(index, conv);
                        conservationAdapter.refreshView(index, conv);
                    }
                });
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        conversationList.add(conv);
                        conservationAdapter.notifyDataSetChanged();
                    }
                });
            }

        }
    }


}
