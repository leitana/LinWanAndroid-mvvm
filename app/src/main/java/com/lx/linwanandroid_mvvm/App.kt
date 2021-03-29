package com.lx.linwanandroid_mvvm

import android.app.Application
import android.content.Context
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
}