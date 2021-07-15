package com.example.practice.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.R
import com.example.practice.adapter.ImageBannerAdapter
import com.example.practice.adapter.MainArticleAdapter
import com.example.practice.adapter.MyGridViewAdpter
import com.example.practice.adapter.MyViewPagerAdapter
import com.example.practice.base.BaseFragment
import com.example.practice.bean.BannerBean
import com.example.practice.bean.MainArticleBean
import com.example.practice.bean.PageList
import com.example.practice.bean.WeChatArticle
import com.example.practice.config.Constants
import com.example.practice.databinding.FragmentMainBinding
import com.example.practice.view.activity.WeChatDetailActivity
import com.example.practice.view.activity.WebViewActivity
import com.example.practice.viewmodel.MainViewModel
import com.example.practice.widge.GridViewPager
import com.example.practice.widge.MyGridView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wljy.mvvmlibrary.annotation.Event
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.RectangleIndicator
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class MainFragment : BaseFragment<FragmentMainBinding?>() {

    private var mainRecycleView: RecyclerView? = null
    private var refreshLayout: SmartRefreshLayout? = null
    private var mainViewModel: MainViewModel? = null
    private var page: Int = 0

    private var bannerBeanList: MutableList<BannerBean> = ArrayList()
    private var mainArticleBeanList: MutableList<MainArticleBean> = ArrayList()
    private var weChatArticleList: MutableList<WeChatArticle> = ArrayList()

    private var adapter: MainArticleAdapter? = null
    var pageList: PageList<MainArticleBean>? = null
    var mainBanner: Banner<BannerBean, ImageBannerAdapter>? = null
    var imageAdapter: ImageBannerAdapter? = null
    var viewpager: GridViewPager? = null

    var totalPage: Int = 0
    var mPageSize: Int = 10
    var viewPagerList: MutableList<View>? = null


    override fun initView(state: Bundle?) {
        super.initView(state)
        refreshLayout = binding?.refreshLayout
        mainRecycleView = binding?.mainRecycleview
        mainRecycleView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = MainArticleAdapter(R.layout.item_main_article, mainArticleBeanList)
        mainRecycleView?.adapter = adapter
        //---
        val headView = layoutInflater.inflate(R.layout.item_main_header, null)
        mainBanner = headView?.findViewById(R.id.main_banner)
        viewpager = headView?.findViewById(R.id.view_pager)
        //---
        imageAdapter = ImageBannerAdapter(bannerBeanList, context!!)
        mainBanner?.currentItem = 1
        mainBanner?.indicator = RectangleIndicator(context)
        mainBanner?.setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
        mainBanner?.setIndicatorMargins(IndicatorConfig.Margins(0, 0, 100, 50))
        //mainBanner.setBannerGalleryEffect(150, 20); //添加画廊效果
        mainBanner?.setAdapter(imageAdapter)?.addBannerLifecycleObserver(this)
        //=====
        //=====
        adapter?.addHeaderView(headView, -1, LinearLayout.VERTICAL)
    }

    override fun initListener() {
        super.initListener()
        //下拉刷新
        refreshLayout?.setOnRefreshListener {
            page = 0
            mainViewModel?.getMainArticle(page)
        }
        //上拉加载
        refreshLayout?.setOnLoadMoreListener {
            mainViewModel?.getMainArticle(++page)
        }

        //recycleview点击事件
        adapter?.setOnItemClickListener { adapter, view, position ->
            goWebActivity(mainArticleBeanList?.get(position))
        }

        //banner点击时间
        mainBanner?.setOnBannerListener { data, position ->
            goWebActivityForBanner(bannerBeanList?.get(position))
        }
    }

    override fun initViewModel() {
        mainViewModel = registerViewModel(MainViewModel::class.java)
    }

    override fun getRemoteData() {
        //获取首页Banner
        mainViewModel?.getBanner()
        //获取首页文章列表
        mainViewModel?.getMainArticle(page)
        //获取微信公众号
        mainViewModel?.getWeChatArticle()
    }


    fun goWebActivityForBanner(bannerBean: BannerBean?) {
        val bundle = Bundle()
        bundle.putString(Constants.TITILE, bannerBean?.title)
        bundle.putString(Constants.AUTHOR, "")
        bundle.putInt(Constants.ID, bannerBean?.id ?: 0)
        bundle.putString(Constants.URL, bannerBean?.url)
        activity(WebViewActivity::class.java, bundle)
    }

    fun goWeChatDetailActivity(weChatArticle: WeChatArticle?) {
        val bundle = Bundle()
        bundle.putInt(Constants.ID, weChatArticle?.id ?: 0)
        bundle.putString(Constants.TITILE, weChatArticle?.name)
        activity(WeChatDetailActivity::class.java, bundle)
    }

    fun goWebActivity(mainArticleBean: MainArticleBean?) {
        val bundle = Bundle()
        bundle.putString(Constants.TITILE, mainArticleBean?.title)
        bundle.putString(Constants.AUTHOR, mainArticleBean?.author)
        bundle.putInt(Constants.ID, mainArticleBean?.id ?: 0)
        bundle.putString(Constants.URL, mainArticleBean?.link)
        activity(WebViewActivity::class.java, bundle)
    }

    @Event(key = [Constants.GET_MAIN_ARTICLE_REFRESH, Constants.GET_MAIN_ARTICLE_LOADMORE, Constants.REQUEST_ERROR, Constants.GET_MAIN_BANNER, Constants.GET_MAIN_WECHAT_ARTICLE])
    fun onEvent(key: String, value: Any) {
        if (key == Constants.GET_MAIN_ARTICLE_REFRESH) {
            pageList = value as PageList<MainArticleBean>
            if (pageList?.getDatas() != null && pageList?.getDatas()?.size != 0) {
                mainArticleBeanList.clear()
                mainArticleBeanList.addAll(pageList?.getDatas() ?: ArrayList())
                adapter?.notifyDataSetChanged()
                //判断是不是最后一页,如果是，关闭上滑加载,连底部没有数据都不提示
                if (page == pageList?.getPageCount() ?: 1 - 1) {
                    refreshLayout?.setEnableLoadMore(false)
                } else {
                    refreshLayout?.setEnableLoadMore(true)
                }
            } else {
                //第一页都没有数据,显示空视图
            }
            refreshLayout?.finishRefresh()
        } else if (key == Constants.GET_MAIN_ARTICLE_LOADMORE) {
            pageList = value as PageList<MainArticleBean>
            if (pageList == null || pageList?.getDatas()?.size == 0) {
                //加载页面没有数据，显示空视图
            } else {
                if (pageList?.getDatas() != null) {
                    mainArticleBeanList.addAll(pageList?.getDatas() ?: ArrayList())
                    adapter?.notifyDataSetChanged()
                }
                //判断是否到了底页,得提示,告知用户
                if (page == pageList?.getPageCount() ?: 1 - 1) {
                    refreshLayout?.finishLoadMoreWithNoMoreData()
                } else {
                    refreshLayout?.finishLoadMore()
                }
            }
        } else if (key == Constants.GET_MAIN_BANNER) {
            bannerBeanList.addAll(value as List<BannerBean>)
            imageAdapter?.notifyDataSetChanged()
        } else if (key == Constants.GET_MAIN_WECHAT_ARTICLE) {
            weChatArticleList = value as MutableList<WeChatArticle>
            loadGridViewPager()
        } else if (key == Constants.REQUEST_ERROR) {
            refreshLayout?.finishRefresh()
            refreshLayout?.finishLoadMore()
            //显示网络错误视图
        }
    }

    private fun loadGridViewPager() {
        totalPage = Math.ceil(weChatArticleList?.size * 1.0 / mPageSize).toInt()
        viewPagerList = ArrayList()
        for (i in 0 until totalPage) {
            //每个页面都是inflate出一个新实例
            val gridView = View.inflate(context, R.layout.gridview_main, null) as MyGridView
            gridView.adapter = MyGridViewAdpter(context, weChatArticleList, i, mPageSize)
            gridView.numColumns = 5
            //添加item点击监听
            gridView.onItemClickListener =
                AdapterView.OnItemClickListener { arg0, arg1, position, arg3 -> // TODO Auto-generated method stub
                    val obj = gridView.getItemAtPosition(position)
                    if (obj != null && obj is WeChatArticle) {
                        goWeChatDetailActivity(weChatArticleList[position])
                    }
                }
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList?.add(gridView)
        }
        //设置ViewPager适配器
        viewpager?.adapter = MyViewPagerAdapter(viewPagerList)
    }

}