package com.example.practice.view.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.R
import com.example.practice.adapter.MainArticleAdapter
import com.example.practice.base.BaseActivity
import com.example.practice.bean.MainArticleBean
import com.example.practice.bean.PageList
import com.example.practice.bean.SystemListBean.ChildrenBean
import com.example.practice.config.Constants
import com.example.practice.databinding.ActivityWeChatDetailBinding
import com.example.practice.viewmodel.MainViewModel
import com.gyf.immersionbar.ImmersionBar
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wljy.mvvmlibrary.annotation.Event
import java.util.*
import kotlin.collections.ArrayList

class SystemDetailActivity : BaseActivity<ActivityWeChatDetailBinding?>() {
    var toolbar: Toolbar? = null
    var recycleview: RecyclerView? = null
    var refreshLayout: SmartRefreshLayout? = null
    private var mainViewModel: MainViewModel? = null
    private var id = 0
    var mainArticleBeanList: MutableList<MainArticleBean> = ArrayList()
    private var adapter: MainArticleAdapter? = null
    private var page = 0
    private var pageList: PageList<MainArticleBean>? = null
    private var name: String? = null
    private var childrenBean: ChildrenBean? = null
    override fun useImmersionBar(): Boolean {
        return false
    }

    override fun initStatusBar() {
        ImmersionBar.with(this).fitsSystemWindows(true)
            .statusBarColor(com.wljy.mvvmlibrary.R.color.icon_enabled).statusBarDarkFont(true)
            .init()
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        toolbar = binding?.toolbar?.toolbar
        recycleview = binding?.recycleview
        refreshLayout = binding?.refreshLayout
        initData()
        recycleview?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = MainArticleAdapter(R.layout.item_main_article, mainArticleBeanList)
        recycleview?.adapter = adapter
    }

        fun initData() {
            val extras = intent.extras
            if (extras != null) {
                childrenBean = extras.getSerializable("childrenBean") as ChildrenBean?
                id = childrenBean?.id?:0
                name = childrenBean?.name
            }
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = name
        }

    override fun initListener() {
        toolbar?.setNavigationOnClickListener { finish() }
        //下拉刷新
        refreshLayout?.setOnRefreshListener {
            page = 0
            mainViewModel?.getMainArticle(page)
        }
        //上拉加载
        refreshLayout?.setOnLoadMoreListener { mainViewModel?.getMainArticle(++page) }
        adapter?.setOnItemClickListener { adapter, view, position ->
            goWebActivity(
                mainArticleBeanList[position]
            )
        }
    }

    private fun goWebActivity(mainArticleBean: MainArticleBean) {
        val bundle = Bundle()
        bundle.putString(Constants.TITILE, mainArticleBean.title)
        bundle.putString(Constants.AUTHOR, mainArticleBean.author)
        bundle.putInt(Constants.ID, mainArticleBean.id)
        bundle.putString(Constants.URL, mainArticleBean.link)
        activity(WebViewActivity::class.java, bundle)
    }

    override fun getRemoteData() {
        mainViewModel?.getSystemDetail(page, id)
    }

    override fun initViewModel() {
        mainViewModel = registerViewModel(MainViewModel::class.java)
    }

    @Event(key = [Constants.GET_MAIN_ARTICLE_REFRESH, Constants.GET_MAIN_ARTICLE_LOADMORE, Constants.REQUEST_ERROR])
    fun onEvent(key: String, value: Any?) {
        if (key == Constants.GET_MAIN_ARTICLE_REFRESH) {
            pageList = value as PageList<MainArticleBean>?
            if (pageList?.datas != null && pageList?.datas?.size != 0) {
                mainArticleBeanList.clear()
                mainArticleBeanList.addAll(pageList?.datas?:ArrayList())
                adapter?.notifyDataSetChanged()
                //判断是不是最后一页,如果是，关闭上滑加载,连底部没有数据都不提示
                if (page == pageList?.pageCount?:1 - 1) {
                    refreshLayout?.setEnableLoadMore(false)
                } else {
                    refreshLayout?.setEnableLoadMore(true)
                }
            } else {
                //第一页都没有数据,显示空视图
            }
            refreshLayout?.finishRefresh()
        } else if (key == Constants.GET_MAIN_ARTICLE_LOADMORE) {
            pageList = value as PageList<MainArticleBean>?
            if (pageList == null || pageList?.datas?.size == 0) {
                //加载页面没有数据，显示空视图
            } else {
                if (pageList?.datas != null) {
                    mainArticleBeanList.addAll(pageList?.datas?:ArrayList())
                    adapter?.notifyDataSetChanged()
                }
                //判断是否到了底页,得提示,告知用户
                if (page == pageList?.pageCount?:1 - 1) {
                    refreshLayout?.finishLoadMoreWithNoMoreData()
                } else {
                    refreshLayout?.finishLoadMore()
                }
            }
        } else if (key == Constants.REQUEST_ERROR) {
            refreshLayout?.finishRefresh()
            refreshLayout?.finishLoadMore()
            //显示网络错误视图
        }
    }
}