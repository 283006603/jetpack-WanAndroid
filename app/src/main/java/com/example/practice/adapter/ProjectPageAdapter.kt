package com.example.practice.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.practice.bean.ProjectPageBean

/**
 * Created By 大苏打
 * on 2020/9/23
 */
class ProjectPageAdapter(fm: FragmentManager?, var list: MutableList<ProjectPageBean>) :
    FragmentPagerAdapter(
        fm!!
    ) {
    override fun getItem(position: Int): Fragment {
        return list[position].fragment!!
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position].title
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}