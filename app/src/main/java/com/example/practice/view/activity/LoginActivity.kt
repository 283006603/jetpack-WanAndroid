package com.example.practice.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.practice.base.BaseActivity
import com.example.practice.bean.LoginBean
import com.example.practice.config.Constants
import com.example.practice.databinding.ActivityLoginBinding
import com.example.practice.utils.SharePrefUtil
import com.example.practice.view.MainActivity
import com.example.practice.viewmodel.MainViewModel
import com.wljy.mvvmlibrary.annotation.Event

/**
 *Created By 大苏打
 * on 2021/7/14
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>(), View.OnClickListener {

    var editLoginAccount: EditText? = null
    var editLoginPwd: EditText? = null
    private var btRegister: Button? = null
    var btLoginConfirm: Button? = null
    var tvSkip:TextView?=null

    var str_account: String? = null
    var str_pwd: String? = null

    var mainViewModel: MainViewModel? = null


    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        editLoginAccount = binding.editLoginAccount
        editLoginPwd = binding.editLoginPwd
        btLoginConfirm = binding.btLoginConfirm
        btRegister = binding.btRegister
        tvSkip=binding.tvSkip
        autoLogin()
    }

    private fun autoLogin() {
        btRegister?.setOnClickListener(this)
        btLoginConfirm?.setOnClickListener(this)

        var isLogin = SharePrefUtil.getBoolean(this, Constants.ISLOGIN, false)
        if (isLogin) {
            editLoginAccount?.setText(SharePrefUtil.getString(this, Constants.ACCOUNT, ""))
            editLoginPwd?.setText(SharePrefUtil.getString(this, Constants.PASSWORD, ""))
            btLoginConfirm?.performClick()//注意顺序，一定要先setListener
        }
    }

    override fun initListener() {
        tvSkip?.setOnClickListener(this)
    }


    override fun getRemoteData() {
    }

    override fun initViewModel() {
        mainViewModel = registerViewModel(MainViewModel::class.java)
    }

    override fun useImmersionBar(): Boolean {
        return true
    }

    override fun onClick(v: View?) {
        when (v) {
            btLoginConfirm -> {
                str_account = editLoginAccount?.text.toString().trim()
                str_pwd = editLoginPwd?.text.toString().trim()
                mainViewModel?.userLogin(str_account, str_pwd)
            }

            btRegister -> activity(RegisterActivity::class.java)

            tvSkip -> {
                activity(MainActivity::class.java)
                finish()
            }

        }
    }

    @Event(key = [Constants.GET_LOGIN_RESULT, Constants.REQUEST_ERROR])
    fun onEvent(key: String, `object`: Any) {
        if (key == Constants.GET_LOGIN_RESULT) {
            val loginBean = `object` as LoginBean
            SharePrefUtil.saveInt(this, Constants.USERID, loginBean.id)
            SharePrefUtil.saveBoolean(this, Constants.ISLOGIN, true)
            SharePrefUtil.saveString(this, Constants.ACCOUNT, str_account)
            SharePrefUtil.saveString(this, Constants.PASSWORD, str_pwd)
            activity(MainActivity::class.java)
            finish()

        }else if (key == Constants.REQUEST_ERROR) {
            Toast.makeText(this, `object` as String, Toast.LENGTH_SHORT).show()
        }
    }
}