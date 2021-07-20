package com.example.practice.viewmodel;

import android.app.Application;

import com.example.practice.config.Constants;
import com.wljy.mvvmlibrary.base.AbsViewModel;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import rxhttp.RxHttp;

/**
 * Created By 大苏打
 * on 2021/7/19
 */
public class DownLoadViewMoudel extends AbsViewModel{
    public DownLoadViewMoudel(@NonNull @NotNull Application application){
        super(application);
    }

    public void savaImg(String url,String path){
        RxHttp.get(url).asDownload(path)
                .subscribe(s ->{
                //回调文件下载路径
                postData(Constants.DOWN_IMG_SUCCESS,s);
                },throwable -> {
                //下载失败
                postData(Constants.REQUEST_ERROR,throwable);
                });
    }
}
