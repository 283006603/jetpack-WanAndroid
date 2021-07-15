package com.example.practice.view.fragment

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.practice.adapter.ProjectPageAdapter
import com.example.practice.base.BaseFragment
import com.example.practice.bean.ProjectListBean
import com.example.practice.bean.ProjectPageBean
import com.example.practice.config.Constants
import com.example.practice.databinding.FragmentProjectBinding
import com.example.practice.viewmodel.MainViewModel
import com.example.practice.widge.MySlidingTabLayout
import com.wljy.mvvmlibrary.annotation.Event
import java.security.Key
import java.util.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class ProjectFragment : BaseFragment<FragmentProjectBinding>() {


    var tableLayout: MySlidingTabLayout? = null
    var viewPager: ViewPager? = null
    var mainViewModel:MainViewModel?=null
    var projectListBeans:MutableList<ProjectListBean>?=null
    var projectPageBeans:MutableList<ProjectPageBean>?=null

    override fun initViewModel() {
        mainViewModel=registerViewModel(MainViewModel::class.java)
    }

    override fun initView(state: Bundle?) {
        super.initView(state)
        tableLayout=binding.tableLayout
        viewPager=binding.viewPager
    }

    override fun getRemoteData() {
        mainViewModel?.getProject()
    }

    @Event(key=[Constants.GET_PROJECT_LIST,Constants.REQUEST_ERROR])
    fun event(key: String,value :Any){
        if(key==Constants.GET_PROJECT_LIST){
            projectListBeans=value as MutableList<ProjectListBean>
            changePageFragment()
            relateVpAndTab()
        }else if(key==Constants.REQUEST_ERROR){

        }
    }

    private fun relateVpAndTab() {
        //      https://www.jianshu.com/p/8fdd6f2719b0
        val pageAdapter =ProjectPageAdapter(childFragmentManager,projectPageBeans!!)
        var titles = arrayOfNulls<String>(projectPageBeans?.size?:0)
        for(i in titles.indices){
            titles[i]= projectListBeans?.get(i)?.name
        }
        viewPager?.adapter=pageAdapter
        tableLayout?.setViewPager(viewPager,titles)
    }

    private fun changePageFragment():MutableList<ProjectPageBean> {
        projectPageBeans= ArrayList<ProjectPageBean>()
        projectListBeans?.forEach {
            val projectPageBean :ProjectPageBean= ProjectPageBean()
            projectPageBean.id=it.id
            projectPageBean.title=it.name
            projectPageBean.fragment=ProjectPageFragment.newInstance(it.id)
            projectPageBeans?.add(projectPageBean)
        }
        return projectPageBeans as ArrayList<ProjectPageBean>
    }

}