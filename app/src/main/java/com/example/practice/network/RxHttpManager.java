package com.example.practice.network;

import android.app.Application;

import com.example.practice.BuildConfig;
import com.example.practice.bean.tool.MyFastJsonConverter;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.cahce.CacheMode;
import rxhttp.wrapper.callback.IConverter;

public class RxHttpManager{


    public static void init (Application context){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true) //忽略host验证
                .addInterceptor(new TokenInterceptor(context))//拦截器属于okhttp3
                .addInterceptor(httpLoggingInterceptor).build();
        //RxHttp初始化，自定义OkHttpClient对象，非必须
        //设置debug模式，默认为false，设置为true后，发请求，过滤"RxHttp"能看到请求日志
        //设置缓存目录为：Android/data/{app包名目录}/cache/RxHttpCache
        File cacheDir = new File(context.getExternalCacheDir(), "RxHttpCache");
        //RxHttp初始化，自定义OkHttpClient对象，非必须
        //设置最大缓存为10M，缓存有效时长为600秒
        IConverter converter = MyFastJsonConverter.create();
        RxHttpPlugins.init(client)
                .setDebug(BuildConfig.DEBUG)
                .setCache(cacheDir, 10 * 1024 * 1024, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE, 10 * 60 * 1000)
                .setConverter(converter);

    }



}
