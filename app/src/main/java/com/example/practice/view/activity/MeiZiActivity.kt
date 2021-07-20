package com.example.practice.view.activity

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.R
import com.example.practice.adapter.GankMeiziAdapter
import com.example.practice.base.BaseActivity
import com.example.practice.bean.GankLzyBaseResponse
import com.example.practice.bean.GankMeiziBean
import com.example.practice.config.Constants
import com.example.practice.databinding.ActivityMeiZiBinding
import com.example.practice.viewmodel.MainViewModel
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wljy.mvvmlibrary.annotation.Event
import java.util.*
import kotlin.collections.ArrayList

class MeiZiActivity : BaseActivity<ActivityMeiZiBinding?>() {
    var recyclerView: RecyclerView? = null
    var refreshLayout: SmartRefreshLayout? = null
    private var mainViewModel: MainViewModel? = null
    var page = 1
    var pagecount = 10
    private var gankLzyBaseResponse: GankLzyBaseResponse<Any>? = null
    private var gankMeiziList: MutableList<GankMeiziBean?>? = null
    private val list: MutableList<GankMeiziBean?> = ArrayList()
    private var adapter: GankMeiziAdapter? = null
    override fun useImmersionBar(): Boolean {
        return true
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        recyclerView = binding?.recycleview
        refreshLayout = binding?.refreshLayout
        recyclerView?.layoutManager = GridLayoutManager(this, 2)
        adapter = GankMeiziAdapter(R.layout.item_meizi, list)
        recyclerView?.adapter = adapter
    }

    override fun initListener() {
        //下拉刷新
        refreshLayout?.setOnRefreshListener {
            page = 1
            mainViewModel?.getMeizi(page, pagecount)
        }
        //上拉加载
        refreshLayout?.setOnLoadMoreListener { mainViewModel?.getMeizi(++page, pagecount) }
        adapter?.setOnItemClickListener { adapter, view, position ->
            if (adapter != null && list.size != 0) {
                val bundle = Bundle()
                bundle.putParcelableArrayList("meizis", list as ArrayList<out Parcelable?>)
                bundle.putInt("position", position)
                activity(MeiZiDetailActivity::class.java, bundle)
            }
        }
    }

    override fun getRemoteData() {
        mainViewModel?.getMeizi(page, pagecount)
    }

    override fun initViewModel() {
        mainViewModel = registerViewModel(MainViewModel::class.java)
    }

    @Event(key = [Constants.GET_MEIZI_RESULT_REFRESH, Constants.GET_MEIZI_RESULT_LOADMORE, Constants.REQUEST_ERROR])
    fun event(key: String, value: Any?) {
        if (key == Constants.GET_MEIZI_RESULT_REFRESH) {
            gankLzyBaseResponse = value as GankLzyBaseResponse<Any>?
            gankMeiziList = gankLzyBaseResponse?.data as MutableList<GankMeiziBean?>
            if (gankMeiziList != null && gankMeiziList?.size != 0) {
                list.clear()
                list.addAll(gankMeiziList?:ArrayList())
                adapter?.notifyDataSetChanged()
                //判断是不是最后一页,如果是，关闭上滑加载,连底部没有数据都不提示
                if (list.size >= gankLzyBaseResponse?.total_counts?:0) {
                    refreshLayout?.setEnableLoadMore(false)
                } else {
                    refreshLayout?.setEnableLoadMore(true)
                }
            } else {
                //第一页都没有数据,显示空视图
            }
            refreshLayout?.finishRefresh()
        } else if (key == Constants.GET_MEIZI_RESULT_LOADMORE) {
            gankLzyBaseResponse = value as GankLzyBaseResponse<Any>?
            gankMeiziList = gankLzyBaseResponse?.data as MutableList<GankMeiziBean?>
            if (gankMeiziList == null || gankMeiziList?.size == 0) {
                Log.d("MeiZiActivity", "aaa")
                //加载页面没有数据，显示空视图
            } else {
                Log.d("MeiZiActivity", "bbb")
                if (gankMeiziList != null) {
                    list.addAll(gankMeiziList?:ArrayList())
                    adapter?.notifyDataSetChanged()
                }
                //判断是否到了底页,得提示,告知用户
                if (list.size >= gankLzyBaseResponse?.total_counts?:0) {
                    Log.d("MeiZiActivity", "ccc")
                    refreshLayout?.finishLoadMoreWithNoMoreData()
                } else {
                    Log.d("MeiZiActivity", "ddd")
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