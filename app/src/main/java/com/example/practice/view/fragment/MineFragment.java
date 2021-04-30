package com.example.practice.view.fragment;

import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.practice.R;
import com.example.practice.adapter.MineAdapter;
import com.example.practice.base.BaseFragment;
import com.example.practice.utils.CacheToolsUtil;
import com.example.practice.utils.FullPopupwindow;
import com.example.practice.view.activity.MeiZiActivity;
import com.example.practice.widge.QQHeaderSrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment{
    @BindView(R.id.sv_scroll)
    QQHeaderSrollView qqHeaderSrollView;

    FullPopupwindow popupWindow;

    private ImageView iv;
    private View header;

    List<Map<String, Object>> list = new ArrayList<>();
    private MineAdapter mineAdapter;

    @Override
    public void initViewModel(){
    }

    @Override
    public int getLayoutResId(){
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View rootView){
        super.initView(rootView);
        initData();
        mineAdapter = new MineAdapter(list, getActivity(),R.layout.item_mine);
        //获取到头部的View
        header = View.inflate(getActivity(), R.layout.listview_header, null);
        //获取到头部的View的图片控件
        iv = (ImageView) header.findViewById(R.id.layout_header_image);
        //将imageView传入到ListView中
        qqHeaderSrollView.setZoomImageView(iv);
        //将头部的View设置给ListView的HeaderView
        qqHeaderSrollView.addHeaderView(header);
        //设置适配器
        qqHeaderSrollView.setAdapter(mineAdapter);
        initListener();
    }

    private void initData(){
        Map<String, Object> map1 = new HashMap<>();
        map1.put("title", "搜藏");
        map1.put("image", R.drawable.ic_mine_favorite);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("title", "开车");
        map2.put("image", R.drawable.ic_mine_happy);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("title", "设置");
        map3.put("image", R.drawable.ic_mine_setting);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("title", "关于");
        map4.put("image", R.drawable.ic_mine_about);
        Map<String, Object> map5 = new HashMap<>();
        map5.put("title", "清除缓存");
        map5.put("image", R.drawable.ic_mine_favorite);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
    }

    private void initListener(){
        qqHeaderSrollView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.d("MineFragment", "position:" + position);
                switch(position){
                    case 1://搜藏
                    case 3://设置
                        Toast.makeText(mActivity, R.string.str_function_not_open, Toast.LENGTH_SHORT).show();
                        break;
                    case 2://老车
                        activity(MeiZiActivity.class);
                        break;
                    case 4://关于
                        showAboutPop();
                        break;
                    case 5://清楚缓存
                        try{
                            showCachelDialog();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void getRemoteData(){
    }

    private void showAboutPop(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_about_mine, null, false);
        if(popupWindow == null){
            popupWindow = new FullPopupwindow(getContext());
            popupWindow.setContentView(view);
        }
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.START, 0, 0);
    }

    private void showCachelDialog() throws Exception{
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getContext());
        normalDialog.setTitle("清除缓存");
        normalDialog.setMessage("清除全部缓存:" + CacheToolsUtil.getTotalCacheSize(getContext()));
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                CacheToolsUtil.clearAllCache(getContext());
            }
        });
        normalDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
            }
        });
        normalDialog.show();
    }
}