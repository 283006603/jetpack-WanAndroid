package com.example.practice.view.fragment

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.R
import com.example.practice.adapter.SystemLeftAdapter
import com.example.practice.adapter.SystemRightAdapter
import com.example.practice.base.BaseFragment
import com.example.practice.bean.SystemListBean
import com.example.practice.bean.SystemListBean.ChildrenBean
import com.example.practice.config.Constants
import com.example.practice.databinding.FragmentSystemBinding
import com.example.practice.view.activity.SystemDetailActivity
import com.example.practice.viewmodel.MainViewModel
import com.wljy.mvvmlibrary.annotation.Event
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [SystemFragment. factory method to
 * create an instance of this fragment.仿拼多多或扑扑的分类栏
 */
class SystemFragment : BaseFragment<FragmentSystemBinding>() {

    var leftRecycleView: RecyclerView? = null
    var rightRecycleView: RecyclerView? = null
    var mainViewModel: MainViewModel? = null
    val leftList: MutableList<SystemListBean> = ArrayList()
    val rightList: MutableList<SystemListBean.ChildrenBean> = ArrayList()
    var leftAdapter: SystemLeftAdapter? = null
    var rightAdapter: SystemRightAdapter? = null
    var select: Int = 0
    var children: MutableList<SystemListBean.ChildrenBean>? = null


    override fun initView(state: Bundle?) {
        super.initView(state)
        leftRecycleView = binding?.leftRecycleview
        rightRecycleView = binding?.rightRecycleview
        //左边
        leftRecycleView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        leftAdapter = SystemLeftAdapter(R.layout.item_system_left, leftList)
        //        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_divider_space, null));
        //        leftRecycleview.addItemDecoration(itemDecoration);
        leftRecycleView?.adapter = leftAdapter
        leftAdapter?.setSelect(select)
        //右边
        rightRecycleView?.layoutManager = GridLayoutManager(context, 2)
        rightAdapter = SystemRightAdapter(R.layout.item_system_right, rightList)
        rightRecycleView?.adapter = rightAdapter
    }

    override fun initListener() {
        super.initListener()
        leftAdapter?.setOnItemClickListener { adapter, view, position ->
            if (select == position) return@setOnItemClickListener
            leftAdapter?.setSelect(position)
            rightList.clear()
            children = leftList?.get(position)?.children as MutableList<ChildrenBean>?
            rightList?.addAll(children ?: ArrayList())
            leftAdapter?.notifyDataSetChanged()
            rightAdapter?.notifyDataSetChanged()
            select = position
        }

        rightAdapter?.setOnItemClickListener { adapter, view, position ->
            goSystemDetailActivity(rightList.get(position));
        }
    }

    private fun goSystemDetailActivity(childrenBean: ChildrenBean) {
        val bundle = Bundle()
        bundle.putSerializable("childrenBean", childrenBean)
        activity(SystemDetailActivity::class.java, bundle)
    }

    override fun initViewModel() {
        mainViewModel = registerViewModel(MainViewModel::class.java)
    }

    override fun getRemoteData() {
        mainViewModel?.getSystemList()
    }

    @Event(key = [Constants.GET_SYSTEM_LIST, Constants.REQUEST_ERROR])
    fun event(key: String, value: Any) {
        if (key == Constants.GET_SYSTEM_LIST) {
            if (value != null) {
                leftList.addAll(value as MutableList<SystemListBean>)
                leftAdapter?.notifyDataSetChanged()
                //初始化右边的recycleview
                rightList.addAll(leftList.get(0)?.children?:ArrayList())
                rightAdapter?.notifyDataSetChanged()
            } else if (key == Constants.REQUEST_ERROR) {

            }
        }
    }

}

