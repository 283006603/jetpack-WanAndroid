package com.example.practice.network;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rxhttp.RxHttp;

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
        RxHttp.init(client, false);

    }
}
