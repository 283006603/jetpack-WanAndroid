package com.example.practice.adapter;

import android.view.ViewGroup;

import com.example.practice.bean.ProjectPageBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created By 大苏打
 * on 2020/9/23
 */
public class ProjectPageAdapter extends FragmentPagerAdapter{

    List<ProjectPageBean>list;
    public ProjectPageAdapter(FragmentManager fm,List<ProjectPageBean>mlist) {
        super(fm);
        list=mlist;
    }

    @NonNull
    @Override
    public Fragment getItem(int position){
        return list.get(position).getFragment();
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        return list.get(position).getTitle();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        super.destroyItem(container, position, object);
    }
}
