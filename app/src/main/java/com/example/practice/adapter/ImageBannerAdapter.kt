package com.example.practice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practice.R
import com.example.practice.adapter.ImageBannerAdapter.MyViewHolder
import com.example.practice.bean.BannerBean
import com.youth.banner.adapter.BannerAdapter
import java.util.zip.Inflater

/**
 * Created By 大苏打
 * on 2020/9/22
 */
class ImageBannerAdapter(datas:List<BannerBean>, var context: Context) :
    BannerAdapter<BannerBean, RecyclerView.ViewHolder>(datas) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view =LayoutInflater.from(context).inflate(R.layout.item_main_banner,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindView(
        holder: RecyclerView.ViewHolder?,
        data: BannerBean?,
        position: Int,
        size: Int
    ) {
        val myViewHolder= holder as MyViewHolder
        Glide.with(context).load(data?.imagePath).into(myViewHolder.imaBanner)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imaBanner=itemView.findViewById<ImageView>(R.id.ima_banner)
    }
}
