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
import com.xx.yuefang.bean.NewsCommentDetailResponse;
import com.xx.yuefang.bean.NewsItemBean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.LayoutReplyComment4Binding;
import com.xx.yuefang.databinding.ListNewscommetDetailItemBinding;
import com.xx.yuefang.databinding.NewscommentDetailHeadBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.fragment.news.NewsCommentDetail;
import com.xx.yuefang.ui.widget.ReplayDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;
import org.json.JSONObject;
import java.util.List;

public class NewsCommentDetailAdapter extends BaseListAdapter {
    private ListView listView;
    private int type;

    public NewsCommentDetailAdapter(Context context, List datas, int res, ListView listView, int type) {
        super(context, datas, res);
        this.listView = listView;
        this.type = type;
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        NewsCommentDetailResponse.DataBean.NewsInfoCommentDtosBeanX dataBean = (NewsCommentDetailResponse.DataBean.NewsInfoCommentDtosBeanX) datas.get(position);
        View convertView;
        if (position == 0) {
            NewscommentDetailHeadBinding headBinding = DataBindingUtil.inflate(layoutInflater, R.layout.newscomment_detail_head, parent, false);
            String avatar = dataBean.getAvatar();
            setHead(headBinding.iv, avatar);
            headBinding.setCommentBean(dataBean);
            convertView = headBinding.getRoot();
            headBinding.setCount(datas.size() - 1);
            headBinding.setType(type);
            headBinding.rise.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        HttpUtil.getInstance().riseNewsComment(dataBean.getId()).subscribe(
                                str -> {
                                    if (dataBean.isIsRise()) {
                                        dataBean.setRise(dataBean.getRise() - 1);
                                    } else {
                                        dataBean.setRise(dataBean.getRise() + 1);
                                    }
                                    dataBean.setIsRise(!dataBean.isIsRise());
                                    headBinding.setCommentBean(dataBean);
                                }
                        );
                    }
                    return false;
                }
            });
            headBinding.lookAll.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        NewsCommentDetail newsCommentDetail = new NewsCommentDetail();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", dataBean.getId());
                        newsCommentDetail.setArguments(bundle);
                        Presenter.getInstance().step2fragment(newsCommentDetail, "newsCommentDetail");
                    }
                    return false;
                }
            });
        } else {
            ListNewscommetDetailItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
            binding.setBean(dataBean);
            convertView = binding.getRoot();
            String avatar = dataBean.getAvatar();
            setHead(binding.iv, avatar);
            List<NewsItemBean> newsInfoCommentDtos = dataBean.getNewsInfoCommentDtos();
            if (newsInfoCommentDtos != null && newsInfoCommentDtos.size() > 0) {
                for (int i = 0; i < newsInfoCommentDtos.size(); i++) {
                    NewsItemBean newsItemBean = newsInfoCommentDtos.get(i);
                    addNewsItem(layoutInflater, binding, newsItemBean, position);
                }
            }
            binding.rise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpUtil.getInstance().riseNewsComment(dataBean.getId()).subscribe(
                            str -> {
                                if (dataBean.isIsRise()) {
                                    dataBean.setRise(dataBean.getRise() - 1);
                                } else {
                                    dataBean.setRise(dataBean.getRise() + 1);
                                }
                                dataBean.setIsRise(!dataBean.isIsRise());
                                binding.setBean(dataBean);
                            }
                    );
                }
            });
            binding.reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReplayDialog replayDialog = new ReplayDialog(context, new ClickSureListener() {
                        @Override
                        public void click(EditText e) {
                            HttpUtil.getInstance().replayComment(dataBean.getId(), e.getText().toString()).subscribe(
                                    str -> {
                                        JSONObject jsonObject = new JSONObject(str);
                                        int errcode = jsonObject.getInt("Errcode");
                                        if (errcode == 0) {
                                            Toast.makeText(context, "回复成功", Toast.LENGTH_SHORT).show();
                                            e.setText("");
                                            JSONObject data = jsonObject.getJSONObject("Data");
                                            String s = data.toString();
                                            NewsItemBean bean = GsonUtil.fromJson(s, NewsItemBean.class);
                                            addNewsItem(layoutInflater, binding, bean, position);
                                            int height = binding.getRoot().getHeight();
                                            listView.setSelectionFromTop(position, -height);
                                        } else {
                                            String message = jsonObject.getString("Message");
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                        }
                    }, "回复" + dataBean.getUserName());
                    replayDialog.show();
                }
            });
        }
        return convertView;
    }

    private void addNewsItem(LayoutInflater layoutInflater, ListNewscommetDetailItemBinding binding, NewsItemBean newsItemBean, int position) {
        LayoutReplyComment4Binding itemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_reply_comment4, binding.containerReply, false);
        itemBinding.setBean(newsItemBean);
        String path = newsItemBean.getAvatar();
        setHead(itemBinding.iv, path);
        itemBinding.reply.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ReplayDialog replayDialog = new ReplayDialog(context, new ClickSureListener() {
                        @Override
                        public void click(EditText e) {
                            HttpUtil.getInstance().replayComment(newsItemBean.getId(), e.getText().toString()).subscribe(
                                    str -> {
                                        JSONObject jsonObject = new JSONObject(str);
                                        int errcode = jsonObject.getInt("Errcode");
                                        if (errcode == 0) {
                                            Toast.makeText(context, "回复成功", Toast.LENGTH_SHORT).show();
                                            e.setText("");
                                            JSONObject data = jsonObject.getJSONObject("Data");
                                            String s = data.toString();
                                            NewsItemBean bean = GsonUtil.fromJson(s, NewsItemBean.class);
                                            addNewsItem(layoutInflater, binding, bean, position);
                                            int height = binding.getRoot().getHeight();
                                            listView.setSelectionFromTop(position, -height);
                                        } else {
                                            String message = jsonObject.getString("Message");
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                        }
                    }, "回复" + newsItemBean.getUserName());
                    replayDialog.show();
                }
                return false;
            }
        });
        binding.containerReply.addView(itemBinding.getRoot());
        itemBinding.rise.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    HttpUtil.getInstance().riseNewsComment(newsItemBean.getId()).subscribe(
                            str -> {
                                if (newsItemBean.isIsRise()) {
                                    newsItemBean.setRise(newsItemBean.getRise() - 1);
                                } else {
                                    newsItemBean.setRise(newsItemBean.getRise() + 1);
                                }
                                newsItemBean.setIsRise(!newsItemBean.isIsRise());
                                itemBinding.setBean(newsItemBean);
                            }
                    );

                }
                return false;
            }
        });
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
