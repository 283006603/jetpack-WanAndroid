package com.wljy.mvvmlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.trello.rxlifecycle4.components.support.RxFragment;
import com.wljy.mvvmlibrary.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * @author lzh
 */
public abstract class AbsLifecycleFragment extends RxFragment {


    public AbsLifecycleFragment() {

    }

    public void initView(Bundle state){
        initViewModel();
    }

    public abstract void initViewModel();

    public  <T extends AbsViewModel> T registerViewModel(Class<T> mViewModel) {
        T absViewModel = new ViewModelProvider(this).get(mViewModel);
        absViewModel.registerSubscriber(this);
        return absViewModel;
    }

    private View rootView;
    private Context mContext;
    private boolean isFirstLoad = true; // 是否第一次加载
    public FragmentActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutResId(), null, false);
        initButterKnife(rootView);
        View contentLayout = rootView.findViewById(getContentResId());
        initView(savedInstanceState);
        return rootView;
    }
    private void initButterKnife(View view) {
        ButterKnife.bind(this, view);
    }

    public abstract int getLayoutResId();



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            getRemoteData();
            isFirstLoad = false;
        }
    }


    public abstract void getRemoteData();


    //TODO loading显示的位置
    protected int getContentResId() {
        return -1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mActivity = null;
        this.mContext = null;
    }

    //跳转activity
    public void activity(Class<? extends Activity> cls) {
        Intent intent = new Intent(mContext, cls);
        mActivity.overridePendingTransition(R.anim.in_translate_right, R.anim.out_translate_right);
        mActivity.startActivity(intent);
    }

    //跳转activity
    public void activity(Class<? extends Activity> cls,Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(bundle);
        mActivity.overridePendingTransition(R.anim.in_translate_right, R.anim.out_translate_right);
        mActivity.startActivity(intent);
    }
}
