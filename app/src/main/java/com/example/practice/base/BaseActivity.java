package com.example.practice.base;

import com.gyf.immersionbar.ImmersionBar;
import com.wljy.mvvmlibrary.base.AbsLifecycleActivity;

public abstract class BaseActivity extends AbsLifecycleActivity{

    public abstract boolean useImmersionBar();

    @Override
    public void initStatusBar() {
        if (useImmersionBar()) {
            ImmersionBar.with(this).statusBarDarkFont(true).init();
        }
    }
}
