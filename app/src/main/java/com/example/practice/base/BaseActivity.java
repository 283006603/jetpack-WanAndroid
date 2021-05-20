package com.example.practice.base;

import android.view.LayoutInflater;
import android.view.View;

import com.example.practice.R;
import com.gyf.immersionbar.ImmersionBar;
import com.wljy.mvvmlibrary.base.AbsLifecycleActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<T extends ViewBinding> extends AbsLifecycleActivity{

    public abstract boolean useImmersionBar();

    protected T binding;

    @Override
    public void initStatusBar(){
        if(useImmersionBar()){
            ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.icon_enabled).statusBarDarkFont(true).init();
        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    protected final View provideRootView(){
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Class cls = (Class) type.getActualTypeArguments()[0];
        try{
            Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (T) inflate.invoke(null, getLayoutInflater());
        }catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
            return null;
        }
        return binding.getRoot();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        binding = null;
    }
}
