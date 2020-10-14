package com.example.practice.config;

public class Urls{
 /**
  * 首页RecycleView列表（首页文章列表）
  */
 public static final String GET_MAIN_LIST = "/article/list/%d/json";

 /**
  * (首页Banner)
  */
 public static final String GET_MAIN_BANNER = "/banner/json";

 /**
  * 首页微信公众号(获取公众号列表)
  */
 public static final String GET_WECHAT_ARTICLE = "/wxarticle/chapters/json";

 /**
  * 首页微信公众号历史数据(查看某个公众号历史数据)
  */
 public static final String GET_WECHAT_ARTICLE_LIST = "/wxarticle/list/%d/%d/json";


 /**
  * 体系模块主页面展示(体系数据)
  */
 public static final String GET_SYSTEM_LIST = "/tree/json";

 /**
  * 体系详情列表（知识体系下的文章）
  */
 public static final String GET_SYSTEM_DETAIL = "article/list/%d/json?cid=%d";


 /**
  * (搜索热词)
  */
 public static final String GET_HOT_KEY="/hotkey/json";

 /**
  * 体系搜索框（按照作者昵称搜索文章）
  */
 public static final String GET_AUTHOR_ARTICLE = "/article/list/d%/json?author=s%";//从0开始,不支持模糊匹配

 /**
  * (项目分类)<tableLayout联动>
  */
 public static final String GET_PROJECT_LIST="/project/tree/json";

 /**
  * (项目列表数据)<fragment>
  */
 public static final String GET_PROJECT_LIST_FRAGMENT="/project/list/%d/json?cid=%d";  //page从1开始



}
