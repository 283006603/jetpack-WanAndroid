package com.example.practice.config;

public class Urls{
 /**
  * 首页RecycleView列表
  */
 public static final String GET_MAIN_LIST = "/article/list/%d/json";

 /**
  * 首页Banner
  */
 public static final String GET_MAIN_BANNER = "/banner/json";

 /**
  * 首页微信公众号
  */
 public static final String GET_WECHAT_ARTICLE = "/wxarticle/chapters/json";

 /**
  * 首页微信公众号历史数据
  */
 public static final String GET_WECHAT_ARTICLE_LIST = "/wxarticle/list/%d/%d/json";


 /**
  * 体系模块主页面展示
  */
 public static final String GET_SYSTEM_LIST = "/tree/json";

 /**
  * 体系详情列表
  */
 public static final String GET_SYSTEM_DETAIL = "article/list/%d/json?cid=%d";


 /**
  * 项目分类(tableLayout联动)
  */
 public static final String GET_PROJECT_LIST="/project/tree/json";

 /**
  * 项目列表数据(fragment)
  */
 public static final String GET_PROJECT_LIST_FRAGMENT="/project/list/%d/json?cid=%d";
}
