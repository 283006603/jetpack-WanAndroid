package com.example.practice.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.practice.R;
import com.example.practice.adapter.MainArticleAdapter;
import com.example.practice.base.BaseActivity;
import com.example.practice.bean.HotKeyBean;
import com.example.practice.bean.HotKeyHistoryBean;
import com.example.practice.bean.MainArticleBean;
import com.example.practice.bean.PageList;
import com.example.practice.config.Constants;
import com.example.practice.database.AppDataBase;
import com.example.practice.database.dao.HotKeyHistoryDao;
import com.example.practice.viewmodel.MainViewModel;
import com.example.practice.widge.DampScrollView;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.wljy.mvvmlibrary.annotation.Event;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity{

    @BindView(R.id.search_cancel)
    TextView searchCancel;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.tag_flow_hot)
    TagFlowLayout tagFlowHot;

    public String k;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.scrollView)
    DampScrollView scrollView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tag_flow_history)
    TagFlowLayout tagFlowHistory;

    private MainViewModel mainViewModel;
    //    private int page = 0;//搜索作者文章page从0开始
    //    private String author;
    private List<HotKeyBean> hotBeanList;
    private MainArticleAdapter adapter;
    private static InputMethodManager imm;
    public List<MainArticleBean> mainArticleBeanList = new ArrayList<>();
    int page = 0;
    private PageList<MainArticleBean> pageList;
    private HotKeyHistoryDao hotKeyHistoryDao;
    private List<HotKeyHistoryBean> hotKeyHistoryBeans;

    @Override
    public boolean useImmersionBar(){
        return true;
    }

    @Override
    public void initStatusBar(){
        if(useImmersionBar()){
            ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.color_white).statusBarDarkFont(true).init();
        }
    }

    @Override
    public void initViews(Bundle savedInstanceState){
        super.initViews(savedInstanceState);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        showSoftInputFromWindow(SearchActivity.this, editSearch);
        recycleview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new MainArticleAdapter(R.layout.item_main_article, mainArticleBeanList);
        recycleview.setAdapter(adapter);
        hotKeyHistoryDao = AppDataBase.getInstance().getHotKeyHistoryDao();
        refreshDataBase();
    }

    @Override
    protected void initListener(){
        tagFlowHot.setOnTagClickListener(new TagFlowLayout.OnTagClickListener(){
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent){
                k = hotBeanList.get(position).getName();
                editSearch.setText(k + "");
                return true;
            }
        });
        tagFlowHistory.setOnTagClickListener(new TagFlowLayout.OnTagClickListener(){
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent){
                k = hotKeyHistoryBeans.get(position).hot_name_history;
                editSearch.setText(k + "");
                return true;
            }
        });
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(v.getText().toString().trim())){
                    hideSoftInputFromWindow(SearchActivity.this, editSearch);
                    k = editSearch.getText().toString().trim();
                    page = 0;
                    inSertDataBase();//点击软键盘的搜索按钮，保存到数据库历史数据
                    showRecycleResult(page, k);
                    return true;
                }
                return false;
            }
        });
        editSearch.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                showSoftInputFromWindow(SearchActivity.this, editSearch);
                return false;
            }
        });
        editSearch.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    Log.d("SearchActivity", "有焦点");
                    showSoftInputFromWindow(SearchActivity.this, editSearch);
                }else{
                    Log.d("SearchActivity", "失去焦点");
                    hideSoftInputFromWindow(SearchActivity.this, editSearch);
                }
            }
        });
        //右侧取消键
        searchCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showSoftInputFromWindow(SearchActivity.this, editSearch);
                showHotResult();
                refreshDataBase();
            }
        });
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener(){
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout){
                page = 0;
                showRecycleResult(page, k);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener(){
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout){
                showRecycleResult(++page, k);
            }
        });
        //RecycleView点击事件
        adapter.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position){
                goWebActivity(mainArticleBeanList.get(position));
            }
        });
    }

    private void inSertDataBase(){
        hotKeyHistoryBeans = hotKeyHistoryDao.queryList();
        HotKeyHistoryBean hotKeyHistoryBean = new HotKeyHistoryBean();
        hotKeyHistoryBean.hot_name_history = k;
        //去重，若数据库内有搜索记录这个记录变成首位
        for(int i = 0; i < hotKeyHistoryBeans.size(); i++){
            if(hotKeyHistoryBeans.get(i).hot_name_history.equals(k)){
                hotKeyHistoryBeans.remove(hotKeyHistoryBeans.get(i));
                break;
            }
        }
        hotKeyHistoryBeans.add(hotKeyHistoryBean);
        if(hotKeyHistoryBeans.size() > 10){
            hotKeyHistoryBeans.remove(0);
        }
        //清表了再重新插入新得list
        hotKeyHistoryDao.deleteData();
        hotKeyHistoryDao.insertItems(hotKeyHistoryBeans);
    }

    private void refreshDataBase(){
        hotKeyHistoryBeans = hotKeyHistoryDao.queryList();
        Collections.reverse(hotKeyHistoryBeans);
        if(hotKeyHistoryBeans != null || hotKeyHistoryBeans.size() != 0){
            tagFlowHistory.setAdapter(new TagAdapter<HotKeyHistoryBean>(hotKeyHistoryBeans){
                @Override
                public View getView(FlowLayout parent, int position, HotKeyHistoryBean hotKeyHistoryBean){
                    TextView textView = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_tag_flow, tagFlowHistory, false);
                    textView.setText(hotKeyHistoryBean.hot_name_history);
                    return textView;
                }
            });
        }
    }

    @Override
    public int getLayoutId(){
        return R.layout.activity_search;
    }

    @Override
    public void getRemoteData(){
        mainViewModel.getHotKey();
    }

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Event(key = {Constants.GET_HOT_KEY_LIST, Constants.GET_MAIN_ARTICLE_REFRESH, Constants.GET_MAIN_ARTICLE_LOADMORE, Constants.REQUEST_ERROR})
    public void getEvent(String key, Object value){
        if(key.equals(Constants.GET_HOT_KEY_LIST)){
            hotBeanList = (List) value;
            if(value != null){
                tagFlowHot.setAdapter(new TagAdapter<HotKeyBean>(hotBeanList){
                    @Override
                    public View getView(FlowLayout parent, int position, HotKeyBean hotKeyBean){
                        TextView textView = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_tag_flow, tagFlowHot, false);
                        textView.setText(hotBeanList.get(position).getName());
                        return textView;
                    }
                });
            }
        }else if(key.equals(Constants.GET_MAIN_ARTICLE_REFRESH)){
            pageList = (PageList<MainArticleBean>) value;
            if(pageList.getDatas() != null && pageList.getDatas().size() != 0){
                mainArticleBeanList.clear();
                mainArticleBeanList.addAll(pageList.getDatas());
                adapter.notifyDataSetChanged();
                //判断是不是最后一页,如果是，关闭上滑加载,连底部没有数据都不提示
                if(page == pageList.getPageCount() - 1){
                    refreshLayout.setEnableLoadMore(false);
                }else{
                    refreshLayout.setEnableLoadMore(true);
                }
            }else{
                //第一页都没有数据,显示空视图
            }
            refreshLayout.finishRefresh();
        }else if(key.equals(Constants.GET_MAIN_ARTICLE_LOADMORE)){
            pageList = (PageList<MainArticleBean>) value;
            if(pageList == null || pageList.getDatas().size() == 0){
                //加载页面没有数据，显示空视图
            }else{
                if(pageList.getDatas() != null){
                    mainArticleBeanList.addAll(pageList.getDatas());
                    adapter.notifyDataSetChanged();
                }
                //判断是否到了底页,得提示,告知用户
                if(page == pageList.getPageCount() - 1){
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }else{
                    refreshLayout.finishLoadMore();
                }
            }
        }else{
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            //显示网络错误视图
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText){
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.findFocus();
        //显示软键盘
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public static void hideSoftInputFromWindow(Activity activity, EditText editText){
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.requestFocus();
        editText.findFocus();
        //隐藏软键盘
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showRecycleResult(int page, String k){
        scrollView.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
        searchCancel.setVisibility(View.VISIBLE);
        mainViewModel.getSearch(page, k);
    }

    public void showHotResult(){
        page = 0;
        mainArticleBeanList.clear();
        scrollView.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
        searchCancel.setVisibility(View.GONE);
    }

    private void goWebActivity(MainArticleBean mainArticleBean){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITILE, mainArticleBean.getTitle());
        bundle.putString(Constants.AUTHOR, mainArticleBean.getAuthor());
        bundle.putInt(Constants.ID, mainArticleBean.getId());
        bundle.putString(Constants.URL, mainArticleBean.getLink());
        activity(WebViewActivity.class, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}