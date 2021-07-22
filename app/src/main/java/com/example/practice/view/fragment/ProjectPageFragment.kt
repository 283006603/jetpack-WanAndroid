package com.example.practice.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.R
import com.example.practice.adapter.ProjectPageFragmentAdapter
import com.example.practice.base.BaseFragment
import com.example.practice.bean.MainArticleBean
import com.example.practice.bean.PageList
import com.example.practice.config.Constants
import com.example.practice.databinding.FragmentProjectPageBinding
import com.example.practice.view.activity.WebViewActivity
import com.example.practice.viewmodel.MainViewModel
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wljy.mvvmlibrary.annotation.Event
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 * Use the [ProjectPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProjectPageFragment : BaseFragment<FragmentProjectPageBinding>() {
    var mId = 1
    var recyclerView: RecyclerView? = null
    var refreshLayout: SmartRefreshLayout? = null
    var mainViewModel: MainViewModel? = null
    var page = 1
    var pageList: PageList<MainArticleBean>? = null
    var mainArticleBeanList: MutableList<MainArticleBean?> = ArrayList()
    var mAdapter: ProjectPageFragmentAdapter? = null


    override fun initViewModel() {
        mainViewModel = registerViewModel(MainViewModel::class.java)
    }


    companion object {
        fun newInstance(mId: Int?): ProjectPageFragment {
            val fragment = ProjectPageFragment()
            val args = Bundle()
            args.putInt(Constants.ID, mId ?: 0)
            fragment.arguments = args

            return fragment
        }
    }

    override fun initView(state: Bundle?) {
        super.initView(state)
        recyclerView = binding.recycleview
        refreshLayout = binding.refreshLayout

        if (arguments != null) {
            mId = arguments!!.getInt(Constants.ID)
        }
        recyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mAdapter= ProjectPageFragmentAdapter(R.layout.item_project,mainArticleBeanList)
        recyclerView?.adapter = mAdapter
    }

    override fun initListener() {
        super.initListener()
        //下拉刷新
        refreshLayout?.setOnRefreshListener {
            page = 1
            mainViewModel?.getProjectFragemtList(page, mId)
        }

        //上拉加载
        refreshLayout?.setOnLoadMoreListener {
            mainViewModel?.getProjectFragemtList(++page, mId)
        }

        mAdapter?.setOnItemClickListener { adapter, view, positon ->
            goWebActivity(mainArticleBeanList?.get(positon))
        }

    }

    private fun goWebActivity(mainArticleBean: MainArticleBean?) {
        val bundle = Bundle()
        bundle.putString(Constants.TITILE, mainArticleBean?.title)
        bundle.putString(Constants.AUTHOR, mainArticleBean?.author)
        bundle.putInt(Constants.ID, mainArticleBean?.id ?: 0)
        bundle.putString(Constants.URL, mainArticleBean?.link)
        activity(WebViewActivity::class.java, bundle)
    }

    override fun getRemoteData() {
        mainViewModel?.getProjectFragemtList(page, mId)
    }

    @Event(key = [Constants.GET_MAIN_ARTICLE_REFRESH, Constants.GET_MAIN_ARTICLE_LOADMORE, Constants.REQUEST_ERROR])
    fun event(key: String, value: Any) {
        if (key == Constants.GET_MAIN_ARTICLE_REFRESH) {
            pageList = value as PageList<MainArticleBean>
            Log.d("ProjectPage","pageList"+pageList)
            if (pageList?.datas != null && pageList?.size != 0) {
                mainArticleBeanList?.clear()
                mainArticleBeanList?.addAll(pageList?.datas?:ArrayList())
                mAdapter?.notifyDataSetChanged()
                //判断是不是最后一页,如果是，关闭上滑加载,连底部没有数据都不提示，这里page是从1开始
                if (page == pageList?.pageCount) {
                    refreshLayout?.setEnableLoadMore(false)
                } else {
                    refreshLayout?.setEnableLoadMore(true)
                }
            } else {
                //第一页都没有数据,显示空视图
            }
            refreshLayout?.finishRefresh()
        }else if(key==Constants.GET_MAIN_ARTICLE_LOADMORE){
            pageList=value as PageList<MainArticleBean>
            if(pageList==null||pageList?.size==0){
                //加载页没有数据，显示空视图
            }else{
                if(pageList?.datas!=null){
                    mainArticleBeanList?.addAll(pageList?.datas?:ArrayList())
                    mAdapter?.notifyDataSetChanged()
                }

                //判断是否到了底页，得提示，告知用户，这里page是从1开始
                if(page ==pageList?.pageCount){
                    refreshLayout?.finishLoadMoreWithNoMoreData()
                }else{
                    refreshLayout?.finishLoadMore()
                }
            }
        }else if(key==Constants.REQUEST_ERROR){
            refreshLayout?.finishRefresh()
            refreshLayout?.finishLoadMore()
        }
    }

}