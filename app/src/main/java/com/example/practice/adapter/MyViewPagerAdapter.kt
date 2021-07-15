package com.example.practice.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * Created By 大苏打
 * on 2021/5/31
 */
class MyViewPagerAdapter(  //View就二十GridView
    private val viewList: List<View>?
) : PagerAdapter() {
    override fun getCount(): Int {
        return viewList?.size ?: 0
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        // TODO Auto-generated method stub
        return arg0 === arg1
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // TODO Auto-generated method stub
        container.addView(viewList!![position])
        return viewList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // TODO Auto-generated method stub
        container.removeView(`object` as View)
    }
}