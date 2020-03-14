package com.xx.yuefang.ui.fragment.news;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.FragmentLink2Binding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.widget.NetLoading;
import com.xx.yuefang.util.RudenessScreenHelper;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

public class Link2 extends BaseFragment {
    private FragmentLink2Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_link2, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        return binding.getRoot();
    }

    private NetLoading netLoading;

    private void loadWebView(String url) {
        if (!url.contains("http")) {
            url = "http://" + url;
        }
        com.tencent.smtt.sdk.WebSettings webSettings = binding.webView.getSettings();

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);

        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                netLoading = new NetLoading(getContext());
                netLoading.show();
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                binding.webView.setVisibility(View.VISIBLE);
                if (netLoading != null) {
                    netLoading.dismiss();
                    netLoading = null;
                }
            }
        });
        binding.webView.loadUrl(url);
    }

    @Override
    public void initData() {

    }

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
    public void initlisten() {

    }

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
        RudenessScreenHelper.resetDensity(getContext(), 750);
    }
}
