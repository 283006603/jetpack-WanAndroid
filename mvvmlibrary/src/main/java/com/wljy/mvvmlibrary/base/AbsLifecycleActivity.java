package com.wljy.mvvmlibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.wljy.mvvmlibrary.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import butterknife.ButterKnife;

/**
 * @author lzh
 */
public abstract class AbsLifecycleActivity extends RxAppCompatActivity {

    public AbsLifecycleActivity() {

    }

    private Activity CONTEXT_BASE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CONTEXT_BASE = this;
        //设置布局内容
        setContentView(getLayoutId());
        //状态栏
        initStatusBar();
        //初始化ButterKnife
        initButterKnife();
        //初始化控件
        initViews(savedInstanceState);
        //注册监听器
        initListener();
        //初始化ToolBar
        initToolBar();
        getRemoteData();

    }



    protected abstract void initListener();


    protected void onStateRefresh() {
    }

    //初始化butterKnife
    private void initButterKnife() {
        ButterKnife.bind(CONTEXT_BASE);
    }

    //初始化statusBar
    public void initStatusBar() {
        Log.d("AbsLifecycleActivity", "aaa");
//        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.icon_enabled).statusBarDarkFont(true).init();
    }


    /**
     * 设置布局layout
     */
    public abstract int getLayoutId();


    /**
     * 初始化toolbar
     */
    protected void initToolBar() {
        //doSomething
    }



    //获取网络数据
    public abstract void getRemoteData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //点击虚拟键返回动画
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_translate_left, R.anim.out_translate_left);
    }

    //点击返回按钮调用次方法
    public void onBackPress() {
        overridePendingTransition(R.anim.in_translate_left, R.anim.out_translate_left);
        finish();
    }

    //跳转带多次参数调用次方法
    public void activityParams(Class<? extends Activity> cls, HashMap<String, ? extends Object> params) {
        Intent intent = new Intent(CONTEXT_BASE, cls);
        Iterator<?> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            @SuppressWarnings("unchecked")
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
                    .next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            }
            if (value instanceof Boolean) {
                intent.putExtra(key, (boolean) value);
            }
            if (value instanceof Integer) {
                intent.putExtra(key, (int) value);
            }
            if (value instanceof Float) {
                intent.putExtra(key, (float) value);
            }
            if (value instanceof Double) {
                intent.putExtra(key, (double) value);
            }
        }
        startActivity(intent);

    }

    //跳转activity 传递list参数
    public void activityParams(Class<? extends Activity> cls, String type, List<? extends Object> datas) {
        Intent intent = new Intent(CONTEXT_BASE, cls);
        intent.putExtra(type, (Serializable) datas);
        startActivity(intent);
    }

    //跳转带ArrList
    public void activityParams(Class<? extends Activity> cls, String type, ArrayList<String> datas) {
        Intent intent = new Intent(CONTEXT_BASE, cls);
        intent.putExtra(type, (Serializable) datas);
        startActivity(intent);
    }

    //跳转activity
    public void activityClose(Class<? extends Activity> cls) {
        Intent intent = new Intent(CONTEXT_BASE, cls);
        startActivity(intent);
        finish();
    }

    //跳转activity
    public void activity(Class<? extends Activity> cls) {
        Intent intent = new Intent(CONTEXT_BASE, cls);
        overridePendingTransition(R.anim.in_translate_right, R.anim.out_translate_right);
        startActivity(intent);
    }

    //跳转activity
    public void activity(Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent(CONTEXT_BASE, cls);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.in_translate_right, R.anim.out_translate_right);
        startActivity(intent);
    }

    //跳转activity
    public void activityForReuslt(Class<? extends Activity> cls, Bundle bundle,int requestCode) {
        Intent intent = new Intent(CONTEXT_BASE, cls);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.in_translate_right, R.anim.out_translate_right);
        startActivityForResult(intent,requestCode);
    }

    public void initViews(Bundle savedInstanceState) {
        initViewModel();
    }

    public abstract void initViewModel();

    public  <T extends AbsViewModel> T registerViewModel(Class<T> mViewModel) {
        T absViewModel = new ViewModelProvider(this).get(mViewModel);
        absViewModel.registerSubscriber(this);
        return absViewModel;
    }


}