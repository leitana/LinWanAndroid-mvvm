package com.lx.linwanandroid_mvvm.koin

import com.lx.linwanandroid_mvvm.ui.main.MainViewModel
import com.lx.linwanandroid_mvvm.ui.login.LoginViewModel
import com.lx.linwanandroid_mvvm.ui.main.home.HomeViewModel
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
    viewModel { MainViewModel() }
    viewModel { HomeViewModel() }
}

val appModule = listOf(viewModelModule)