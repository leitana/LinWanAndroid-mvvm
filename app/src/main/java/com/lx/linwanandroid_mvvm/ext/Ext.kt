package com.lx.linwanandroid_mvvm.ext

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * @title：Ext
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/03/31
 */
fun Fragment.showToast(string: String){
    Toast.makeText(this.activity, string, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}