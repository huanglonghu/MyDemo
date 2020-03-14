package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesCommentDetailBean;
import com.xx.yuefang.bean.PremisesCommentSonsBean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.CommentDetailHeadBinding;
import com.xx.yuefang.databinding.LayoutReplyComment3Binding;
import com.xx.yuefang.databinding.ListCommentReplayItemBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.fragment.house.houseDetail.PremisesCommentDetail;
import com.xx.yuefang.ui.widget.ReplayDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;

import org.json.JSONObject;

import java.util.List;

public class ReplayListAdapter extends BaseListAdapter {
    private ListView listView;
    private int type;

    public ReplayListAdapter(Context context, List datas, int res, ListView listView, int type) {
        super(context, datas, res);
        this.listView = listView;
        this.type = type;
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        View convertView;
        PremisesCommentDetailBean.DataBean.PremisesCommentDtosBean replayBean = (PremisesCommentDetailBean.DataBean.PremisesCommentDtosBean) datas.get(position);
        if (position == 0) {
            CommentDetailHeadBinding headBinding = DataBindingUtil.inflate(layoutInflater, R.layout.comment_detail_head, null, false);
            String avatar = replayBean.getAvatar();
            setHead(headBinding.iv, avatar);
            headBinding.setCommentBean(replayBean);
            headBinding.setCount(datas.size() - 1);
            headBinding.setType(type);
            headBinding.rise.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        HttpUtil.getInstance().commentRise(replayBean.getId()).subscribe(
                                str -> {
                                    //刷新时间
                                    boolean isRise = replayBean.isIsRise();
                                    if (isRise) {
                                        replayBean.setRise(replayBean.getRise() - 1);
                                    } else {
                                        replayBean.setRise(replayBean.getRise() + 1);
                                    }
                                    replayBean.setIsRise(!isRise);
                                    headBinding.setCommentBean(replayBean);
                                }
                        );

                    }
                    return false;
                }
            });
            headBinding.lookAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PremisesCommentDetail premisesCommentDetail = new PremisesCommentDetail();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", replayBean.getId());
                    premisesCommentDetail.setArguments(bundle);
                    Presenter.getInstance().step2fragment(premisesCommentDetail, "premisesCommentDetail");
                }
            });
            convertView = headBinding.getRoot();
        } else {
            ListCommentReplayItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
            binding.setReplayBean(replayBean);
            String avatar = replayBean.getAvatar();
            setHead(binding.iv, avatar);
            List<PremisesCommentSonsBean> premisesCommentSonDtos = replayBean.getPremisesCommentSonDtos();
            if (premisesCommentSonDtos != null && premisesCommentSonDtos.size() > 0) {
                for (int i = 0; i < premisesCommentSonDtos.size(); i++) {
                    PremisesCommentSonsBean premisesCommentSonDtosBean = premisesCommentSonDtos.get(i);
                    addReplyItem(premisesCommentSonDtosBean, layoutInflater, position, binding);
                }
            }
            binding.reply.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        UserBean userBean = UserOption.getInstance().querryUser();
                        if (userBean == null) {
                            Presenter.getInstance().step2Fragment("login");
                        } else {
                            //刷新界面
                            ReplayDialog replayDialog = new ReplayDialog(context, new ClickSureListener() {
                                @Override
                                public void click(EditText e) {
                                    HttpUtil.getInstance().replyPremisesComment(replayBean.getId(), e.getText().toString()).subscribe(
                                            str -> {
                                                //刷新界面
                                                JSONObject jo = new JSONObject(str);
                                                int errcode = jo.getInt("Errcode");
                                                if (errcode == 0) {
                                                    Toast.makeText(context, "回复成功", Toast.LENGTH_SHORT).show();
                                                    e.setText("");
                                                    JSONObject js = jo.getJSONObject("Data");
                                                    String s = js.toString();
                                                    PremisesCommentSonsBean data = GsonUtil.fromJson(s, PremisesCommentSonsBean.class);
                                                    if (data != null) {
                                                        addReplyItem(data, layoutInflater, position, binding);
                                                        int height = binding.getRoot().getHeight();
                                                        listView.setSelectionFromTop(position, -height);
                                                    }
                                                } else {
                                                    String message = jo.getString("Message");
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                    );
                                }
                            }, "回复" + replayBean.getUserName());
                            replayDialog.show();
                        }
                    }
                    return false;
                }
            });

            binding.rise.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        HttpUtil.getInstance().commentRise(replayBean.getId()).subscribe(
                                str -> {
                                    //刷新时间
                                    boolean isRise = replayBean.isIsRise();
                                    if (isRise) {
                                        replayBean.setRise(replayBean.getRise() - 1);
                                    } else {
                                        replayBean.setRise(replayBean.getRise() + 1);
                                    }
                                    replayBean.setIsRise(!isRise);
                                    binding.setReplayBean(replayBean);
                                }
                        );
                    }
                    return false;
                }
            });
            convertView = binding.getRoot();
        }
        return convertView;
    }

    public View addReplyItem(PremisesCommentSonsBean premisesCommentSonDtosBean, LayoutInflater layoutInflater, int position, ListCommentReplayItemBinding binding) {
        LayoutReplyComment3Binding itemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_reply_comment3, binding.containerReply, false);
        itemBinding.setBean(premisesCommentSonDtosBean);
        View chid = itemBinding.getRoot();
        binding.containerReply.addView(chid);
        String path = premisesCommentSonDtosBean.getAvatar();
        setHead(itemBinding.iv, path);
        itemBinding.reply.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    UserBean userBean = UserOption.getInstance().querryUser();
                    if (userBean == null) {
                        Presenter.getInstance().step2Fragment("login");
                    } else {
                        //刷新界面
                        ReplayDialog replayDialog = new ReplayDialog(context, new ClickSureListener() {
                            @Override
                            public void click(EditText e) {
                                HttpUtil.getInstance().replyPremisesComment(premisesCommentSonDtosBean.getId(), e.getText().toString()).subscribe(
                                        str -> {
                                            JSONObject jo = new JSONObject(str);
                                            int errcode = jo.getInt("Errcode");
                                            if (errcode == 0) {
                                                Toast.makeText(context, "回复成功", Toast.LENGTH_SHORT).show();
                                                e.setText("");
                                                JSONObject js = jo.getJSONObject("Data");
                                                String s = js.toString();
                                                PremisesCommentSonsBean data = GsonUtil.fromJson(s, PremisesCommentSonsBean.class);
                                                if (data != null) {
                                                    addReplyItem(data, layoutInflater, position, binding);
                                                    int height = binding.getRoot().getHeight();
                                                    listView.setSelectionFromTop(position, -height);
                                                }
                                            } else {
                                                String message = jo.getString("Message");
                                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );
                            }
                        }, "回复" + premisesCommentSonDtosBean.getUserName());
                        replayDialog.show();
                    }
                }
                return false;
            }
        });
        itemBinding.rise.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    HttpUtil.getInstance().commentRise(premisesCommentSonDtosBean.getId()).subscribe(
                            str -> {
                                //刷新时间
                                boolean isRise = premisesCommentSonDtosBean.isIsRise();
                                if (isRise) {
                                    premisesCommentSonDtosBean.setRise(premisesCommentSonDtosBean.getRise() - 1);
                                } else {
                                    premisesCommentSonDtosBean.setRise(premisesCommentSonDtosBean.getRise() + 1);
                                }
                                premisesCommentSonDtosBean.setIsRise(!isRise);
                                itemBinding.setBean(premisesCommentSonDtosBean);
                            }
                    );
                }
                return false;
            }
        });
        return chid;
    }

    private void setHead(ImageView iv, String avatar) {
        String url = ImagUtil.handleUrl(avatar);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(context).getBitmap(url).subscribe(
                    imageBean -> {
                        Bitmap bitmap = imageBean.getBitmap();
                        if (bitmap == null) {
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_chat_head);
                        }
                        Drawable drawable = ImagUtil.circle(bitmap);
                        iv.setBackground(drawable);
                    }
            );
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_chat_head);
            Drawable drawable = ImagUtil.circle(bitmap);
            iv.setBackground(drawable);
        }
    }

}
