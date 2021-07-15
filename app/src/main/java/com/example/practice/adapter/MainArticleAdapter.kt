package com.example.practice.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.practice.R
import com.example.practice.bean.MainArticleBean


/**
 * Created By 大苏打
 * on 2020/9/21
 */
class MainArticleAdapter (layoutResId:Int, data:MutableList<MainArticleBean>) :
    BaseQuickAdapter<MainArticleBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, mainArticleBean: MainArticleBean) {
        holder.setText(R.id.tv_title, mainArticleBean.title)
        holder.setText(R.id.tv_chaptername, "分类：" + mainArticleBean.chapterName)
        holder.setText(
            R.id.tv_shareuser,
            if (TextUtils.isEmpty(mainArticleBean.shareUser)) "作者：" + mainArticleBean.author else "分享人：" + mainArticleBean.shareUser
        )
        holder.setText(R.id.tv_time, "时间：" + mainArticleBean.niceDate)
    }
}
