package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.model.LatLng;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.CustomMessage;
import com.xx.yuefang.bean.ImageBean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.ListChatReceiveCustomItemBinding;
import com.xx.yuefang.databinding.ListChatReceiveImgItemBinding;
import com.xx.yuefang.databinding.ListChatReceiveLocationItemBinding;
import com.xx.yuefang.databinding.ListChatReceiveVoiceItemBinding;
import com.xx.yuefang.databinding.ListChatReceivemsgItemBinding;
import com.xx.yuefang.databinding.ListChatSendCustomItemBinding;
import com.xx.yuefang.databinding.ListChatSendImgItemBinding;
import com.xx.yuefang.databinding.ListChatSendLocationItemBinding;
import com.xx.yuefang.databinding.ListChatSendVoiceItemBinding;
import com.xx.yuefang.databinding.ListChatSendmsgItemBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.customview.chat.ChatView;
import com.xx.yuefang.ui.fragment.chat.ChatLocationMsg2Fragment;
import com.xx.yuefang.ui.fragment.chat.EmotionUtils;
import com.xx.yuefang.ui.fragment.chat.SpanStringUtils;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.ui.widget.BigImg;
import com.xx.yuefang.util.BitmapFillet;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.RudenessScreenHelper;
import com.xx.yuefang.util.TimeUtil;
import com.xx.yuefang.util.UIUtils;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.LocationContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.enums.MessageStatus;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


public class ChatListAdapter extends BaseChatListAdapter {

    private LayoutInflater inflater;
    private HashMap<Integer, File> soundMap = new HashMap<>();
    private ClickSureListener clickSureListener;
    private HashMap<Integer, Long> dateMap;

    public ChatListAdapter(Context context, List datas, int res, ClickSureListener clickSureListener, HashMap dateMap) {
        super(context, datas, res);
        this.clickSureListener = clickSureListener;
        this.dateMap = dateMap;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        Message msg = (Message) datas.get(position);
        MessageDirect direct = msg.getDirect();
        MessageContent content = msg.getContent();
        long createTime = msg.getCreateTime();
        ContentType contentType = content.getContentType();
        typeMap.put(position, contentType);
        if (dateMap.get(position) == null) {
            dateMap.put(position, createTime);
        }
        LogUtil.log("============content================" + msg.toJson());
        View view = null;
        if (contentType == ContentType.text) {
            TextContent textContent = (TextContent) content;
            String text = textContent.getText();
            if (direct == MessageDirect.send) {
                ListChatSendmsgItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_sendmsg_item, parent, false);
                setDate(binding.date, createTime, position);
                initHead(binding.ivHead, msg);
                view = binding.getRoot();
                binding.content.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE,
                        context, binding.content, text));
            } else if (direct == MessageDirect.receive) {
                ListChatReceivemsgItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_receivemsg_item, parent, false);
                binding.content.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE,
                        context, binding.content, text));
                setDate(binding.date, createTime, position);
                initHead(binding.ivHead, msg);
                view = binding.getRoot();
            }
        } else if (contentType == ContentType.custom) {
            CustomContent customContent = (CustomContent) content;
            String s = customContent.toJson();
            CustomMessage.DataBean customMessage = GsonUtil.fromJson(s, CustomMessage.DataBean.class);
            String picture = customMessage.getListPicture();
            String url = ImagUtil.handleUrl(picture);
            if (direct == MessageDirect.send) {
                ListChatSendCustomItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_send_custom_item, parent, false);
                if (!TextUtils.isEmpty(url)) {
                    RxImageLoader.with(context).getBitmap(url).subscribe(
                            imageBean -> {
                                Bitmap bitmap = imageBean.getBitmap();
                                int i = UIUtils.dip2px(10);
                                Bitmap fillet = BitmapFillet.fillet(bitmap, i, BitmapFillet.CORNER_TOP);
                                binding.img.setBackground(new BitmapDrawable(context.getResources(), fillet));
                            }
                    );
                }
                setDate(binding.date, createTime, position);
                binding.setCustomMsg(customMessage);
                view = binding.getRoot();
                initHead(binding.ivHead, msg);
            } else if (direct == MessageDirect.receive) {
                ListChatReceiveCustomItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_receive_custom_item, parent, false);
                if (!TextUtils.isEmpty(url)) {
                    RxImageLoader.with(context).getBitmap(url).subscribe(
                            imageBean -> {
                                Bitmap bitmap = imageBean.getBitmap();
                                int i = UIUtils.dip2px(10);
                                Bitmap fillet = BitmapFillet.fillet(bitmap, i, BitmapFillet.CORNER_TOP);
                                binding.img.setBackground(new BitmapDrawable(context.getResources(), fillet));
                            }
                    );
                }
                setDate(binding.date, createTime, position);
                binding.setCustomMsg(customMessage);
                view = binding.getRoot();
                initHead(binding.ivHead, msg);
            }
            if (view != null) {
                View custommsg = view.findViewById(R.id.customMsg);
                custommsg.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            int id = customMessage.getId();
                            HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", id);
                            bundle.putBoolean("isFirst",true);
                            bundle.putBoolean("initData",true);
                            houseDetailFragment.setArguments(bundle);
                            Presenter.getInstance().step2fragment(houseDetailFragment, "houseDetailFragment");
                        }
                        return false;
                    }
                });
            }
        } else if (contentType == ContentType.voice) {
            if (direct == MessageDirect.send) {
                ListChatSendVoiceItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_send_voice_item, parent, false);
                view = binding.getRoot();
                handleVoiceMsg(binding.voiceMsg, position, msg, createTime, binding.sound, binding.duration, binding.ivHead, binding.date);
            } else if (direct == MessageDirect.receive) {
                ListChatReceiveVoiceItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_receive_voice_item, parent, false);
                view = binding.getRoot();
                handleVoiceMsg(binding.voiceMsg, position, msg, createTime, binding.sound, binding.duration, binding.ivHead, binding.date);
            }
        } else if (contentType == ContentType.location) {//定位
            LocationContent locationContent = (LocationContent) content;
            Number latitude = locationContent.getLatitude();//经度
            Number longitude = locationContent.getLongitude();//纬度
            String lable = locationContent.getAddress();//地址
            String address = null;
            String buildName = null;
            if (lable.contains("|")) {
                String[] split = lable.split("\\|");
                buildName = split[0];
                address = split[1];
            } else {
                address = lable;
            }
            LatLng latLng = gcj02_To_Bd09(latitude.doubleValue(), longitude.doubleValue());
            int width = (int) RudenessScreenHelper.pt2px(context, 470), height = (int) RudenessScreenHelper.pt2px(context, 170);
            String url = "http://api.map.baidu.com/staticimage?" + "width=" + width + "&height=" + height + "&center=" + latLng.latitude + "," + latLng.longitude +
                    "&zoom=18&markers=" + latLng.latitude + "," + latLng.longitude + "&markerStyles=m&&copyright=1";
            if (direct == MessageDirect.send) {
                ListChatSendLocationItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_send_location_item, parent, false);
                binding.adress.setText(address);
                binding.name.setText(buildName);
                setDate(binding.date, createTime, position);
                initHead(binding.ivHead, msg);
                RxImageLoader.with(context).load(url).into(binding.locationMap, 1);
                view = binding.getRoot();
            } else if (direct == MessageDirect.receive) {
                ListChatReceiveLocationItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_receive_location_item, parent, false);
                binding.adress.setText(address);
                binding.name.setText(buildName);
                initHead(binding.ivHead, msg);
                RxImageLoader.with(context).load(url).into(binding.locationMap, 1);
                //根据经纬度显示地图
                setDate(binding.date, createTime, position);
                view = binding.getRoot();
            }
            View locationMsg = view.findViewById(R.id.locationMsg);
            String finalAddress = address;
            locationMsg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        ChatLocationMsg2Fragment chatLocationMsgFragment = new ChatLocationMsg2Fragment();
                        Bundle bundle = new Bundle();
                        double[] doubles = new double[2];
                        doubles[0] = latitude.doubleValue();
                        doubles[1] = longitude.doubleValue();
                        bundle.putDoubleArray("position", doubles);
                        bundle.putString("adress", finalAddress);
                        bundle.putInt("type", 1);
                        chatLocationMsgFragment.setArguments(bundle);
                        Presenter.getInstance().step2fragment(chatLocationMsgFragment, "chatLocation");
                    }
                    return false;
                }
            });
        } else if (contentType == ContentType.image) {
            if (direct == MessageDirect.send) {
                ListChatSendImgItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_send_img_item, parent, false);
                view = binding.getRoot();
                handleImgMsg(msg, binding.img, binding.date, createTime, binding.ivHead, position);
            } else if (direct == MessageDirect.receive) {
                ListChatReceiveImgItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_chat_receive_img_item, parent, false);
                view = binding.getRoot();
                handleImgMsg(msg, binding.img, binding.date, createTime, binding.ivHead, position);
            }
        }

        if (direct == MessageDirect.send) {
            if (view != null) {
                ProgressBar progressBar = view.findViewById(R.id.img_loading);
                if (msg.getStatus() == MessageStatus.send_success) {
                    progressBar.setVisibility(View.GONE);
                }
                msgStatusOption(msg, progressBar);
            }
        }
        String targetID = msg.getFromID();
        String first = targetID.substring(0, 1);
        if ("3".equals(first) || "6".equals(first)) {
            ImageView head = view.findViewById(R.id.ivHead);
            head.setClickable(true);
            head.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        clickSureListener.clickSure();
                    }
                    return false;
                }
            });
        }
        return view;
    }

    private void msgStatusOption(Message msg, ProgressBar progressBar) {
        if (msg.getStatus() == MessageStatus.send_fail) {
            sendFailed(progressBar, msg);
        } else {
            msg.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        progressBar.setVisibility(View.GONE);
                    } else {
                        sendFailed(progressBar, msg);
                    }
                }
            });
        }
    }

    private void sendFailed(ProgressBar progressBar, Message msg) {
        Toast.makeText(context, "消息发送失败", Toast.LENGTH_SHORT).show();
        Drawable drawable = context.getResources().getDrawable(R.drawable.error);
        progressBar.setIndeterminateDrawable(null);
        progressBar.setBackground(drawable);
        progressBar.setClickable(true);
        progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //重新发送
                    JMessageClient.sendMessage(msg);
                    msgStatusOption(msg, progressBar);
                }
                return false;
            }
        });
    }

    public LatLng gcj02_To_Bd09(double gg_lat, double gg_lon) {
        double pi = 3.1415926535897932384626;
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new LatLng(bd_lon, bd_lat);
    }


    private void setDate(TextView tv, long createTime, int position) {
        if (position == 0) {
            String time = TimeUtil.formatTime3(createTime);
            tv.setText(time);
        } else {
            Long crateTime2 = dateMap.get(position - 1);
            long l = (createTime - crateTime2) / 1000;
            if (l >= 120) {
                String time = TimeUtil.formatTime3(createTime);
                tv.setText(time);
            } else {
                tv.setVisibility(View.GONE);
            }
        }

    }

    private int i;

    private void initHead(ImageView imageView, Message msg) {
        String fromID = msg.getFromID();
        Bitmap bitmap = RxImageLoader.with(context).getBitmapFromMemory(fromID);
        if (bitmap == null) {
            JMessageClient.getUserInfo(msg.getFromID(), new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                        @Override
                        public void gotResult(int i, String s, Bitmap bitmap) {
                            if (bitmap != null) {
                                ImageBean imageBean = new ImageBean(bitmap, msg.getFromID());
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


    private void handleVoiceMsg(ChatView voiceMsg, int position, Message msg, long createTime, ImageView ivSound, TextView tvDuration, ImageView ivHead, TextView tvDate) {
        VoiceContent voiceContent = (VoiceContent) msg.getContent();
        int duration = voiceContent.getDuration();
        MediaPlayer mediaPlayer = new MediaPlayer();
        voiceContent.downloadVoiceFile(msg, new DownloadCompletionCallback() {
            @Override
            public void onComplete(int i, String s, File file) {
                try {
                    soundMap.put(position, file);
                    mediaPlayer.setDataSource(file.getPath());
                    mediaPlayer.prepare();
                } catch (Exception e) {
                }
            }
        });
        if (!mediaPlayer.isPlaying()) {
            voiceMsg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        mediaPlayer.start();
                        AnimationDrawable animationDrawable = (AnimationDrawable) ivSound.getDrawable();
                        animationDrawable.start();//播放动画
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                animationDrawable.stop();
                                ivSound.setImageResource(R.drawable.play_animation);
                            }
                        });
                    }
                    return false;
                }
            });
        }
        tvDuration.setText(duration + "＂");
        initHead(ivHead, msg);
        setDate(tvDate, createTime, position);
    }

    private void handleImgMsg(Message msg, ImageView img, TextView tvDate, long createTime, ImageView ivHead, int position) {
        ImageContent imageContent = (ImageContent) msg.getContent();
        String localThumbnailPath = imageContent.getLocalThumbnailPath();
        Bitmap bitmap = BitmapFactory.decodeFile(localThumbnailPath);
        img.setImageBitmap(bitmap);
        imageContent.downloadOriginImage(msg, new DownloadCompletionCallback() {
            @Override
            public void onComplete(int i, String s, File file) {
                String path = file.getPath();
                Bitmap b = BitmapFactory.decodeFile(path);
                img.setTag(b);
            }
        });
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (img.getTag() != null) {
                        Bitmap b = (Bitmap) img.getTag();
                        BigImg bigImg = new BigImg(context, b);
                        bigImg.showAtLocation(v, Gravity.CENTER, 0, 0);
                    }
                }
                return false;
            }
        });
        setDate(tvDate, createTime, position);
        initHead(ivHead, msg);
    }


}



