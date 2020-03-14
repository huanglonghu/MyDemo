package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointItemView;
import com.xx.yuefang.bean.DPtBean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.AppointCommentDetailFirstBinding;
import com.xx.yuefang.databinding.AppointCommentDetailSecondBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class AppointCommentSecond extends BaseFragment {

    private AppointCommentDetailSecondBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.appoint_comment_detail_second, container, false);
        binding.setPresenter(Presenter.getInstance());
        binding.setBean(appointItemView);
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            binding.setUserType(userBean.getUserType());
        }
        initPicture();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {}

    private DPtBean.DataBean dataBean;

    private void initPicture() {
        String picture = dataBean.getPicture();
        String url = ImagUtil.handleUrl(picture);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                    imageBean -> {
                        Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                        binding.appointItem.iv.setBackground(drawable);
                    }
            );
        }
        List<String> characteristics = dataBean.getCharacteristics();
        for (int i = 0; i < characteristics.size() + 1; i++) {
            if (i < 3) {
                TextView item;
                if (i == 0) {
                    item = (TextView) View.inflate(getContext(), R.layout.layout_character1, null);
                    item.setText(dataBean.getState());
                } else {
                    item = (TextView) View.inflate(getContext(), R.layout.layout_character2, null);
                    item.setText(characteristics.get(i - 1));
                }
                binding.appointItem.llexpend.addView(item, i);
            }
        }
    }


    @Override
    public void initView() {

    }


    @Override
    public void initlisten() {

        binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = binding.ratingBar.getRating();
                if (rating == 0) {
                    Toast.makeText(getContext(), "请先给服务评分", Toast.LENGTH_SHORT).show();
                    return;
                }
                String content = binding.content.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    content = "此用户没有填写评价";
                }
                if (type == 3 || type == 6) {
                    HttpUtil.getInstance().sellerComment(appointItemView.getId(), rating, content).subscribe(
                            str -> {
                                Presenter.getInstance().back();
                            }
                    );
                } else if (type == 4) {
                    HttpUtil.getInstance().userComment(appointItemView.getId(), rating, content).subscribe(
                            str -> {
                                Presenter.getInstance().back();
                            }
                    );
                }
            }
        });
    }


    private AppointItemView appointItemView;
    private int type;


    public void setData(AppointItemView appointItemView, int type, DPtBean.DataBean dataBean) {
        this.appointItemView = appointItemView;
        this.type = type;
        this.dataBean = dataBean;
    }

}
