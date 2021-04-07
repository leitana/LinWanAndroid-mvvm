package com.lx.linwanandroid_mvvm.ui.login

import com.lx.linwanandroid_mvvm.model.api.ApiResponse
import com.lx.linwanandroid_mvvm.model.api.FlowResponse
import com.lx.linwanandroid_mvvm.model.api.apiData
import com.lx.linwanandroid_mvvm.model.bean.Login
import com.lx.linwanandroid_mvvm.model.exception.ExceptionHandle
import com.lx.linwanandroid_mvvm.net.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @title：LoginRepository
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/05
 */
class LoginRepository {
    suspend fun login(userName: String, password: String) =
            RetrofitHelper.service.login(userName, password).apiData()

    suspend fun loginByFlow(userName: String, password: String) = flow {
        try {
            val login = RetrofitHelper.service.login(userName, password)
            emit(FlowResponse.Success(login.data))
        } catch (e: Exception) {
            emit(FlowResponse.Error(ExceptionHandle.handleException(e)))
        }

    }.flowOn(Dispatchers.IO)
}