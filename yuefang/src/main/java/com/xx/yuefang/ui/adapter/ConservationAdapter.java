package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.ImageBean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.ListConversationItemBinding;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.TimeUtil;
import java.util.HashMap;
import java.util.List;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

public class ConservationAdapter extends BaseListAdapter {

    private HashMap<String, Integer> cvMap = new HashMap<>();

    public ConservationAdapter(Context context, List<Conversation> datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        ListConversationItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_conversation_item, parent, false);
        Conversation conversation = (Conversation) datas.get(position);
        binding.setConversation(conversation);
        String targetId = conversation.getTargetId();
        cvMap.put(targetId, position);
        String s = TimeUtil.formatTime3(conversation.getLastMsgDate());
        binding.date.setText(s);
        String title = conversation.getTitle();
        binding.name.setText(title);
        ContentType contentType = conversation.getLatestType();
        if (contentType == ContentType.voice) {
            binding.content.setText("[声音]");
        } else if (contentType == ContentType.image) {
            binding.content.setText("[图片]");
        } else if (contentType == ContentType.text) {
            binding.content.setText(conversation.getLatestText());
        } else if (contentType == ContentType.custom) {
            binding.content.setText("[图文]");
        }else if(contentType==ContentType.location){
            binding.content.setText("[位置]");
        }
        initHead(binding.head, conversation.getTargetId());
        return binding.getRoot();
    }


    public boolean isConversationExit(String target_id) {
        return cvMap.containsKey(target_id);
    }

    public int getIndexByTargetId(String target_id) {
        return cvMap.get(target_id);
    }


    public void refreshView(int index, Conversation cv) {
        View view = getView(index, null, null);
        ListConversationItemBinding binding = DataBindingUtil.findBinding(view);
        binding.setConversation(cv);
    }

    private void initHead(ImageView imageView, String id) {
        Bitmap bitmap = RxImageLoader.with(context).getBitmapFromMemory(id);
        if (bitmap == null) {
            JMessageClient.getUserInfo(id, new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                        @Override
                        public void gotResult(int i, String s, Bitmap bitmap) {
                            if (bitmap != null) {
                                ImageBean imageBean = new ImageBean(bitmap, id);
                                RxImageLoader.with(context).catche(imageBean);
                                Drawable drawable = ImagUtil.circle(bitmap);
                                imageView.setBackground(drawable);
                            } else {
                                imageView.setBackgroundResource(R.drawable.default_chat_head);
                            }
                        }
                    });
                }
            });
        } else {
            Drawable drawable = ImagUtil.circle(bitmap);
            imageView.setBackground(drawable);
        }
    }

}
