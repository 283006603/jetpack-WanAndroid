package com.example.practice.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.practice.adapter.SystemNavigationAdapter;
import com.example.practice.base.BaseFragment;
import com.example.practice.databinding.FragmentSystemNavigationBinding;
import com.example.practice.view.activity.SearchActivity;
import com.example.practice.viewmodel.MainViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SystemNavigationFragment#} factory method to
 * create an instance of this fragment.仿拼多多或扑扑的分类栏
 */
public class SystemNavigationFragment extends BaseFragment<FragmentSystemNavigationBinding>{

    RelativeLayout relaSearch;
    TabLayout tableLayout;
    ViewPager viewPager;
    private MainViewModel mainViewModel;
    private List<Fragment> list;
    private String[] title = {"体系", "导航"};

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Override
    public void initView(Bundle bundle){
        super.initView(bundle);
        relaSearch = binding.itemsearch.relaSearch;
        tableLayout = binding.tableLayout;
        viewPager = binding.viewPager;
        initFragment();
    }

    private void initFragment(){
        list = new ArrayList<>();
        list.add(new SystemFragment());
        list.add(new NavigationFragment());
        SystemNavigationAdapter adapter = new SystemNavigationAdapter(getChildFragmentManager(), list, title);
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initListener(){
        relaSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("SystemFragment", "aaa");
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putString("str", "按作者名称搜索文章");
                activity(SearchActivity.class, bundle);
            }
        });
    }

    @Override
    public void getRemoteData(){
    }
}