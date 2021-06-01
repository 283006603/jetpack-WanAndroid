package com.example.practice.widge;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created By 大苏打
 * on 2021/6/1
 */
public class MyGridView extends GridView{
    public MyGridView(Context context){
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
