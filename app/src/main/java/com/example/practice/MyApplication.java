package com.example.practice;

import android.app.Application;

import com.example.practice.bean.tool.MyFastJsonConverter;
import com.example.practice.network.RxHttpManager;
import com.example.practice.service.InitializeService;

import java.io.File;

import rxhttp.RxHttp;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.cahce.CacheMode;
import rxhttp.wrapper.callback.IConverter;

public class MyApplication extends Application{

    @Override
    public void onCreate(){
        super.onCreate();
        //下面关于Rxhttp的不建议放在service里面，可能会导致monkey速度太快了，还没初始化完成就开始网络请求操作了
        RxHttpManager.init(this);
        //设置缓存目录为：Android/data/{app包名目录}/cache/RxHttpCache
        File cacheDir = new File(getExternalCacheDir(), "RxHttpCache");
        //设置最大缓存为10M，缓存有效时长为600秒
        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE,
                10 * 60 * 1000);
        IConverter converter = MyFastJsonConverter.create();
        RxHttp.setConverter(converter);

        InitializeService.start(this);

    }


}
