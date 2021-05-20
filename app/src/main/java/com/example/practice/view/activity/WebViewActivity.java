package com.example.practice.view.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.practice.base.BaseActivity;
import com.example.practice.config.Constants;
import com.example.practice.databinding.ActivityWebViewBinding;
import com.example.practice.widge.ProgressWebView;
import com.gyf.immersionbar.ImmersionBar;

import androidx.appcompat.widget.Toolbar;

public class WebViewActivity extends BaseActivity<ActivityWebViewBinding>{

    Toolbar toolbar;
    ProgressWebView wvWeb;
    private String author;
    private int id;
    private String url;
    private String title;

    @Override
    public boolean useImmersionBar(){
        return false;
    }

    @Override
    public void initStatusBar(){
        super.initStatusBar();
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(com.wljy.mvvmlibrary.R.color.icon_enabled).statusBarDarkFont(true).init();
    }

    @Override
    public void initViews(Bundle savedInstanceState){
        super.initViews(savedInstanceState);
        toolbar = binding.toolbar.toolbar;
        wvWeb = binding.wvWeb;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getInitData();
    }

    private void getInitData(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            author = extras.getString(Constants.AUTHOR);
            id = extras.getInt(Constants.ID, -1);
            url = extras.getString(Constants.URL);
            title = extras.getString(Constants.TITILE);
            wvWeb.loadUrl(url);
        }
    }

    @Override
    protected void initListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                handleWebViewBack();
            }
        });
        wvWeb.setWebViewCallback(new ProgressWebView.WebViewCallback(){
            @Override
            public void onProgressChanged(WebView view, int newProgress){
            }

            @Override
            public void onReceivedTitle(WebView view, String title){
                toolbar.setTitle(title);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
            }

            @Override
            public void onPageFinished(WebView view, String url){
            }

            @Override
            public void onLoadResource(WebView view, String url){
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
            }

            @Override
            public void onPageLoadComplete(){
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams){
                return false;
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture){
            }
        });
    }

    @Override
    public void getRemoteData(){
    }

    @Override
    public void initViewModel(){
    }

    private void handleWebViewBack(){
        if(wvWeb.canGoBack()){
            wvWeb.goBack();
        }else{
            finish();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        wvWeb.removeAllViews();
        wvWeb = null;
    }
}