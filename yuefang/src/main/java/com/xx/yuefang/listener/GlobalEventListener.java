package com.xx.yuefang.listener;

import android.content.Context;

import com.xx.yuefang.util.LogUtil;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Message;


/**
 * 在demo中对于通知栏点击事件和在线消息接收事件，我们都直接在全局监听
 */
public class GlobalEventListener {
    private Context appContext;

    public GlobalEventListener(Context context) {
        appContext = context;
        JMessageClient.registerEventReceiver(this);
    }

    public void onEvent(NotificationClickEvent event) {


    }

    public void onEvent(MessageEvent event) {
        Message message = event.getMessage();
        switch (message.getContentType()) {
            case text:
                break;
            case custom:
                break;
            case image:
                break;
            case voice:
                break;
        }

    }

}
