package com.example.practice.bean

/**
 * Created By 大苏打
 * on 2020/10/23
 */
class NavigationListBean {
    var cid = 0
    var name: String? = null
    var articles: List<ArticlesBean>? = null

    class ArticlesBean {
        /**
         * apkLink :
         * audit : 1
         * author : 小编
         * canEdit : false
         * chapterId : 272
         * chapterName : 常用网站
         * collect : false
         * courseId : 13
         * desc :
         * descMd :
         * envelopePic :
         * fresh : false
         * id : 1848
         * link : https://developers.google.cn/
         * niceDate : 2018-01-07 18:59
         * niceShareDate : 未知时间
         * origin :
         * prefix :
         * projectLink :
         * publishTime : 1515322795000
         * realSuperChapterId : 0
         * selfVisible : 0
         * shareDate : null
         * shareUser :
         * superChapterId : 0
         * superChapterName :
         * tags : []
         * title : Google开发者
         * type : 0
         * userId : -1
         * visible : 0
         * zan : 0
         */
        var apkLink: String? = null
        var audit = 0
        var author: String? = null
        var isCanEdit = false
        var chapterId = 0
        var chapterName: String? = null
        var isCollect = false
        var courseId = 0
        var desc: String? = null
        var descMd: String? = null
        var envelopePic: String? = null
        var isFresh = false
        var id = 0
        var link: String? = null
        var niceDate: String? = null
        var niceShareDate: String? = null
        var origin: String? = null
        var prefix: String? = null
        var projectLink: String? = null
        var publishTime: Long = 0
        var realSuperChapterId = 0
        var selfVisible = 0
        var shareDate: Any? = null
        var shareUser: String? = null
        var superChapterId = 0
        var superChapterName: String? = null
        var title: String? = null
        var type = 0
        var userId = 0
        var visible = 0
        var zan = 0
        var tags: List<Any>? = null
    }
}