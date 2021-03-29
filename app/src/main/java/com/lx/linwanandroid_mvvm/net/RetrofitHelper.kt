package com.lx.linwanandroid_mvvm.net

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.lx.linwanandroid_mvvm.App
import com.lx.linwanandroid_mvvm.BuildConfig
import com.lx.linwanandroid_mvvm.api.ApiService
import com.lx.linwanandroid_mvvm.constant.Constant
import com.lx.linwanandroid_mvvm.net.interceptor.CacheInterceptor
import com.lx.linwanandroid_mvvm.net.interceptor.HeaderInterceptor
import com.lx.linwanandroid_mvvm.net.interceptor.SaveCookieInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @title：RetrofitHelper
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/03/29
 */
object RetrofitHelper {

    /**Cookie*/
    private val cookiePersistor = SharedPrefsCookiePersistor(App.context)
    private val cookieJar = PersistentCookieJar(SetCookieCache(), cookiePersistor)


    private const val DEFAULT_TIMEOUT: Long = 15

    private var retrofit: Retrofit? = null

    val service: ApiService by lazy {
        getRetrofit()!!.create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit?{
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val cacheFile = File(App.context.cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50)

        val builder = OkHttpClient().newBuilder()
        builder.run {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(HeaderInterceptor())
            addInterceptor(SaveCookieInterceptor())
//            addInterceptor(CacheInterceptor())
            cookieJar(cookieJar)
            cache(cache)
            connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }
        return builder.build()
    }

    /**清除Cookie*/
    fun clearCookie() = cookieJar.clear()

    /**是否有Cookie*/
    fun hasCookie() = cookiePersistor.loadAll().isNotEmpty()

}