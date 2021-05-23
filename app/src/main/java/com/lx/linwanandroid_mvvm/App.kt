package com.lx.linwanandroid_mvvm

import android.app.Application
import android.content.Context
import com.lx.linwanandroid_mvvm.koin.appModule
import io.rong.imkit.RongIM
import io.rong.push.RongPushClient
import io.rong.push.pushconfig.PushConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.properties.Delegates


/**
 * @title：App
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/03/29
 */
class App: Application() {
    companion object {
        var context: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
        val config = PushConfig.Builder()
            .build()
        RongPushClient.setPushConfig(config)
        RongIM.init(this, "pwe86ga5psyv6")
    }
}