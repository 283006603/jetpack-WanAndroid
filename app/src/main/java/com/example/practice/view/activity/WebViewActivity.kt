package com.example.practice.view.activity

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient.FileChooserParams
import android.webkit.WebView
import androidx.appcompat.widget.Toolbar
import com.example.practice.base.BaseActivity
import com.example.practice.config.Constants
import com.example.practice.databinding.ActivityWebViewBinding
import com.example.practice.widge.ProgressWebView
import com.example.practice.widge.ProgressWebView.WebViewCallback
import com.gyf.immersionbar.ImmersionBar
import com.wljy.mvvmlibrary.R

class WebViewActivity : BaseActivity<ActivityWebViewBinding?>() {
    var toolbar: Toolbar? = null
    var wvWeb: ProgressWebView? = null
    private var author: String? = null
    private var id = 0
    private var url: String? = null
    private var title: String? = null
    override fun useImmersionBar(): Boolean {
        return false
    }

    override fun initStatusBar() {
        super.initStatusBar()
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.icon_enabled)
            .statusBarDarkFont(true).init()
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        toolbar = binding?.toolbar?.toolbar
        wvWeb = binding?.wvWeb
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initData()
    }

        fun initData(){
            val extras = intent.extras
            if (extras != null) {
                author = extras.getString(Constants.AUTHOR)
                id = extras.getInt(Constants.ID, -1)
                url = extras.getString(Constants.URL)
                title = extras.getString(Constants.TITILE)
                wvWeb?.loadUrl(url.toString())
            }

        }
    override fun initListener() {
        toolbar?.setNavigationOnClickListener { handleWebViewBack() }
        wvWeb?.setWebViewCallback(object : WebViewCallback {
            override fun onProgressChanged(view: WebView, newProgress: Int) {}
            override fun onReceivedTitle(view: WebView, title: String) {
                toolbar?.title = title
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {}
            override fun onPageFinished(view: WebView, url: String) {}
            override fun onLoadResource(view: WebView, url: String) {}
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
            }

            override fun onPageLoadComplete() {}
            override fun onShowFileChooser(
                webView: WebView,
                valueCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                return false
            }

            override fun openFileChooser(
                valueCallback: ValueCallback<Uri>,
                acceptType: String,
                capture: String
            ) {
            }
        })
    }

    override fun getRemoteData() {}
    override fun initViewModel() {}
    private fun handleWebViewBack() {
        if (wvWeb?.canGoBack() == true) {
            wvWeb?.goBack()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        wvWeb?.removeAllViews()
        wvWeb = null
    }
}