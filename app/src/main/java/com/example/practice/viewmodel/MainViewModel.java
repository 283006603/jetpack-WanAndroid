package com.example.practice.viewmodel;

import android.app.Application;

import com.example.practice.bean.MainArticleBean;
import com.example.practice.bean.PageList;
import com.example.practice.config.Constants;
import com.example.practice.config.Urls;
import com.wljy.mvvmlibrary.base.AbsViewModel;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.RxHttp;

public class MainViewModel extends AbsViewModel{
    public MainViewModel(@NonNull Application application){
        super(application);
    }

    public void getBanner(){
    }

    public void getMainArticle(int page){
        RxHttp.get(String.format(Urls.GET_POEM_LIST, page)).asResponsePageList(MainArticleBean.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PageList<MainArticleBean>>(){
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
}
