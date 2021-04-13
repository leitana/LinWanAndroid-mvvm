package com.lx.linwanandroid_mvvm.ui.main.home

import com.lx.linwanandroid_mvvm.model.api.apiData
import com.lx.linwanandroid_mvvm.net.RetrofitHelper

/**
 * @title：MainRepository
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/13
 */
class HomeRepository {
    suspend fun getArticles(pageNum: Int) = RetrofitHelper.service.getArticles(pageNum).apiData()
}