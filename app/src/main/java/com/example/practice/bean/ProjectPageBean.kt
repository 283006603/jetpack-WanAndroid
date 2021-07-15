package com.example.practice.bean

import androidx.fragment.app.Fragment

/**
 * Created By 大苏打
 * on 2020/9/23
 */
class ProjectPageBean {
    var fragment: Fragment? = null
    var title: String? = null
    var id = 0
    override fun toString(): String {
        return "ProjectPageBean{fragment=$fragment, title='$title', id=$id}"
    }
}