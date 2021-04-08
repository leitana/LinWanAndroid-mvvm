package com.lx.linwanandroid_mvvm.koin

import com.lx.linwanandroid_mvvm.ui.home.HomeViewModel
import com.lx.linwanandroid_mvvm.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @title：KoinModule
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/05
 */
val viewModelModule = module {
    viewModel { LoginViewModel() }
    viewModel { HomeViewModel() }
}

val appModule = listOf(viewModelModule)