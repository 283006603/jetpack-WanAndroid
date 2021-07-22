package com.example.practice.service

import android.app.IntentService
import android.content.Context
import android.content.Intent

class InitializeService : IntentService("InitializeService") {


    companion object{
        val ACTION_INIT_WHEN_APP_CREATE="com.example.practice.service.INIT"

        fun start(context: Context) {
            val intent = Intent(context, InitializeService::class.java)
            intent.action = ACTION_INIT_WHEN_APP_CREATE
            context.startService(intent)
        }
    }


    override fun onHandleIntent(intent: Intent?) {
        if(intent !=null){
            val action : String? =intent?.action
            if(ACTION_INIT_WHEN_APP_CREATE.equals(action)){
                performInit();
            }
        }
    }


    private fun performInit() {}
}
