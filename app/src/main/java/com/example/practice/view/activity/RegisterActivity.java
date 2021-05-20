package com.example.practice.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practice.R;
import com.example.practice.base.BaseActivity;
import com.example.practice.bean.LoginBean;
import com.example.practice.config.Constants;
import com.example.practice.databinding.ActivityRegisterBinding;
import com.example.practice.utils.SharePrefUtil;
import com.example.practice.view.MainActivity;
import com.example.practice.viewmodel.MainViewModel;
import com.wljy.mvvmlibrary.annotation.Event;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> implements View.OnClickListener{

    EditText edit_register_account;
    EditText edit_register_pwd;
    EditText edit_register_pwd_again;
    Button bt_confirm;

    String account, pwd, pwd_again;
    private MainViewModel mainViewModel;

    @Override
    public boolean useImmersionBar(){
        return true;
    }

    @Override
    public void initViews(Bundle savedInstanceState){
        super.initViews(savedInstanceState);
         edit_register_account=binding.editRegisterAccount;
         edit_register_pwd=binding.editRegisterPwd;
         edit_register_pwd_again=binding.editRegisterPwdAgain;
         bt_confirm=binding.btConfirm;

        bt_confirm.setOnClickListener(this);
    }

    @Override
    protected void initListener(){
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
            case R.id.bt_confirm://注册
                account = edit_register_account.getText().toString().trim();
                pwd = edit_register_pwd.getText().toString().trim();
                pwd_again = edit_register_pwd_again.getText().toString().trim();
                mainViewModel.registerAccount(account, pwd, pwd_again);
                break;
        }
    }

    @Event(key = {Constants.GET_REGISTER_RESULT, Constants.REQUEST_ERROR})
    public void onEvent(String key, Object value){
        if(key.equals(Constants.GET_REGISTER_RESULT)){
            Log.d("TAG", "bbb");
            LoginActivity.loginActivity.finish();
            LoginBean loginBean = (LoginBean) value;
            SharePrefUtil.saveInt(this, Constants.USERID, loginBean.getId());
            SharePrefUtil.saveBoolean(this, Constants.ISLOGIN, true);
            SharePrefUtil.saveString(this, Constants.ACCOUNT, account);
            SharePrefUtil.saveString(this, Constants.PASSWORD, pwd);
            activity(MainActivity.class);
            finish();
        }else if(key.equals(Constants.REQUEST_ERROR)){
            Toast.makeText(this, (String) value, Toast.LENGTH_SHORT).show();
        }
    }
}