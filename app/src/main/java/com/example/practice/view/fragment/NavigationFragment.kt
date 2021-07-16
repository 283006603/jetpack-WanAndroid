package com.example.practice.view.fragment

import android.os.Bundle
import android.util.Log
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.practice.R
import com.example.practice.adapter.NavigationLeftAdapter
import com.example.practice.adapter.NavigationRightAdapter
import com.example.practice.base.BaseFragment
import com.example.practice.bean.NavigationListBean
import com.example.practice.bean.NavigationListBean.ArticlesBean
import com.example.practice.config.Constants
import com.example.practice.databinding.FragmentNavigationBinding
import com.example.practice.view.activity.WebViewActivity
import com.example.practice.viewmodel.MainViewModel
import com.wljy.mvvmlibrary.annotation.Event
import java.util.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class NavigationFragment : BaseFragment<FragmentNavigationBinding?>() {
    var leftRecycleview: RecyclerView? = null
    var rightRecycleview: RecyclerView? = null
    var viewModel: MainViewModel? = null
    var list: MutableList<NavigationListBean?> = ArrayList()
    var leftAdapter: NavigationLeftAdapter? = null
    var rightAdapter: NavigationRightAdapter? = null
    private var leftSelect = 0
    private var rightVisible = 0
    private var rightLinearLayoutManager: LinearLayoutManager? = null
    private var leftLinearLayoutManager: LinearLayoutManager? = null
    var TAG = "NavigationFragment"
    private var mFirstVisiblePosition = 0
    private var mLastVisiblePosition = 0
    override fun initViewModel() {
        viewModel = registerViewModel(MainViewModel::class.java)
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        leftRecycleview = binding?.leftRecycleview
        rightRecycleview = binding?.rightRecycleview
        leftLinearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        leftRecycleview?.layoutManager = leftLinearLayoutManager
        leftAdapter = NavigationLeftAdapter(R.layout.item_system_left, list)
        leftRecycleview?.adapter = leftAdapter
        leftAdapter?.setSelect(leftSelect)
        //--
        rightLinearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rightRecycleview?.layoutManager = rightLinearLayoutManager
        rightAdapter = NavigationRightAdapter(R.layout.item_navigation_right, list)
        rightRecycleview?.adapter = rightAdapter
    }

    override fun initListener() {
        super.initListener()
        leftAdapter?.setOnItemClickListener(OnItemClickListener { adapter, view, position ->
            Log.d("NavigationFragment", "position:$position")
            rightLinearLayoutManager?.scrollToPositionWithOffset(position, 0)
            if (leftSelect == position) return@OnItemClickListener
            leftAdapter?.setSelect(position)
            leftAdapter?.notifyDataSetChanged()
            leftSelect = position
            //--------
        })
//        rightAdapter?.setOnTagSelectListener { position, tagPosition ->
//            goWebActivity(list[position]?.articles?.get(tagPosition))
//        }
        rightAdapter?.setOnTagSelectListener(object : NavigationRightAdapter.TagSelectListener {
            override fun onTagSelect(positon: Int, tagPosition: Int) {
                goWebActivity(list[positon]?.articles?.get(tagPosition))
            }

        })
        rightRecycleview?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE || newState == ViewPager.SCROLL_STATE_DRAGGING) {
                    // DES: 找出当前可视Item位置
                    val layoutManager = rightRecycleview?.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val linearManager = layoutManager
                        mFirstVisiblePosition = linearManager.findFirstVisibleItemPosition()
                        mLastVisiblePosition = linearManager.findLastVisibleItemPosition()
                    }
                    if (mFirstVisiblePosition == rightVisible) {
                        return
                    } else {
                        leftLinearLayoutManager?.scrollToPositionWithOffset(
                            mFirstVisiblePosition,
                            0
                        )
                        leftAdapter?.setSelect(mFirstVisiblePosition)
                        leftAdapter?.notifyDataSetChanged()
                        leftSelect = mFirstVisiblePosition
                        rightVisible = mFirstVisiblePosition
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun goWebActivity(mainArticleBean: ArticlesBean?) {
        val bundle = Bundle()
        bundle.putString(Constants.TITILE, mainArticleBean?.title)
        bundle.putString(Constants.AUTHOR, mainArticleBean?.author)
        bundle.putInt(Constants.ID, mainArticleBean?.id ?: 0)
        bundle.putString(Constants.URL, mainArticleBean?.link)
        activity(WebViewActivity::class.java, bundle)
    }

    override fun getRemoteData() {
        viewModel?.getNavigation()
    }

    @Event(key = [Constants.REQUEST_ERROR, Constants.GET_NAVIGATION_LIST])
    fun event(key: String, value: Any?) {
        if (key == Constants.GET_NAVIGATION_LIST) {
            if (value != null) {
                list.addAll(value as List<NavigationListBean?>)
                leftAdapter?.notifyDataSetChanged()
                rightAdapter?.notifyDataSetChanged()
            }
        } else if (key == Constants.REQUEST_ERROR) {
        }
    }
}