package com.example.practice.view.activity;

import com.example.practice.R;
import com.example.practice.base.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;

public class SearchActivity extends BaseActivity{

    @Override
    public boolean useImmersionBar(){
        return true;
    }

    @Override
    public void initStatusBar() {
        if (useImmersionBar()) {
            ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.color_white).statusBarDarkFont(true).init();
        }
    }

    @Override
    protected void initListener(){
    }

    @Override
    public int getLayoutId(){
        return R.layout.activity_search;
    }

    @Override
    public void getRemoteData(){
    }

    @Override
    public void initViewModel(){
    }
}