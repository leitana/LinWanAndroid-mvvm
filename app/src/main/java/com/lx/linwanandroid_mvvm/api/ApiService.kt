package com.lx.linwanandroid_mvvm.api

import com.lx.linwanandroid_mvvm.model.api.ApiResponse
import com.lx.linwanandroid_mvvm.model.bean.Login
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @title：ApiService
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/03/29
 */
interface ApiService {
    /**
     * 登录
     * https://www.wanandroid.com/user/login
     * [username] 用户名
     * [password] 密码
     */
    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(
            @Field("username") username: String,
            @Field("password") password: String): ApiResponse<Login>
}