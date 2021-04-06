package com.lx.linwanandroid_mvvm.ui.home

import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMActivity
import com.lx.linwanandroid_mvvm.databinding.ActivityHomeBinding

/**
 * @title：HomeActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/06
 */
class HomeActivity: BaseVMActivity(){

    private val binding by binding<ActivityHomeBinding>(R.layout.activity_home)

    override fun initView() {
        binding.run {

        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}