package com.example.practice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.practice.R;
import com.example.practice.bean.BannerBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created By 大苏打
 * on 2020/9/22
 */
public class ImageBannerAdapter extends BannerAdapter<BannerBean, ImageBannerAdapter.MyViewHolder>{

   Context context;

    public ImageBannerAdapter(List<BannerBean> datas, Context context){
        super(datas);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_banner,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindView(MyViewHolder holder, BannerBean data, int position, int size){
        Glide.with(context).load(data.getImagePath()).into(holder.ima_banner);
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ima_banner;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            ima_banner=itemView.findViewById(R.id.ima_banner);
        }
    }

}
