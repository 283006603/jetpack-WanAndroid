package com.example.practice.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.practice.R;
import com.example.practice.adapter.NavigationLeftAdapter;
import com.example.practice.adapter.NavigationRightAdapter;
import com.example.practice.base.BaseFragment;
import com.example.practice.bean.NavigationListBean;
import com.example.practice.config.Constants;
import com.example.practice.view.activity.WebViewActivity;
import com.example.practice.viewmodel.MainViewModel;
import com.wljy.mvvmlibrary.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class NavigationFragment extends BaseFragment{

    @BindView(R.id.left_recycleview)
    RecyclerView leftRecycleview;
    @BindView(R.id.right_recycleview)
    RecyclerView rightRecycleview;
    private MainViewModel viewModel;
    List<NavigationListBean> list = new ArrayList<>();
    NavigationLeftAdapter leftAdapter;
    NavigationRightAdapter rightAdapter;
    private int select;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void initViewModel(){
        viewModel = registerViewModel(MainViewModel.class);
    }

    @Override
    public int getLayoutResId(){
        return R.layout.fragment_navigation;
    }

    @Override
    public void initView(View rootView){
        super.initView(rootView);
        leftRecycleview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        leftAdapter = new NavigationLeftAdapter(R.layout.item_system_left, list);
        leftRecycleview.setAdapter(leftAdapter);
        leftAdapter.setSelect(select);
        //--
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rightRecycleview.setLayoutManager(linearLayoutManager);
        rightAdapter = new NavigationRightAdapter(R.layout.item_navigation_right, list);
        rightRecycleview.setAdapter(rightAdapter);
        initListener();
    }

    private void initListener(){
        leftAdapter.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position){
                Log.d("NavigationFragment", "position:" + position);
                linearLayoutManager.scrollToPositionWithOffset(position,0);
                if(select == position)
                    return;
                leftAdapter.setSelect(position);
                leftAdapter.notifyDataSetChanged();
                select = position;
                //--------

            }
        });

        rightAdapter.setOnTagSelectListener(new NavigationRightAdapter.TagSelectListener(){
            @Override
            public void onTagSelect(int position,int tagPosition){
                goWebActivity(list.get(position).getArticles().get(tagPosition));
            }
        });





    }

    private void goWebActivity(NavigationListBean.ArticlesBean mainArticleBean){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITILE, mainArticleBean.getTitle());
        bundle.putString(Constants.AUTHOR, mainArticleBean.getAuthor());
        bundle.putInt(Constants.ID, mainArticleBean.getId());
        bundle.putString(Constants.URL, mainArticleBean.getLink());
        activity(WebViewActivity.class, bundle);
    }

    @Override
    public void getRemoteData(){
        viewModel.getNavigation();
    }

    @Event(key = {Constants.REQUEST_ERROR, Constants.GET_NAVIGATION_LIST})
    public void event(String key, Object value){
        if(key.equals(Constants.GET_NAVIGATION_LIST)){
            if(value != null){
                list.addAll((List<NavigationListBean>) value);
                leftAdapter.notifyDataSetChanged();
                rightAdapter.notifyDataSetChanged();
            }
        }else if(key.equals(Constants.REQUEST_ERROR)){
        }
    }
}