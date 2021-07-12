package com.lx.linwanandroid_mvvm.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.lx.linwanandroid_mvvm.R
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @title：SplashActivity
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/11
 */
class SplashActivity: AppCompatActivity() {
    private val adapter2: SplashAdapter2 by lazy { SplashAdapter2() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashDot.totalIndex = 3
        splashDot.curIndex = 1

        val imageList: MutableList<Int> = mutableListOf()
        imageList.add(R.drawable.ic_splash11)
        imageList.add(R.drawable.ic_splash22)
        imageList.add(R.drawable.ic_splash33)
        adapter2.setList(imageList)
        viewpager.run {
            adapter = adapter2
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    splashDot.curIndex = position + 1
                }
            })
        }

        lifecycleScope.launch {
            delay(2000)
            viewpager.currentItem = 1
            delay(2000)
            viewpager.currentItem = 2
        }
    }
}