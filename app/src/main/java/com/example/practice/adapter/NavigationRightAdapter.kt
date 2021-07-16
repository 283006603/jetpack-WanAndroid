package com.example.practice.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.practice.R
import com.example.practice.bean.NavigationListBean
import com.example.practice.bean.NavigationListBean.ArticlesBean
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * Created By 大苏打
 * on 2020/10/23
 */
class NavigationRightAdapter(layoutResId: Int, data: List<NavigationListBean?>?) :
    BaseQuickAdapter<NavigationListBean, BaseViewHolder>(layoutResId,
        data as MutableList<NavigationListBean>?
    ) {
    override fun convert(holder: BaseViewHolder, navigationListBean: NavigationListBean) {
        holder.setText(R.id.tv_name, navigationListBean.name)
        val tagFlow = holder.getView<TagFlowLayout>(R.id.tag_flow_hot)
        tagFlow.adapter = object : TagAdapter<ArticlesBean>(navigationListBean.articles) {
            override fun getView(
                parent: FlowLayout,
                position: Int,
                articlesBean: ArticlesBean
            ): View {
                val textView = LayoutInflater.from(context)
                    .inflate(R.layout.item_tag_flow2, tagFlow, false) as TextView
                textView.text = articlesBean.title
                return textView
            }
        }
        tagFlow.setOnTagClickListener { view, position, parent ->
            mtagSelectListener?.onTagSelect(holder.layoutPosition, position)
            true
        }
    }

    var mtagSelectListener: TagSelectListener? = null
     fun setOnTagSelectListener(tagSelectListener: TagSelectListener?) {
        mtagSelectListener = tagSelectListener
    }

     interface TagSelectListener {
        fun onTagSelect(positon: Int, tagPosition: Int)
    }
}