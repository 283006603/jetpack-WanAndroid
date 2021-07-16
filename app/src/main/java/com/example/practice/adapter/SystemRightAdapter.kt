package com.example.practice.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.practice.R
import com.example.practice.bean.SystemListBean
import com.example.practice.bean.SystemListBean.ChildrenBean

/**
 * Created By 大苏打
 * on 2020/9/23
 */
class SystemRightAdapter(layoutResId: Int, data: MutableList<SystemListBean.ChildrenBean>) :
    BaseQuickAdapter<ChildrenBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, childrenBean: ChildrenBean) {
        holder.setText(R.id.tv_right, childrenBean.name)
    }
}