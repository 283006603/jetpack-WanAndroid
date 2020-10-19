package com.example.practice.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.practice.R;
import com.example.practice.adapter.MainArticleAdapter;
import com.example.practice.base.BaseActivity;
import com.example.practice.bean.HotKeyBean;
import com.example.practice.config.Constants;
import com.example.practice.viewmodel.MainViewModel;
import com.gyf.immersionbar.ImmersionBar;
import com.wljy.mvvmlibrary.annotation.Event;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity{

    @BindView(R.id.search_cancel)
    TextView searchCancel;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.tag_flow)
    TagFlowLayout tagFlow;

    public String hotBeanContent;
    public int hotBeanId;

    private MainViewModel mainViewModel;
    //    private int page = 0;//搜索作者文章page从0开始
    //    private String author;
    private List<HotKeyBean> hotBeanList;
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
    }

    @Override
    protected void initListener(){
        tagFlow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener(){
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent){
                hotBeanContent= hotBeanList.get(position).getName();
                Log.d("SearchActivity", hotBeanContent);
                hotBeanId=hotBeanList.get(position).getId();
                Log.d("SearchActivity", "hotBeanId:" + hotBeanId);
                return true;
            }
        });

        /*tagFlow.setOnSelectListener(new TagFlowLayout.OnSelectListener(){
            @Override
            public void onSelected(Set<Integer> selectPosSet){
                Log.d("SearchActivity", "selectPosSet:" + selectPosSet);
            }
        });*/
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘
//                    author=editSearch.getText().toString().trim();
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
        mainViewModel.getHotKey();
    }

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Event(key = {Constants.GET_HOT_KEY_LIST, Constants.REQUEST_ERROR})
    public void getEvent(String key, Object value){
        if(key.equals(Constants.GET_HOT_KEY_LIST)){
            hotBeanList = (List) value;
            if(value != null){
                tagFlow.setAdapter(new TagAdapter<HotKeyBean>(hotBeanList){
                    @Override
                    public View getView(FlowLayout parent, int position, HotKeyBean hotKeyBean){
                        TextView textView = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_tag_flow, tagFlow, false);
                        textView.setText(hotBeanList.get(position).getName());
                        return textView;
                    }
                });
            }
        }else{
            Log.d("SearchActivity", "错误");
        }
    }


}