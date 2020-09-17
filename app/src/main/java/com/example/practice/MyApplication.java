package com.example.practice;

import android.app.Application;

import com.example.practice.service.InitializeService;

public class MyApplication extends Application{

    @Override
    public void onCreate(){
        super.onCreate();
        InitializeService.start(this);

    }


}
