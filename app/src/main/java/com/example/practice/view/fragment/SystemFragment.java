package com.example.practice.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.practice.R;
import com.example.practice.adapter.SystemLeftAdapter;
import com.example.practice.adapter.SystemRightAdapter;
import com.example.practice.base.BaseFragment;
import com.example.practice.bean.SystemListBean;
import com.example.practice.config.Constants;
import com.example.practice.view.activity.SystemDetailActivity;
import com.example.practice.viewmodel.MainViewModel;
import com.wljy.mvvmlibrary.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SystemFragment#} factory method to
 * create an instance of this fragment.仿拼多多或扑扑的分类栏
 */
public class SystemFragment extends BaseFragment{

    @BindView(R.id.left_recycleview)
    RecyclerView leftRecycleview;
    @BindView(R.id.right_recycleview)
    RecyclerView rightRecycleview;

    private MainViewModel mainViewModel;
    private List<SystemListBean> leftList = new ArrayList<>();
    private List<SystemListBean.ChildrenBean> rightList = new ArrayList<>();
    private SystemLeftAdapter leftAdapter;
    private SystemRightAdapter rightAdapter;
    private int select;
    private List<SystemListBean.ChildrenBean> children;

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Override
    public int getLayoutResId(){
        return R.layout.fragment_system;
    }

    @Override
    public void initView(Bundle state){
        super.initView(state);
        leftRecycleview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        leftAdapter = new SystemLeftAdapter(R.layout.item_system_left, leftList);
        //        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_divider_space, null));
        //        leftRecycleview.addItemDecoration(itemDecoration);
        leftRecycleview.setAdapter(leftAdapter);
        leftAdapter.setSelect(select);
        //===右边
        rightRecycleview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rightAdapter = new SystemRightAdapter(R.layout.item_system_right, rightList);
        rightRecycleview.setAdapter(rightAdapter);
        initListener();
    }

    private void initListener(){
        leftAdapter.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position){
                if(select == position)
                    return;
                leftAdapter.setSelect(position);
                rightList.clear();
                children = leftList.get(position).getChildren();
                rightList.addAll(children);
                leftAdapter.notifyDataSetChanged();
                rightAdapter.notifyDataSetChanged();
                select = position;
            }
        });
        rightAdapter.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position){
                goSystemDetailActivity(rightList.get(position));
            }
        });


    }

    private void goSystemDetailActivity(SystemListBean.ChildrenBean childrenBean){
        Bundle bundle = new Bundle();
        bundle.putSerializable("childrenBean", childrenBean);
        activity(SystemDetailActivity.class, bundle);
    }

    @Override
    public void getRemoteData(){
        mainViewModel.getSystemList();
    }

    @Event(key = {Constants.REQUEST_ERROR, Constants.GET_SYSTEM_LIST})
    public void event(String key, Object value){
        if(key.equals(Constants.GET_SYSTEM_LIST)){
            if(value != null){
                leftList.addAll((List<SystemListBean>) value);
                leftAdapter.notifyDataSetChanged();
                //初始化右边的recycleview
                rightList.addAll(leftList.get(0).getChildren());
                rightAdapter.notifyDataSetChanged();
            }
        }else if(key.equals(Constants.REQUEST_ERROR)){
        }
    }
}