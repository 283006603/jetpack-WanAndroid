package com.example.practice.adapter

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.practice.R
import com.example.practice.bean.MainArticleBean

/**
 * Created By 大苏打
 * on 2020/9/21
 */
class ProjectPageFragmentAdapter(layoutResId: Int, data: MutableList<MainArticleBean>?) :
    BaseQuickAdapter<MainArticleBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, mainArticleBean: MainArticleBean) {
        Log.d("ProjectPageFragmentAdap", mainArticleBean.envelopePic)
        Log.d("ProjectPageFragmentAdap", mainArticleBean.title)
        holder.setText(R.id.tv_title, mainArticleBean.title)
        holder.setText(
            R.id.tv_shareuser,
            if (TextUtils.isEmpty(mainArticleBean.shareUser)) "作者：" + mainArticleBean.author else "分享人：" + mainArticleBean.shareUser
        )
        holder.setText(R.id.tv_time, "时间：" + mainArticleBean.niceDate)
        Glide.with(context).load(mainArticleBean.envelopePic).centerCrop()
            .into((holder.getView<View>(R.id.ima_show) as ImageView))
    }
}