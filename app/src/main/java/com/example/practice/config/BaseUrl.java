package com.example.practice.config;

import rxhttp.wrapper.annotation.DefaultDomain;

public class BaseUrl{


    @DefaultDomain//设置默认域名
    public static String baseUrl="https://www.wanandroid.com/";

   /* @DefaultDomain
    public static final String BASE_TEST_URL = "rtm".equals(BuildConfig.ENVIRONMENT) ?
            "https://student20.ytwljy.com/api" : "http://121.37.204.141:7003/api";*/

   /* *//**
     * 设置非默认域名，name 可不传，不传默认为变量的名称
     *//*
    @Domain(name = "Update", className = "Update")
    public static String UPDATE_URL = "rtm".equals(BuildConfig.ENVIRONMENT) ?
            "https://apps20api.ytwljy.com/api/" : "http://121.37.204.141:7002/api/";*/

}
