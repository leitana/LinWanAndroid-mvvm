package com.lx.linwanandroid_mvvm.ui.home

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.app.ActionBarDrawerToggle
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMActivity
import com.lx.linwanandroid_mvvm.databinding.ActivityHomeBinding
import com.lx.linwanandroid_mvvm.model.bean.Title
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @title：HomeActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/06
 */
class HomeActivity: BaseVMActivity(){

    private val binding by binding<ActivityHomeBinding>(R.layout.activity_home)
    private val homeViewModel by viewModel<HomeViewModel>()

    override fun initView() {
        binding.run {
            title = Title("主页")

            drawerLayout.run {
                val toggle = ActionBarDrawerToggle(
                    this@HomeActivity,
                    this,
                    icToolbar.toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close)
                addDrawerListener(toggle)
                toggle.syncState()
            }



        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }

    private fun initNavView() {
        binding.bottomNavigation.itemIconTintList = createColorStateList(this@HomeActivity, mThemeColor)
        binding.bottomNavigation.itemTextColor = createColorStateList(this@HomeActivity, mThemeColor)
    }

    /**
     * 生成ColorStateList
     */
    private fun createColorStateList(context: Context, color: Int): ColorStateList? {
        val colors = intArrayOf(
//            ContextCompat.getColor(context, color),
            color,
            resources.getColor(R.color.item_desc)
        )
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_checked)
        states[1] = intArrayOf(-android.R.attr.state_checked)
        return ColorStateList(states, colors)
    }
}