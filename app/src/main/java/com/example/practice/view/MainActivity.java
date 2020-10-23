package com.example.practice.view;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.practice.R;
import com.example.practice.base.BaseActivity;
import com.example.practice.view.fragment.MainFragment;
import com.example.practice.view.fragment.MineFragment;
import com.example.practice.view.fragment.ProjectFragment;
import com.example.practice.view.fragment.SystemNavigationFragment;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

public class MainActivity extends BaseActivity{

    @BindView(R.id.main_vb1)
    RadioButton mainVb1;
    @BindView(R.id.main_vb2)
    RadioButton mainVb2;
    @BindView(R.id.main_vb3)
    RadioButton mainVb3;
    @BindView(R.id.main_vb4)
    RadioButton mainVb4;
    @BindView(R.id.main_vg)
    RadioGroup mainVg;
    @BindView(R.id.fl_main_container)
    FrameLayout mainFramelayout;


    List<Fragment> fragments = new ArrayList<>();
    private FragmentManager fragmentManager;
    private int currentSelectedId = R.id.main_vb1;
    private long lastBackTime = 0;

    @Override
    protected void initListener(){
        mainVg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                if(checkedId == currentSelectedId){
                    return;
                }
                currentSelectedId = checkedId;
                if(checkedId == R.id.main_vb1){
                    selectFragment(0);
                }else if(checkedId == R.id.main_vb2){
                    selectFragment(1);
                }else if(checkedId == R.id.main_vb3){
                    selectFragment(2);
                }else if(checkedId == R.id.main_vb4){
                    selectFragment(3);
                }
            }
        });
    }

    @Override
    public void initViews(Bundle savedInstanceState){
        super.initViews(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        createFragment();
        selectFragment(0);
        mainVb1.setChecked(true);
    }

    private void selectFragment(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if (i == index) {
                ft.show(fragments.get(i));
            } else {
                ft.hide(fragments.get(i));
            }
        }
        ft.commit();
    }

    private void createFragment() {
        fragments = new ArrayList<>();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        MainFragment homeFragment = new MainFragment();
        ft.add(R.id.fl_main_container, homeFragment);
        fragments.add(homeFragment);

        ProjectFragment projectFragment = new ProjectFragment();
        ft.add(R.id.fl_main_container, projectFragment);
        fragments.add(projectFragment);

        SystemNavigationFragment systemFragment = new SystemNavigationFragment();
        ft.add(R.id.fl_main_container, systemFragment);
        fragments.add(systemFragment);

        MineFragment mineFragment = new MineFragment();
        ft.add(R.id.fl_main_container, mineFragment);
        fragments.add(mineFragment);

        // 提交事务
        ft.commit();
    }

    @Override
    public int getLayoutId(){
        return R.layout.activity_main;
    }

    @Override
    public void getRemoteData(){
    }

    @Override
    public void initViewModel(){
    }


    @Override
    public void onBackPressed(){
        long currentBackTime = System.currentTimeMillis();
        if(currentBackTime - lastBackTime > 2000){
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            lastBackTime = currentBackTime;
        }else{
            super.onBackPressed();
            System.exit(0);
        }
    }

    @Override
    public boolean useImmersionBar(){
        return true;
    }

    @Override
    public void initStatusBar() {
        if (useImmersionBar()) {
            ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.color_white).statusBarDarkFont(true).init();
        }
    }
}