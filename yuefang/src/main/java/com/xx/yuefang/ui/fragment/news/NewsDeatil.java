package com.xx.yuefang.ui.fragment.news;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.NewsDetailBean;
import com.xx.yuefang.databinding.FragmentNewsDeatailBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.widget.ReplyWindow;
import com.xx.yuefang.ui.widget.ShareDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.RudenessScreenHelper;
import com.xx.yuefang.util.SoftHideKeyBoardUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsDeatil extends BaseFragment {
    private FragmentNewsDeatailBinding binding;
    private int id;
    private NewsDetailBean.DataBean data;
    private String text;
    private UserObserver<EventData> userObserver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_deatail, container, false);
        binding.setPresenter(Presenter.getInstance());
        SoftHideKeyBoardUtil.setToggle(false);
        initView();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        HttpUtil.getInstance().getNewsDetailById(id).subscribe(
                str -> {
                    NewsDetailBean newsDetailBean = GsonUtil.fromJson(str, NewsDetailBean.class);
                    data = newsDetailBean.getData();
                    if (data != null) {
                        binding.setBean(data);
                        String html = data.getRichTextContent();
                        html = getNewContent(html);
                        binding.content.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
                    }
                }
        );
    }

    public String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }

    private ReplyWindow replyWindow;

    private int keyboardHeight;

    @Override
    public void initlisten() {
        replyWindow = new ReplyWindow(getContext(), new ClickSureListener() {
            @Override
            public void click(String content, int type, int commentId) {
                HttpUtil.getInstance().addNewsComment(id, content).subscribe(
                        str -> {
                            Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
                            //刷新数据
                            replyWindow.replySuccess();
                            NewsComment newsComment = new NewsComment();
                            Bundle bundle = getArguments();
                            bundle.putSerializable("newsInfo", data);
                            newsComment.setArguments(bundle);
                            Presenter.getInstance().step2fragment(newsComment, "newscomment");
                        }
                );

            }
        });

        binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyWindow.hint("输入评论", 1, 0);
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        binding.toComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsComment newsComment = new NewsComment();
                Bundle bundle = getArguments();
                bundle.putSerializable("newsInfo", data);
                newsComment.setArguments(bundle);
                Presenter.getInstance().step2fragment(newsComment, "newscomment");
            }
        });

        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = getBundle();
                if (params != null) {
                    ShareDialog shareDialog = new ShareDialog(getContext(), 2, null, params);
                    shareDialog.toShow();
                }
            }
        });


        userObserver = new UserObserver<EventData>() {
            @Override
            public void onUpdate(UserObservable<EventData> observable, EventData data) {
                switch (data.getEventType()) {
                    case UserObservable.TYPE_KEYBOARD_OUT:
                        Bundle bundle = data.getData();
                        int height = bundle.getInt("height");
                        float v = RudenessScreenHelper.pt2px(YueFangApplication.getApplication(), 320);
                        if (height < 300) {
                            keyboardHeight = 0;
                            LogUtil.log(height + "==============CCCCCCCCCC=============" + v);
                            if (replyWindow.isShowing()) {
                                replyWindow.update(0, 0, -1, -1);
                            }
                        } else {
                            keyboardHeight = height;
                            LogUtil.log(height + "==============BBBBBBBBBBBB=============" + v);
                            replyWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, height);
                            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                            lp.alpha = 0.6f;
                            getActivity().getWindow().setAttributes(lp);
                        }
                        break;

                }
            }
        };
        UserObservable.getInstance().register(userObserver);

        replyWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
                boolean empty = TextUtils.isEmpty(replyWindow.getHint());
                if (keyboardHeight > 0 && !empty) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });


    }

    private Bundle getBundle() {
        Bundle params = new Bundle();
        params.putString("title", data.getTitle());//标题
        text = data.getRichTextContent();
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        text = text.replaceAll("\\s*|\t|\r|\n", "");//去除字符串中的空格,回车,换行符,制表符
        text = text.replaceAll("&nbsp;", "");
        String REGEX_HTML = "<[^>]+>";
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(text);
        text = m_html.replaceAll("");
        text = text.replaceAll("</p>", "").replaceAll("<p>", "");

        if (text.length() > 50) {
            text = text.substring(0, 50);
        }
        params.putString("description", text);//描述
        int id = data.getId();
        params.putString("webUrl", "http://api.yuefangwang.net/house/news/" + id);//连接
        return params;
    }


    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
    }

    @Override
    public void onStop() {
        super.onStop();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
    }


    @Override
    public void onDestroy() {
        if (binding != null) {
            //加载null内容
            binding.content.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            //清除历史记录
            binding.content.clearHistory();
            //销毁VebView
            binding.content.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (userObserver != null) {
            UserObservable.getInstance().unregister(userObserver);
            userObserver = null;
            if (replyWindow != null) {
                replyWindow.dismiss();
                replyWindow = null;
            }
        }
    }
}
