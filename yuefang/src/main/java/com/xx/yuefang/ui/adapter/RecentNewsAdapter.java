package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.NewsList;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.ListNewsItem2Binding;
import com.xx.yuefang.databinding.ListNewsItemBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.fragment.news.NewsDeatil;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.RudenessScreenHelper;
import java.util.List;

public class RecentNewsAdapter extends BaseListAdapter {

    public RecentNewsAdapter(Context context, List datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        NewsList.DataBeanX.DataBean dataBean = (NewsList.DataBeanX.DataBean) datas.get(position);
        String picture = dataBean.getPicture();
        View convertView = null;
        if (!TextUtils.isEmpty(picture)) {
            String[] pictures = picture.split(",");
            if (pictures.length == 1) {
                ListNewsItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
                convertView = binding.getRoot();
                String url = ImagUtil.handleUrl(picture);
                if (!TextUtils.isEmpty(url)) {
                    RxImageLoader.with(context).getBitmap(url).subscribe(
                            imageBean -> {
                                Bitmap bitmap = imageBean.getBitmap();
                                Drawable drawable = ImagUtil.conner2(bitmap);
                                binding.iv.setBackground(drawable);
                            }
                    );
                }
                binding.setNewsBean(dataBean);
            } else {
                ListNewsItem2Binding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_news_item2, parent, false);
                convertView = binding.getRoot();
                binding.setNewsBean(dataBean);
                for (int i = 0; i < pictures.length; i++) {
                    if (i < 3) {
                        ImageView iv = new ImageView(context);
                        int w = (int) RudenessScreenHelper.pt2px(context, 210);
                        int h = (int) RudenessScreenHelper.pt2px(context, 160);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, h);
                        iv.setLayoutParams(layoutParams);
                        String p = pictures[i];
                        String url = ImagUtil.handleUrl(p);
                        if (!TextUtils.isEmpty(url)) {
                            RxImageLoader.with(context).getBitmap(url).subscribe(
                                    imageBean -> {
                                        Bitmap bitmap = imageBean.getBitmap();
                                        Drawable drawable = ImagUtil.conner(bitmap);
                                        iv.setBackground(drawable);
                                    }
                            );
                        }
                        binding.ivContainer.addView(iv);
                    } else {
                        break;
                    }
                }
                binding.setNewsBean(dataBean);
            }


        } else {
            ListNewsItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
            convertView = binding.getRoot();
            binding.setNewsBean(dataBean);
        }

        if (convertView != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsDeatil newsDeatil = new NewsDeatil();
                    int id = dataBean.getId();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    newsDeatil.setArguments(bundle);
                    Presenter.getInstance().step2fragment(newsDeatil, "newsDetail");
                }
            });

            View finalConvertView = convertView;
            convertView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_UP:
                            finalConvertView.performClick();
                            break;
                    }

                    return false;
                }
            });

        }
        return convertView;
    }
}
