package com.lx.linwanandroid_mvvm.ui.main

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseVMActivity
import com.lx.linwanandroid_mvvm.databinding.ActivityHomeBinding
import com.lx.linwanandroid_mvvm.model.bean.Title
import com.lx.linwanandroid_mvvm.ui.main.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @title：HomeActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/06
 */
class MainActivity: BaseVMActivity(){

    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_KNOWLEDGE = 0x02
    private val FRAGMENT_NAVIGATION = 0x03
    private val FRAGMENT_PROJECT = 0x04
    private val FRAGMENT_WECHAT = 0x05

    private var mIndex = FRAGMENT_HOME

    private var mHomeFragment: HomeFragment? = null
//    private var mKnowledgeTreeFragment: KnowledgeTreeFragment? = null
//    private var mWeChatFragment: WeChatFragment? = null
//    private var mNavigationFragment: NavigationFragment? = null
//    private var mProjectTreeFragment: ProjectTreeFragment? = null

    private val binding by binding<ActivityHomeBinding>(R.layout.activity_home)
    private val homeViewModel by viewModel<MainViewModel>()

    override fun initView() {
        binding.run {
            title = Title("主页", mThemeColor)

            drawerLayout.run {
                val toggle = ActionBarDrawerToggle(
                    this@MainActivity,
                    this,
                    icToolbar.toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close)
                addDrawerListener(toggle)
                toggle.syncState()
            }

            initNavView()
            showFragment(FRAGMENT_HOME)
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }

    private fun initNavView() {
        binding.bottomNavigation.itemIconTintList = createColorStateList(this@MainActivity, mThemeColor)
        binding.bottomNavigation.itemTextColor = createColorStateList(this@MainActivity, mThemeColor)
    }

    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = index
        when(index) {
            FRAGMENT_HOME -> {
                binding.title = Title(getString(R.string.app_name), mThemeColor)
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment()
                    transaction.add(binding.icContainer.id, mHomeFragment!!, "home")
                } else {
                    transaction.show(mHomeFragment!!)
                }
            }
        }

        transaction.commit()
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
//        mKnowledgeTreeFragment?.let { transaction.hide(it) }
//        mWeChatFragment?.let { transaction.hide(it) }
//        mNavigationFragment?.let { transaction.hide(it) }
//        mProjectTreeFragment?.let { transaction.hide(it) }
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