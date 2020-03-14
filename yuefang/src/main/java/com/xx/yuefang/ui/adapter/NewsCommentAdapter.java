package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.NewsCommentBean;
import com.xx.yuefang.bean.NewsItemBean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.LayoutReplyCommentBinding;
import com.xx.yuefang.databinding.ListNewscommetItemBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.fragment.news.NewsCommentDetail;
import com.xx.yuefang.ui.widget.ReplyWindow;
import com.xx.yuefang.util.ImagUtil;
import java.util.List;

public class NewsCommentAdapter extends BaseAdapter {

    private ReplyWindow replyWindow;
    private Context context;
    private List<NewsCommentBean> datas;
    private int res;
    private final LayoutInflater inflater;

    public NewsCommentAdapter(Context context, List<NewsCommentBean> datas, int res, ReplyWindow replyWindow) {
        this.context = context;
        this.datas = datas;
        this.res = res;
        this.replyWindow = replyWindow;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListNewscommetItemBinding binding = DataBindingUtil.inflate(inflater, res, parent, false);
        NewsCommentBean dataBean = datas.get(position);
        binding.setBean(dataBean);
        String avatar = dataBean.getAvatar();
        String url = ImagUtil.handleUrl(avatar);
        List<NewsItemBean> newsInfoCommentDtos = dataBean.getNewsInfoCommentDtos();
        if (newsInfoCommentDtos != null && newsInfoCommentDtos.size() > 0) {
            LayoutReplyCommentBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.layout_reply_comment, binding.containerReply, false);
            NewsItemBean newsItemBean = newsInfoCommentDtos.get(0);
            itemBinding.setBean(newsItemBean);
            itemBinding.name.setText(dataBean.getUserName());
            binding.containerReply.addView(itemBinding.getRoot());
            itemBinding.setCount(dataBean.getNumber());
            itemBinding.lookAllReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsCommentDetail newsCommentDetail = new NewsCommentDetail();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", dataBean.getId());
                    newsCommentDetail.setArguments(bundle);
                    Presenter.getInstance().step2fragment(newsCommentDetail, "newsCommentDetail");

                }
            });
        }

        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(context).getBitmap(url).subscribe(
                    imageBean -> {
                        Bitmap bitmap = imageBean.getBitmap();
                        Drawable drawable = ImagUtil.circle(bitmap);
                        binding.head.setBackground(drawable);
                    }
            );
        }

        binding.rise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.getInstance().riseNewsComment(dataBean.getId()).subscribe(
                        str -> {
                            if (dataBean.isIsRise()) {
                                dataBean.setRise(dataBean.getRise() - 1);
                                dataBean.setIsRise(false);
                            } else {
                                dataBean.setRise(dataBean.getRise() + 1);
                                dataBean.setIsRise(true);
                            }
                            binding.setBean(dataBean);
                        }
                );
            }
        });



        binding.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出输入框回复
                replyWindow.hint("回复" + dataBean.getUserName(), 2, dataBean.getId());
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        convertView = binding.getRoot();
        return convertView;

    }
}
