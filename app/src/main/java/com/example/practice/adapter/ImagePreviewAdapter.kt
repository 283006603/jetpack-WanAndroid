package com.example.practice.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.practice.R
import com.example.practice.bean.GankMeiziBean
import com.example.practice.view.activity.MeiZiDetailActivity
import com.github.chrisbanes.photoview.PhotoView

class ImagePreviewAdapter(layoutResId: Int, data: MutableList<GankMeiziBean>) :
    BaseQuickAdapter<GankMeiziBean, BaseViewHolder>(layoutResId, data ) {
    override fun convert(helper: BaseViewHolder, item: GankMeiziBean) {
        val photoView = helper.getView<PhotoView>(R.id.pv_photo)
        photoView.setOnOutsidePhotoTapListener { finishActivity() }
        photoView.setOnPhotoTapListener { view, x, y -> finishActivity() }
        Glide.with(context)
            .load(item.url)
            .into(photoView)
    }

    private fun finishActivity() {
        if (context is MeiZiDetailActivity) {
            (context as MeiZiDetailActivity).finish()
        }
    }
}