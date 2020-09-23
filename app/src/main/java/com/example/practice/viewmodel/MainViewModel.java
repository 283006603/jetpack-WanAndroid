package com.example.practice.viewmodel;

import android.app.Application;
import android.util.Log;

import com.example.practice.bean.BannerBean;
import com.example.practice.bean.MainArticleBean;
import com.example.practice.bean.PageList;
import com.example.practice.bean.ProjectListBean;
import com.example.practice.bean.SystemListBean;
import com.example.practice.bean.WeChatArticle;
import com.example.practice.config.Constants;
import com.example.practice.config.Urls;
import com.wljy.mvvmlibrary.base.AbsViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.RxHttp;

public class MainViewModel extends AbsViewModel{
    public MainViewModel(@NonNull Application application){
        super(application);
    }

    public void getBanner(){
        RxHttp.get(Urls.GET_MAIN_BANNER).asResponseList(BannerBean.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<BannerBean>>(){
            @Override
            public void accept(List<BannerBean> bannerBeans) throws Throwable{
                postData(Constants.GET_MAIN_BANNER,bannerBeans);
            }
        }, new Consumer<Throwable>(){
            @Override
            public void accept(Throwable throwable) throws Throwable{
                postData(Constants.REQUEST_ERROR,throwable.getMessage());
            }
        });
    }

    public void getMainArticle(int page){
        RxHttp.get(String.format(Urls.GET_MAIN_LIST, page)).asResponsePageList(MainArticleBean.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PageList<MainArticleBean>>(){
            @Override
            public void accept(PageList<MainArticleBean> mainArticleBeanPageList) throws Throwable{
                if(page == 0){
                    postData(Constants.GET_MAIN_ARTICLE_REFRESH, mainArticleBeanPageList);
                }else{
                    postData(Constants.GET_MAIN_ARTICLE_LOADMORE, mainArticleBeanPageList);
                }
            }
        }, new Consumer<Throwable>(){
            @Override
            public void accept(Throwable throwable) throws Throwable{
                postData(Constants.REQUEST_ERROR,throwable.getMessage());
            }
        });
    }

    public void getWeChatArticle(){
        RxHttp.get(Urls.GET_WECHAT_ARTICLE).asResponseList(WeChatArticle.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<WeChatArticle>>(){
            @Override
            public void accept(List<WeChatArticle> weChatArticles) throws Throwable{
                Log.d("MainViewModel", "weChatArticles.size():" + weChatArticles.size());
                postData(Constants.GET_MAIN_WECHAT_ARTICLE,weChatArticles);
            }
        }, new Consumer<Throwable>(){
            @Override
            public void accept(Throwable throwable) throws Throwable{
                postData(Constants.REQUEST_ERROR,throwable.getMessage());
            }
        });
    }

    public void getWeChatDetail(int wechatid,int page){
        RxHttp.get(String.format(Urls.GET_WECHAT_ARTICLE_LIST,wechatid, page)).asResponsePageList(MainArticleBean.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PageList<MainArticleBean>>(){
            @Override
            public void accept(PageList<MainArticleBean> mainArticleBeanPageList) throws Throwable{
                if(page == 0){
                    postData(Constants.GET_MAIN_ARTICLE_REFRESH, mainArticleBeanPageList);
                }else{
                    postData(Constants.GET_MAIN_ARTICLE_LOADMORE, mainArticleBeanPageList);
                }
            }
        }, new Consumer<Throwable>(){
            @Override
            public void accept(Throwable throwable) throws Throwable{
                postData(Constants.REQUEST_ERROR,throwable.getMessage());
            }
        });
    }

    //========================================体系
    public void getSystemList(){
        RxHttp.get(Urls.GET_SYSTEM_LIST).asResponseList(SystemListBean.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<SystemListBean>>(){
            @Override
            public void accept(List<SystemListBean> systemListBeans) throws Throwable{
                postData(Constants.GET_SYSTEM_LIST,systemListBeans);
            }
        }, new Consumer<Throwable>(){
            @Override
            public void accept(Throwable throwable) throws Throwable{
                postData(Constants.REQUEST_ERROR,throwable.getMessage());
            }
        });
    }

    //点击右边进入详情列表
    public void getSystemDetail(int page,int id){
        RxHttp.get(String.format(Urls.GET_SYSTEM_DETAIL, page,id)).asResponsePageList(MainArticleBean.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PageList<MainArticleBean>>(){
            @Override
            public void accept(PageList<MainArticleBean> mainArticleBeanPageList) throws Throwable{
                if(page == 0){
                    postData(Constants.GET_MAIN_ARTICLE_REFRESH, mainArticleBeanPageList);
                }else{
                    postData(Constants.GET_MAIN_ARTICLE_LOADMORE, mainArticleBeanPageList);
                }
            }
        }, new Consumer<Throwable>(){
            @Override
            public void accept(Throwable throwable) throws Throwable{
                postData(Constants.REQUEST_ERROR,throwable.getMessage());
            }
        });
    }

    //========================================项目
    public void getProject(){
        RxHttp.get(Urls.GET_PROJECT_LIST).asResponseList(ProjectListBean.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<ProjectListBean>>(){
            @Override
            public void accept(List<ProjectListBean> projectListBeans) throws Throwable{
                postData(Constants.GET_PROJECT_LIST,projectListBeans);
            }
        }, new Consumer<Throwable>(){
            @Override
            public void accept(Throwable throwable) throws Throwable{
                postData(Constants.REQUEST_ERROR,throwable.getMessage());
            }
        });

    }

    public void getProjectFragemtList(int page,int cid){
        RxHttp.get(String.format(Urls.GET_PROJECT_LIST_FRAGMENT,page,cid)).asResponseList(MainArticleBean.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<MainArticleBean>>(){
            @Override
            public void accept(List<MainArticleBean> projectListBeans) throws Throwable{
                postData(Constants.GET_PROJECT_LIST_FRAGMENT,projectListBeans);
            }
        }, new Consumer<Throwable>(){
            @Override
            public void accept(Throwable throwable) throws Throwable{
                postData(Constants.REQUEST_ERROR,throwable.getMessage());
            }
        });

    }



}
