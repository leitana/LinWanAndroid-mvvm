package com.lx.linwanandroid_mvvm.api

import com.lx.linwanandroid_mvvm.model.api.ApiResponse
import com.lx.linwanandroid_mvvm.model.bean.*
import io.reactivex.Observable
import retrofit2.http.*

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

    /**
     * 获取轮播图
     * http://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    suspend fun getBanners(): ApiResponse<List<Banner>>

    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * [pageNum]
     */
    @GET("article/list/{pageNum}/json")
    suspend fun getArticles(@Path("pageNum") pageNum: Int): ApiResponse<HomeArticle>

    /**
     * 获取首页置顶文章列表
     * http://www.wanandroid.com/article/top/json
     */
    @GET("article/top/json")
    suspend fun getTopArticles(): ApiResponse<MutableList<HomeArticle.DatasBean>>

    /**
     * 知识体系
     * https://www.wanandroid.com/tree/json
     */
    @GET("tree/json")
    suspend fun getKnowledgeTree(): ApiResponse<MutableList<KnowledgeSystem>>

    /**
     * 知识体系下的文章
     * https://www.wanandroid.com/article/list/0/json?cid=60
     */
    @GET("article/list/{page}/json")
    suspend fun getKnowledge(@Path("page") page: Int, @Query("cid") cid: Int): ApiResponse<KnowledgeSysArticle>
}