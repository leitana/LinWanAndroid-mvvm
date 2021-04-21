package com.lx.linwanandroid_mvvm.ui.main.home

import com.lx.linwanandroid_mvvm.model.api.ApiResponse
import com.lx.linwanandroid_mvvm.model.api.FlowResponse
import com.lx.linwanandroid_mvvm.model.api.apiData
import com.lx.linwanandroid_mvvm.model.api.onSuccess
import com.lx.linwanandroid_mvvm.model.bean.HomeArticle
import com.lx.linwanandroid_mvvm.model.exception.ExceptionHandle
import com.lx.linwanandroid_mvvm.net.RetrofitHelper
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip

/**
 * @title：MainRepository
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/13
 */
class HomeRepository {
//    suspend fun getArticles(pageNum: Int) = RetrofitHelper.service.getArticles(pageNum).apiData()

    suspend fun getArticles(page: Int) = flow {
//        try {
//            val homeArticle = RetrofitHelper.service.getArticles(page)
//            emit(FlowResponse.Success(homeArticle.data))
//        } catch (e: Exception) {
//            emit(FlowResponse.Error(ExceptionHandle.handleException(e)))
//        }
        val homeArticle = RetrofitHelper.service.getArticles(page)
        emit(homeArticle)
    }

    suspend fun getTopArticles() = flow {
//        try {
//            val topArticles = RetrofitHelper.service.getTopArticles()
//            emit(FlowResponse.Success(topArticles.data))
//        } catch (e: Exception) {
//            emit(FlowResponse.Error(ExceptionHandle.handleException(e)))
//        }
        val topArticles = RetrofitHelper.service.getTopArticles()
        emit(topArticles)
    }

    suspend fun getHomeList(page: Int) = getTopArticles().zip(getArticles(page)) { t1, t2 ->
        t1.data.forEach {
            // 置顶数据中没有标识，手动添加一个标识
            it.top = true
        }
        t2.data.datas?.addAll(0, t1.data)

        t2.apiData()
    }

    suspend fun getBanner() = RetrofitHelper.service.getBanners().apiData()
}