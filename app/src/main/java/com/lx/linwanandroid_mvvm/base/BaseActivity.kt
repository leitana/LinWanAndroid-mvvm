package com.lx.linwanandroid_mvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @title：BaseActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/03/26
 */
abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLaytouResId())
        initView()
        initData()
    }

    abstract fun getLaytouResId(): Int
    abstract fun initView()
    abstract fun initData()
}