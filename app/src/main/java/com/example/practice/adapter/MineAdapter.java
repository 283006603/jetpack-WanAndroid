package com.example.practice.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practice.R;

import java.util.List;
import java.util.Map;

/**
 * Created By 大苏打
 * on 2021/4/29
 */
public class MineAdapter extends BaseAdapter{

    List<Map<String,Object>> list;
    Context context;
    int layout;


    public MineAdapter(List<Map<String, Object>> list, Context context,int layout){
        this.list = list;
        this.context = context;
        this.layout=layout;
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d("MineAdapter", "position:" + position);
        MyViewholder viewholder;
        if(convertView==null){
            viewholder=new MyViewholder();
            convertView= LayoutInflater.from(context).inflate(layout,null);
            viewholder.ima_icon=convertView.findViewById(R.id.ima_icon);
            viewholder.tv_detail=convertView.findViewById(R.id.tv_detail);
            convertView.setTag(viewholder);
        }else{
            viewholder= (MyViewholder) convertView.getTag();
        }

        viewholder.tv_detail.setText((String) list.get(position).get("title"));
        viewholder.ima_icon.setImageResource((Integer) list.get(position).get("image"));
        return convertView;
    }

    class MyViewholder{
        ImageView ima_icon;
        TextView tv_detail;
    }
}
