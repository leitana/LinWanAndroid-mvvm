package com.lx.linwanandroid_mvvm.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.utils.StatusBarUtil

/**
 * @title：BaseVMActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/03/26
 */
abstract class BaseVMActivity : AppCompatActivity(){

    protected inline fun <reified T: ViewDataBinding> binding(@LayoutRes resId: Int): Lazy<T> = lazy {
        DataBindingUtil.setContentView<T>(this, resId).apply {
            lifecycleOwner = this@BaseVMActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initColor()
        startObserve()
        initView()
        initData()
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()

    fun initColor() {
        StatusBarUtil.setColor(this, resources.getColor(R.color.colorPrimary), 0)
    }
}