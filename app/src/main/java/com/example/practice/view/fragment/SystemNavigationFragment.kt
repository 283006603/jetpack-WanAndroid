package com.example.practice.view.fragment

import android.os.Bundle
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.practice.adapter.SystemNavigationAdapter
import com.example.practice.base.BaseFragment
import com.example.practice.databinding.FragmentSystemNavigationBinding
import com.example.practice.view.activity.SearchActivity
import com.example.practice.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 * Use the [SystemNavigationFragment.factory method to
 * create an instance of this fragment.仿拼多多或扑扑的分类栏
 */
class SystemNavigationFragment :BaseFragment<FragmentSystemNavigationBinding>() {

    var relaSearch :RelativeLayout?=null
    var tabLayout:TabLayout?=null
    var viewPager:ViewPager?=null
    var mainViewModel : MainViewModel?=null
    var list :MutableList<Fragment>?=null
    val title = arrayListOf("体系","导航")

    override fun initViewModel() {
        mainViewModel=registerViewModel(MainViewModel::class.java)
    }

    override fun initView(state: Bundle?) {
        super.initView(state)
        relaSearch=binding?.itemsearch?.relaSearch
        tabLayout=binding.tableLayout
        viewPager=binding?.viewPager
        initFragment()
    }

    private fun initFragment() {
        list=ArrayList()
        list?.add(SystemFragment())
        list?.add(NavigationFragment())
        val adapter =SystemNavigationAdapter(childFragmentManager,
            list as ArrayList<Fragment>,title)
        viewPager?.adapter=adapter
        tabLayout?.setupWithViewPager(viewPager)
    }

    override fun initListener() {
        super.initListener()
        relaSearch?.setOnClickListener{
            val bundle=Bundle()
            bundle.putInt("type",1)
            bundle.putString("str","按作者名称搜索文章")
            activity(SearchActivity::class.java, bundle)
        }
    }

    override fun getRemoteData() {
    }
}