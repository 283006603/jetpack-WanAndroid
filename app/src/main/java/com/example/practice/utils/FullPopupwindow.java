package com.example.practice.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class FullPopupwindow extends PopupWindow {
    private int mWidth;
    private int mHeight;

    public FullPopupwindow(Context context) {
        super(context);
        //计算宽度和高度
        calWidthAndHeight(context);
        setWidth(mWidth);
        setHeight(mHeight);
        //设置布局与相关属性
        setFocusable(true);
        setTouchable(true);
        setClippingEnabled(false);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
    }


    /**
     * 设置PopupWindow的大小
     * @param context
     */
    private void calWidthAndHeight(Context context) {
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics= new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(metrics);

        mWidth=metrics.widthPixels;
        mHeight= metrics.heightPixels;
    }
}