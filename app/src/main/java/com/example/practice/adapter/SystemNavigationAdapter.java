package com.example.practice.adapter;

import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created By 大苏打
 * on 2020/10/23
 */
public class SystemNavigationAdapter extends FragmentPagerAdapter{
    private List<Fragment>list;
    private String []title;

    public SystemNavigationAdapter(@NonNull FragmentManager fm, List<Fragment> list, String[] title){
        super(fm);
        this.list = list;
        this.title = title;
    }

    @NonNull
    @Override
    public Fragment getItem(int position){
        return list.get(position);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        return title[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        super.destroyItem(container, position, object);
    }
}
