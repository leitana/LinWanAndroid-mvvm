package com.lx.linwanandroid_mvvm.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.afollestad.materialdialogs.color.CircleView
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.constant.Constant
import com.lx.linwanandroid_mvvm.utils.Preference
import com.lx.linwanandroid_mvvm.utils.SettingUtil
import com.lx.linwanandroid_mvvm.utils.StatusBarUtil
import java.util.*

/**
 * @title：BaseVMActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/03/26
 */
abstract class BaseVMActivity : AppCompatActivity(){

    var mThemeColor: Int by Preference(Constant.THEME_COLOR, -1)


    protected inline fun <reified T : ViewDataBinding> binding(@LayoutRes resId: Int): Lazy<T> = lazy {
        DataBindingUtil.setContentView<T>(this, resId).apply {
            lifecycleOwner = this@BaseVMActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initColor()
        startObserve()
        initView()
        initData()
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()

    open fun initColor(){

        mThemeColor = if (!SettingUtil.isNightMode()) {
            SettingUtil.getColor()
        } else {
            resources.getColor(R.color.colorPrimary)
        }
        StatusBarUtil.setColor(this, mThemeColor, 0)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (SettingUtil.getNavBar()) {
                window.navigationBarColor = CircleView.shiftColorDown(mThemeColor)
            } else {
                window.navigationBarColor = Color.BLACK
            }
        }

    }
}