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
     * (项目分类)<tableLayout联动>
     */
    public static final String GET_PROJECT_LIST = "/project/tree/json";

    /**
     * (项目列表数据)<fragment>
     */
    public static final String GET_PROJECT_LIST_FRAGMENT = "/project/list/%d/json?cid=%d";  //page从1开始

    /**
     * 体系模块主页面展示(体系数据)
     */
    public static final String GET_SYSTEM_LIST = "/tree/json";

    /**
     * 体系详情列表（知识体系下的文章）
     */
    public static final String GET_SYSTEM_DETAIL = "/article/list/%d/json?cid=%d";

    /**
     * 与体系合并到一个fragment（导航数据）
     */
    public static final String GET_NAVIGATION_LIST = "/navi/json";

    /**
     * (搜索热词)
     */
    public static final String GET_HOT_KEY = "/hotkey/json";
    // /**
    //  * 体系搜索框（按照作者昵称搜索文章）
    //  */
    // public static final String GET_AUTHOR_ARTICLE = "/article/list/d%/json?author=s%";//从0开始,不支持模糊匹配.这里废弃掉。直接用搜索API，此接口仅仅支持作者文章搜索

    public static final String POST_SEARCH = "/article/query/0/json";//k ： 搜索关键词


    /**
     * (登录)
     *  errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
     * errorCode = -1001 代表登录失效，需要重新登录。复数为其他错误码
     *  登录相关
     */
    public static final String POST_LOGIN="/user/login";


    /**
     * (注册)
     */
    public static final String POST_REGISTER="/user/register";

    //==========================================================================================个人中心，妹子
    public static final String GET_MEIZI="https://gank.io/api/v2/data/category/Girl/type/Girl/page/%d/count/%d";
}
