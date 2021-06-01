package com.example.practice.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * Created By 大苏打
 * on 2021/5/31
 */
public class MyViewPagerAdapter extends PagerAdapter{
    private List<View> viewList;//View就二十GridView

    public MyViewPagerAdapter(List<View> viewList){
        this.viewList = viewList;
    }

    @Override
    public int getCount(){
        return viewList != null ? viewList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1){
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        // TODO Auto-generated method stub
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        // TODO Auto-generated method stub
        container.removeView((View) object);
    }
}
