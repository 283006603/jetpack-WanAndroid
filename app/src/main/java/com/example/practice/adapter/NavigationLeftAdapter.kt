package com.example.practice.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.practice.R
import com.example.practice.bean.NavigationListBean

/**
 * Created By 大苏打
 * on 2020/9/23
 */
class NavigationLeftAdapter(layoutResId: Int, data: MutableList<NavigationListBean?>?) :
    BaseQuickAdapter<NavigationListBean, BaseViewHolder>(
        layoutResId, data as MutableList<NavigationListBean>?

    ) {
    private var select = 0
    override fun convert(holder: BaseViewHolder, navigationListBean: NavigationListBean) {
        holder.itemView.isSelected = select == holder.layoutPosition
        holder.setText(R.id.tv_left, navigationListBean.name)
        holder.getView<View>(R.id.ima_lable).visibility =
            if (select == holder.layoutPosition) View.VISIBLE else View.GONE
    }

    fun setSelect(select: Int) {
        this.select = select
    }
}