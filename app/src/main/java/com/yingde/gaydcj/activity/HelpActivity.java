package com.yingde.gaydcj.activity;

import android.graphics.Color;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.util.MyToolbar;

import butterknife.BindView;

public class HelpActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    MyToolbar toolbar;

    @BindView(R.id.webView)
    WebView webView;
    private static final String sUrl = "file:///android_asset/html/czsc.htm";
//
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_help;
    }

    @Override
    protected void initData() {
        webView.getSettings().setLoadsImagesAutomatically(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webView.setBackgroundColor(Color.TRANSPARENT); // WebView 背景透明效果
        webView.loadUrl(sUrl);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        toolbar.setTitle("操作手册");
    }

}
