package com.example.practice.utils

import android.content.Context
import android.content.pm.PackageManager

class AppUtil {

    companion object {
        fun getAppVersion(): String? {
            return null
        }


        fun getAppName(context: Context): String? {
            val pm = context.packageManager
            //获取包信息
            try {
                val packageInfo = pm.getPackageInfo(context.packageName, 0)
                //获取应用 信息
                val applicationInfo = packageInfo.applicationInfo
                //获取albelRes
                val labelRes = applicationInfo.labelRes
                //返回App的名称
                return context.resources.getString(labelRes)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return null
        }
    }

    init {
        throw UnsupportedOperationException("cannot be instantiated")
    }
}