package com.xx.yuefang.ui.fragment.house.houseDetail;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.PosterBean;
import com.xx.yuefang.databinding.FragmentPosterBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.news.URLImageParser;
import com.xx.yuefang.util.GsonUtil;

public class Poster extends BaseFragment {

    private FragmentPosterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_poster, container, false);
        binding.setPresenter(Presenter.getInstance());
        initData();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        String name = bundle.getString("name");
        binding.setTitle(name);
        HttpUtil.getInstance().getPosterDetail(id).subscribe(
                str -> {
                    PosterBean posterBean = GsonUtil.fromJson(str, PosterBean.class);
                    PosterBean.DataBean data = posterBean.getData();
                    if (data != null) {
                        String html = data.getPosterContent();
                        if(!TextUtils.isEmpty(html)){
//                            binding.content.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
//                            binding.content.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
//                            URLImageParser urlImageParser = new URLImageParser(getContext(), binding.content);
//                            Html.ImageGetter imageGetter = new Html.ImageGetter() {
//
//                                @Override
//                                public Drawable getDrawable(String source) {
//                                    return null;
//                                }
//                            };
//                            Html.fromHtml(html,imageGetter)
//                            binding.content.setText(Html.fromHtml(html, urlImageParser, null));

                            String htmlData = getHtmlData(html);
                            //content是后台返回的h5标签
                            binding.content.loadDataWithBaseURL(null,
                                    getHtmlData(html), "text/html", "utf-8", null);



                        }
                    }
                }
        );
    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {

    }


    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

}
