package com.example.practice.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.practice.R
import com.example.practice.base.BaseActivity
import com.example.practice.bean.LoginBean
import com.example.practice.config.Constants
import com.example.practice.databinding.ActivityRegisterBinding
import com.example.practice.utils.SharePrefUtil
import com.example.practice.view.MainActivity
import com.example.practice.viewmodel.MainViewModel
import com.wljy.mvvmlibrary.annotation.Event

class RegisterActivity : BaseActivity<ActivityRegisterBinding?>(), View.OnClickListener {
    private var editRegisterAccount: EditText? = null
    private var editRegisterPwd: EditText? = null
    private var editRegisterPwdAgain: EditText? = null
    private var btConfirm: Button? = null
    private var account: String? = null
    private var pwd: String? = null
    private var pwd_again: String? = null
    private var mainViewModel: MainViewModel? = null
    override fun useImmersionBar(): Boolean {
        return true
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        editRegisterAccount = binding?.editRegisterAccount
        editRegisterPwd = binding?.editRegisterPwd
        editRegisterPwdAgain = binding?.editRegisterPwdAgain
        btConfirm = binding?.btConfirm
        btConfirm?.setOnClickListener(this)
    }

    override fun initListener() {}
    override fun getRemoteData() {}
    override fun initViewModel() {
        mainViewModel = registerViewModel(MainViewModel::class.java)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_confirm -> {
                account = editRegisterAccount?.text.toString().trim()
                pwd = editRegisterPwd?.text.toString().trim()
                pwd_again = editRegisterPwdAgain?.text.toString().trim()
                mainViewModel?.registerAccount(account, pwd, pwd_again)
            }
        }
    }

    @Event(key = [Constants.GET_REGISTER_RESULT, Constants.REQUEST_ERROR])
    fun onEvent(key: String, value: Any) {
        if (key == Constants.GET_REGISTER_RESULT) {
            /*LoginActivity.loginActivity.finish();*/
            val loginBean = value as LoginBean
            SharePrefUtil.saveInt(this, Constants.USERID, loginBean.id)
            SharePrefUtil.saveBoolean(this, Constants.ISLOGIN, true)
            SharePrefUtil.saveString(this, Constants.ACCOUNT, account)
            SharePrefUtil.saveString(this, Constants.PASSWORD, pwd)
            activity(MainActivity::class.java)
            finish()
        } else if (key == Constants.REQUEST_ERROR) {
            Toast.makeText(this, value as String, Toast.LENGTH_SHORT).show()
        }
    }
}