package com.example.practice.widge;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.practice.R;

public class QQHeaderSrollView extends ListView {
    private ImageView mImageView;
    //ImageView的初始高度
    private int mImageViewHeight;//初始高度

    public QQHeaderSrollView(Context context) {
        super(context);
    }

    public QQHeaderSrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mImageViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.size_default_height);
    }

    public QQHeaderSrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置要改变大小的图片
     * @param iv
     */
    public void setZoomImageView(ImageView iv) {
        this.mImageView = iv;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_UP){
            ResetAnimation resetAnimation = new ResetAnimation(mImageViewHeight);
            //设置动画的迭代器 有波动的迭代器
            resetAnimation.setInterpolator(new OvershootInterpolator());
            //设置间隔时间
            resetAnimation.setDuration(700);
            mImageView.startAnimation(resetAnimation);
        }
        return super.onTouchEvent(ev);
    }





    public class ResetAnimation extends Animation{
        //ImageView增加了的高度
        private int extraHeight;
        //ImageView当前的高度
        private int currentHeight;

        public ResetAnimation(int targetHeight) {
            currentHeight = mImageView.getHeight();
            extraHeight = mImageView.getHeight() - targetHeight;
        }

        /**
         *
         * @param interpolatedTime 标准化的时间的值 0-1
         * @param t
         */
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            //让mImageView的高度回到它原来的高度
            mImageView.getLayoutParams().height = (int)(currentHeight - extraHeight*interpolatedTime);
            mImageView.requestLayout();
            super.applyTransformation(interpolatedTime, t);
        }
    }

    /**
     * 越界下拉的方法  就是数据到头了 依然能下拉的方法
     * @param deltaX
     * @param deltaY Y轴的增量
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX
     * @param maxOverScrollY
     * @param isTouchEvent
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if(deltaY<0){
            mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY/3;
            mImageView.requestLayout();
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
}
