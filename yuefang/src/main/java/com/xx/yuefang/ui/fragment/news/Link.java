package com.xx.yuefang.ui.fragment.news;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.FragmentLinkBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Link extends BaseFragment {
    private FragmentLinkBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_link, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        return binding.getRoot();
    }

    private void loadWebView(String url) {
        if (!url.contains("http")) {
            url = "http://" + url;
        }
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        binding.webView.loadUrl(url);
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.pb.setProgress(100);
                binding.setProgress(100);
                Observable.just(true).delay(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                        a -> {
                            binding.pbLayout.setVisibility(View.GONE);
                        }
                );
            }
        });

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int progress) {
                binding.pb.setProgress(progress);
                binding.setProgress(progress);
                if (progress == 100) {
                    Observable.just(true).delay(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                            a -> {
                                binding.pbLayout.setVisibility(View.GONE);
                            }
                    );
                }
            }

        });

    }

    @Override
    public void initData() {}

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String url = bundle.getString("url");
            loadWebView(url);
            String title = bundle.getString("title");
            binding.setTitle(title);
        }
    }

    @Override
    public void initlisten() {}

    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
    }

    @Override
    public void onStop() {
        super.onStop();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
    }

    @Override
    public void onDestroy() {
        if (binding != null) {
            //加载null内容
            binding.webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            //清除历史记录
            binding.webView.clearHistory();
            //销毁VebView
            binding.webView.destroy();
        }
        super.onDestroy();
    }
}
