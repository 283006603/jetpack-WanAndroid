package com.example.practice.view

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.practice.R
import com.example.practice.base.BaseActivity
import com.example.practice.databinding.ActivityMainBinding
import com.example.practice.view.fragment.MainFragment
import com.example.practice.view.fragment.MineFragment
import com.example.practice.view.fragment.ProjectFragment
import com.example.practice.view.fragment.SystemNavigationFragment
import kotlin.system.exitProcess


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var mainVb1: RadioButton? = null
    private var mainVb2: RadioButton? = null
    private var mainVb3: RadioButton? = null
    private var mainVb4: RadioButton? = null
    private var mainVg: RadioGroup? = null
    private var mainFrameLayout: FrameLayout? = null
    private val fragments: MutableList<Fragment> = ArrayList()
    private var fragmentManager: FragmentManager? = null
    private var currentSelectedId: Int? = R.id.main_vb1
    var lastBackTime: Long = 0


    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        mainVb1 = binding.mainVb1
        mainVb2 = binding.mainVb2
        mainVb3 = binding.mainVb3
        mainVb4 = binding.mainVb4
        mainVg = binding.mainVg
        mainFrameLayout = binding.flMainContainer

        fragmentManager = supportFragmentManager
        creatFragment()
        selectFragment(0)
        mainVb1?.isChecked = true
    }

    private fun selectFragment(index: Int) {
        val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
        for (i in 0 until fragments.size) {
            if (index == i) {
                ft.show(fragments[i])
            } else {
                ft.hide(fragments[i])
            }
        }
        ft.commit()
    }

    private fun creatFragment() {
        val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
        val homeFragment = MainFragment()
        ft.add(R.id.fl_main_container, homeFragment)
        fragments.add(homeFragment)
        //--
        val projectFragment = ProjectFragment()
        ft.add(R.id.fl_main_container, projectFragment)
        fragments.add(projectFragment)
        //--
        val systemNavigationFragment = SystemNavigationFragment()
        ft.add(R.id.fl_main_container, systemNavigationFragment)
        fragments.add(systemNavigationFragment)
        //--
        val mineFragment = MineFragment()
        ft.add(R.id.fl_main_container, mineFragment)
        fragments.add(mineFragment)
        //--
        ft.commit()
    }

    override fun initListener() {
        mainVg?.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == currentSelectedId) {
                return@setOnCheckedChangeListener
            }
            currentSelectedId=checkedId
            when(checkedId){
                R.id.main_vb1 ->selectFragment(0)
                R.id.main_vb2 ->selectFragment(1)
                R.id.main_vb3 ->selectFragment(2)
                R.id.main_vb4 ->selectFragment(3)
            }


        }

    }

    override fun getRemoteData() {
    }

    override fun initViewModel() {
    }

    override fun useImmersionBar(): Boolean {
        return true
    }

    override fun onBackPress() {
        super.onBackPress()
        var currentBackTime = System.currentTimeMillis()
        if (currentBackTime - lastBackTime > 2000) {
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_LONG).show()
            lastBackTime = currentBackTime
        } else {
            super.onBackPress()
            exitProcess(0)
        }
    }

}