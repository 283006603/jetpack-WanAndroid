package com.example.practice.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.practice.R;
import com.example.practice.adapter.MainArticleAdapter;
import com.example.practice.base.BaseActivity;
import com.example.practice.bean.MainArticleBean;
import com.example.practice.config.Constants;
import com.example.practice.viewmodel.MainViewModel;
import com.gyf.immersionbar.ImmersionBar;
import com.wljy.mvvmlibrary.annotation.Event;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SearchActivity extends BaseActivity{

    @BindView(R.id.search_cancel)
    TextView searchCancel;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    private MainViewModel mainViewModel;
    private int page = 0;//搜索作者文章page从0开始
    private String author;
    private List<MainArticleBean>list;
    private MainArticleAdapter adapter;

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
        recycleview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter = new MainArticleAdapter(R.layout.item_main_article,list);
        recycleview.setAdapter(adapter);
    }



    @Override
    protected void initListener(){
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘
                    author=editSearch.getText().toString().trim();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public int getLayoutId(){
        return R.layout.activity_search;
    }

    @Override
    public void getRemoteData(){
        mainViewModel.getAuthorArticle(page, author);
    }

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Event(key = {Constants.GET_MAIN_ARTICLE_REFRESH, Constants.GET_MAIN_ARTICLE_LOADMORE, Constants.REQUEST_ERROR})
    public void getAuthorArticle(String key, Object value){
        if(key.equals(Constants.GET_MAIN_ARTICLE_REFRESH)){
        }else if(key.equals(Constants.GET_MAIN_ARTICLE_LOADMORE)){
        }else{
        }
    }
}