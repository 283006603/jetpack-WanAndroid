package com.example.practice.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.practice.R
import com.example.practice.bean.SystemListBean

/**
 * Created By 大苏打
 * on 2020/9/23
 */
class SystemLeftAdapter(layoutResId: Int, data: MutableList<SystemListBean>?) :
    BaseQuickAdapter<SystemListBean, BaseViewHolder>(layoutResId, data) {
    private var select = 0
    override fun convert(holder: BaseViewHolder, systemListBean: SystemListBean) {
        holder.itemView.isSelected = select == holder.layoutPosition
        holder.setText(R.id.tv_left, systemListBean.name)
        holder.getView<View>(R.id.ima_lable).visibility =
            if (select == holder.layoutPosition) View.VISIBLE else View.GONE
    }

    fun setSelect(select: Int) {
        this.select = select
    }
}