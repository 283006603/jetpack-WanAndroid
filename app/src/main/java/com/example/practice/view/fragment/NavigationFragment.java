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

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING;

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
    private int leftSelect, rightVisible;
    private LinearLayoutManager rightLinearLayoutManager, leftLinearLayoutManager;
    public String TAG = "NavigationFragment";
    private int mFirstVisiblePosition = 0;
    private int mLastVisiblePosition;

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
        leftLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        leftRecycleview.setLayoutManager(leftLinearLayoutManager);
        leftAdapter = new NavigationLeftAdapter(R.layout.item_system_left, list);
        leftRecycleview.setAdapter(leftAdapter);
        leftAdapter.setSelect(leftSelect);
        //--
        rightLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rightRecycleview.setLayoutManager(rightLinearLayoutManager);
        rightAdapter = new NavigationRightAdapter(R.layout.item_navigation_right, list);
        rightRecycleview.setAdapter(rightAdapter);
        initListener();
    }

    private void initListener(){
        leftAdapter.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position){
                Log.d("NavigationFragment", "position:" + position);
                rightLinearLayoutManager.scrollToPositionWithOffset(position, 0);
                if(leftSelect == position)
                    return;
                leftAdapter.setSelect(position);
                leftAdapter.notifyDataSetChanged();
                leftSelect = position;
                //--------
            }
        });
        rightAdapter.setOnTagSelectListener(new NavigationRightAdapter.TagSelectListener(){
            @Override
            public void onTagSelect(int position, int tagPosition){
                goWebActivity(list.get(position).getArticles().get(tagPosition));
            }
        });
        rightRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_DRAGGING){
                    // DES: 找出当前可视Item位置
                    RecyclerView.LayoutManager layoutManager = rightRecycleview.getLayoutManager();
                    if(layoutManager instanceof LinearLayoutManager){
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        mFirstVisiblePosition = linearManager.findFirstVisibleItemPosition();
                        mLastVisiblePosition = linearManager.findLastVisibleItemPosition();
                    }
                    if(mFirstVisiblePosition == rightVisible){
                        return;
                    }else{
                        leftLinearLayoutManager.scrollToPositionWithOffset(mFirstVisiblePosition, 0);
                        leftAdapter.setSelect(mFirstVisiblePosition);
                        leftAdapter.notifyDataSetChanged();
                        leftSelect = mFirstVisiblePosition;
                        rightVisible = mFirstVisiblePosition;
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
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