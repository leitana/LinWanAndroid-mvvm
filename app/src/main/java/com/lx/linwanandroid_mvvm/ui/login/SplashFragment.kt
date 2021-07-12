package com.lx.linwanandroid_mvvm.ui.login

import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMFragment
import com.lx.linwanandroid_mvvm.databinding.FragmentSplashBinding

/**
 * @title：SplashFragment
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/12
 */
class SplashFragment(val res: Int): BaseVMFragment<FragmentSplashBinding>(R.layout.fragment_splash) {
    override fun initView() {
        binding.ivSplash.setImageResource(res)
    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}