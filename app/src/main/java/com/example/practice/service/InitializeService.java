package com.example.practice.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.practice.bean.tool.MyFastJsonConverter;
import com.example.practice.network.RxHttpManager;

import java.io.File;

import androidx.annotation.Nullable;
import rxhttp.RxHttp;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.cahce.CacheMode;
import rxhttp.wrapper.callback.IConverter;

public class InitializeService extends IntentService{
    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.example.practice.service.INIT";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */

    public InitializeService(){
        super("InitializeService");
    }

    public static void start(Context context){
        Intent intent=new Intent(context,InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent){
        //子线程，异步处理，好出可以让Application执行更快见到主页面
        if(intent!=null){
            final String action =intent.getAction();
            if(ACTION_INIT_WHEN_APP_CREATE.equals(action)){
                performInit();
            }
        }
    }

    private void performInit(){


    }
}
