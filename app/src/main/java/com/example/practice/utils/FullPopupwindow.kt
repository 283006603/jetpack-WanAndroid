package com.example.practice.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.PopupWindow

class FullPopupwindow(context: Context?) : PopupWindow(context) {
    private var mWidth = 0
    private var mHeight = 0

    /**
     * 设置PopupWindow的大小
     * @param context
     */
    private fun calWidthAndHeight(context: Context) {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getRealMetrics(metrics)
        mWidth = metrics.widthPixels
        mHeight = metrics.heightPixels
    }

    init {
        //计算宽度和高度
        if (context != null) {
            calWidthAndHeight(context)
        }
        width = mWidth
        height = mHeight
        //设置布局与相关属性
        isFocusable = true
        isTouchable = true
        isClippingEnabled = false
        setBackgroundDrawable(ColorDrawable(Color.parseColor("#80000000")))
    }
}