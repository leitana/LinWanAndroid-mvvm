package com.lx.linwanandroid_mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @title：BaseVMFragment
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/03/26
 */
abstract class BaseVMFragment<T: ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment(layoutId){

    lateinit var binding: T

    protected fun <T: ViewDataBinding> binding(inflater: LayoutInflater, @LayoutRes res: Int, container: ViewGroup?): T =
        DataBindingUtil.inflate<T>(inflater, layoutId, container, false).apply {
            lifecycleOwner = this@BaseVMFragment
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(inflater, layoutId, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startObserve()
        initView()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()
}