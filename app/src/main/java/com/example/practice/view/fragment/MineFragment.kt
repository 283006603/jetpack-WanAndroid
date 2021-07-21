package com.example.practice.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.practice.R
import com.example.practice.adapter.MineAdapter
import com.example.practice.base.BaseFragment
import com.example.practice.databinding.FragmentMineBinding
import com.example.practice.utils.CacheToolsUtil
import com.example.practice.utils.FullPopupwindow
import com.example.practice.utils.SharePrefUtil
import com.example.practice.view.activity.LoginActivity
import com.example.practice.view.activity.MeiZiActivity
import com.example.practice.widge.QQHeaderSrollView
import java.util.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class MineFragment : BaseFragment<FragmentMineBinding?>() {
    var qqHeaderSrollView: QQHeaderSrollView? = null
    var popupWindow: FullPopupwindow? = null
    private var iv: ImageView? = null
    var list: MutableList<Map<String, Any>> = ArrayList()
    private var mineAdapter: MineAdapter? = null
    override fun initViewModel() {}
    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        qqHeaderSrollView = binding?.svScroll
        initData()
        mineAdapter = MineAdapter(list, activity, R.layout.item_mine)
        //获取到头部的View
        val header = View.inflate(activity, R.layout.listview_header, null)
        //获取到头部的View的图片控件
        iv = header.findViewById<View>(R.id.layout_header_image) as ImageView
        //将imageView传入到ListView中
        qqHeaderSrollView?.setZoomImageView(iv)
        //将头部的View设置给ListView的HeaderView
        qqHeaderSrollView?.addHeaderView(header)
        //设置适配器
        qqHeaderSrollView?.adapter = mineAdapter
    }

    private fun initData() {
        val map1: MutableMap<String, Any> = HashMap()
        map1["title"] = "搜藏"
        map1["image"] = R.drawable.ic_mine_favorite
        val map2: MutableMap<String, Any> = HashMap()
        map2["title"] = "开车"
        map2["image"] = R.drawable.ic_mine_happy
        val map3: MutableMap<String, Any> = HashMap()
        map3["title"] = "设置"
        map3["image"] = R.drawable.ic_mine_setting
        val map4: MutableMap<String, Any> = HashMap()
        map4["title"] = "关于"
        map4["image"] = R.drawable.ic_mine_about
        val map5: MutableMap<String, Any> = HashMap()
        map5["title"] = "清除缓存"
        map5["image"] = R.drawable.ic_mine_favorite
        val map6: MutableMap<String, Any> = HashMap()
        map6["title"] = "切换账号"
        map6["image"] = R.drawable.ic_mine_favorite
        list.add(map1)
        list.add(map2)
        list.add(map3)
        list.add(map4)
        list.add(map5)
        list.add(map6)
    }

    override fun initListener() {
        super.initListener()
        qqHeaderSrollView?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                Log.d("MineFragment", "position:$position")
                when (position) {
                    1, 3 -> Toast.makeText(
                        mActivity,
                        R.string.str_function_not_open,
                        Toast.LENGTH_SHORT
                    ).show()
                    2 -> activity(MeiZiActivity::class.java)
                    4 -> showAboutPop()
                    5 -> try {
                        showCachelDialog()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    6 -> {
                        SharePrefUtil.clear(context)
                        activity?.finish()
                        activity(LoginActivity::class.java)
                    }

                }
            }


    }

    override fun getRemoteData() {}

    private fun showAboutPop() {
        val view = LayoutInflater.from(context).inflate(R.layout.pop_about_mine, null, false)
        if (popupWindow == null) {
            popupWindow = FullPopupwindow(context)
            popupWindow?.contentView = view
        }
        view?.setOnClickListener {
            popupWindow?.dismiss()
        }
        popupWindow?.showAtLocation(activity?.window?.decorView, Gravity.START, 0, 0)
    }

    private fun showCachelDialog() {
        val normalDialog = AlertDialog.Builder(
            context!!
        )
        normalDialog.setTitle("清除缓存")
        normalDialog.setMessage("清除全部缓存:" + CacheToolsUtil.getTotalCacheSize(context))
        normalDialog.setPositiveButton("确定") { dialog, which ->
            CacheToolsUtil.clearAllCache(
                context
            )
        }
        normalDialog.setNegativeButton("关闭") { dialog, which -> }
        normalDialog.show()
    }
}