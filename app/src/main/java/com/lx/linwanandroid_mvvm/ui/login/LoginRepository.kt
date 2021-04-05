package com.lx.linwanandroid_mvvm.ui.login

import com.lx.linwanandroid_mvvm.model.api.apiData
import com.lx.linwanandroid_mvvm.net.RetrofitHelper

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
}