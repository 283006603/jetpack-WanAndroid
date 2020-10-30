package com.example.practice.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practice.R;
import com.example.practice.base.BaseActivity;
import com.example.practice.bean.LoginBean;
import com.example.practice.config.Constants;
import com.example.practice.utils.SharePrefUtil;
import com.example.practice.view.MainActivity;
import com.example.practice.viewmodel.MainViewModel;
import com.wljy.mvvmlibrary.annotation.Event;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.edit_login_account)
    EditText edit_login_account;

    @BindView(R.id.edit_login_pwd)
    EditText edit_login_pwd;

    @BindView(R.id.bt_register)
    Button bt_register;

    @BindView(R.id.bt_login_confirm)
    Button bt_login_confirm;
    private String str_account;
    private String str_pwd;
    private MainViewModel mainViewModel;
    public static LoginActivity loginActivity;

    @Override
    public boolean useImmersionBar(){
        return true;
    }

    @Override
    public void initViews(Bundle savedInstanceState){
        super.initViews(savedInstanceState);
        loginActivity=this;
        bt_login_confirm.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        autoLogin();
    }

    private void autoLogin(){
        boolean isLogin = SharePrefUtil.getBoolean(this, Constants.ISLOGIN, false);
        if(isLogin){
            edit_login_account.setText(SharePrefUtil.getString(this,Constants.ACCOUNT,""));
            edit_login_pwd.setText(SharePrefUtil.getString(this,Constants.PASSWORD,""));
            bt_login_confirm.performClick();
        }
    }

    @Override
    protected void initListener(){
    }

    @Override
    public int getLayoutId(){
        return R.layout.activity_login;
    }

    @Override
    public void getRemoteData(){
    }

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bt_login_confirm://登录
                str_account = edit_login_account.getText().toString().trim();
                str_pwd = edit_login_pwd.getText().toString().trim();
                mainViewModel.userLogin(str_account, str_pwd);
                break;
            case R.id.bt_register://注册
                activity(RegisterActivity.class);
                break;
        }
    }

    @Event(key = {Constants.GET_LOGIN_RESULT, Constants.REQUEST_ERROR})
    public void onEvent(String key, Object value){
        if(key.equals(Constants.GET_LOGIN_RESULT)){
            LoginBean loginBean= (LoginBean) value;
            SharePrefUtil.saveInt(this, Constants.USERID, loginBean.getId());
            SharePrefUtil.saveBoolean(this, Constants.ISLOGIN, true);
            SharePrefUtil.saveString(this, Constants.ACCOUNT, str_account);
            SharePrefUtil.saveString(this, Constants.PASSWORD, str_pwd);
            activity(MainActivity.class);
        }else if(key.equals(Constants.REQUEST_ERROR)){
            Toast.makeText(this, (String) value, Toast.LENGTH_SHORT).show();
        }
    }
}