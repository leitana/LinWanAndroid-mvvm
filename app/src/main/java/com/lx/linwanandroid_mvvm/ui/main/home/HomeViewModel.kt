package com.lx.linwanandroid_mvvm.ui.main.home

import com.lx.linwanandroid_mvvm.base.BaseViewModel

/**
 * @title：HomeViewModel
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/13
 */
class HomeViewModel : BaseViewModel(){
    private val homeRepository by lazy { HomeRepository() }
    enum class StateView{
        NORMAL, ERROR, EMPTY, NETWORK_ERROR, LODING
    }
}