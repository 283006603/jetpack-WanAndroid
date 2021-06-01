package com.example.practice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practice.R;
import com.example.practice.bean.WeChatArticle;
import com.example.practice.widge.CircleImageView;

import java.util.List;

/**
 * Created By 大苏打
 * on 2021/5/31
 */
public class MyGridViewAdpter extends BaseAdapter{

    private Context context;
    private List<WeChatArticle> lists;//数据源
    private int mIndex; // 页数下标，标示第几页，从0开始
    private int mPargerSize;// 每页显示的最大的数量

    private int[] colors = {0xffec407a, 0xffab47bc, 0xff29b6f6, 0xff7e57c2, 0xffe24073, 0xffee8360, 0xff26a69a, 0xffef5350, 0xff2baf2b, 0xffffa726};

    public MyGridViewAdpter(Context context, List<WeChatArticle> lists, int mIndex, int mPargerSize){
        this.context = context;
        this.lists = lists;
        this.mIndex = mIndex;
        this.mPargerSize = mPargerSize;
    }

    /**
     * 先判断数据及的大小是否显示满本页lists.size() > (mIndex + 1)*mPagerSize
     * 如果满足，则此页就显示最大数量lists的个数
     * 如果不够显示每页的最大数量，那么剩下几个就显示几个
     */
    @Override
    public int getCount(){
        return lists.size() > (mIndex + 1) * mPargerSize ? mPargerSize : (lists.size() - mIndex * mPargerSize);
    }

    @Override
    public WeChatArticle getItem(int arg0){
        // TODO Auto-generated method stub
        return lists.get(arg0 + mIndex * mPargerSize);
    }

    @Override
    public long getItemId(int arg0){
        return arg0 + mIndex * mPargerSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_home_wechat_authors, null);
            holder.ima_bac = (CircleImageView) convertView.findViewById(R.id.ima_bac);
            holder.tv_home_author_icon = (TextView) convertView.findViewById(R.id.tv_home_author_icon);
            holder.tv_home_author_name = (TextView) convertView.findViewById(R.id.tv_home_author_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //重新确定position因为拿到的总是数据源，数据源是分页加载到每页的GridView上的
        final int pos = position + mIndex * mPargerSize;//假设mPageSiez
        //假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
        holder.ima_bac.setColorFilter(colors[pos % 10]);
        holder.tv_home_author_icon.setText(lists.get(pos).getName().substring(0, 1));
        holder.tv_home_author_name.setText(lists.get(pos).getName() + "");
        //添加item监听
        //        convertView.setOnClickListener(new View.OnClickListener() {
        //
        //            @Override
        //            public void onClick(View arg0) {
        //                Toast.makeText(context, "您点击了"  + lists.get(pos).getName(), Toast.LENGTH_SHORT).show();
        //            }
        //        });
        return convertView;
    }

    static class ViewHolder{
        private CircleImageView ima_bac;
        private TextView tv_home_author_icon;
        private TextView tv_home_author_name;
    }
}
