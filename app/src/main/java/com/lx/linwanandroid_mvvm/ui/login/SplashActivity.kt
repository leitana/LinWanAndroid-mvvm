package com.lx.linwanandroid_mvvm.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lx.linwanandroid_mvvm.R
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * @title：SplashActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/11
 */
class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashDot.totalIndex = 5
        splashDot.curIndex = 3
//        splashDot.invalidate()
    }
}