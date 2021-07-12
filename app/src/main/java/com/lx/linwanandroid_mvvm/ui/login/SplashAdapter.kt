package com.lx.linwanandroid_mvvm.ui.login

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @title：SplashAdapter
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/12
 */
class SplashAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    private val fragmentList = mutableListOf<Fragment>()
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}