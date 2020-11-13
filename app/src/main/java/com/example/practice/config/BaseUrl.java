package com.example.practice.config;

import com.example.practice.BuildConfig;

import rxhttp.wrapper.annotation.DefaultDomain;

public class BaseUrl{

    @DefaultDomain//设置默认域名
    public static String baseUrl = getBaseUrl();

    private static String getBaseUrl(){
        String url = "";
        if ("rtm".equals(BuildConfig.ENVIRONMENT)) {
            url = "https://www.baidu.com/";
        } else if ("preview".equals(BuildConfig.ENVIRONMENT)) {
            url = "https://www.wanandroid.com";
        } else {
            url = "http://139.9.35.9:7013/api/";
        }
        return url;
    }
}
