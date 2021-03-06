package com.lx.linwanandroid_mvvm.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.lx.linwanandroid_mvvm.App
import com.lx.linwanandroid_mvvm.R

/**
 * @title：ImageLoader
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/29
 */
object ImageLoader {
    // 1.开启无图模式 2.非WiFi环境 不加载图片
    private val isLoadImage = !SettingUtil.getIsNoPhotoMode() || NetWorkUtil.isWifi(App.context)

    fun load(context: Context?, url: String?, iv: ImageView){
        if (isLoadImage) {
            iv.apply {
                Glide.with(context!!).clear(iv)
                val options = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.bg_placeholder)

                Glide.with(context!!)
                    .load(url)
                    .transition(DrawableTransitionOptions().crossFade())
                    .apply(options)
                    .into(iv)
            }
        }
    }


    /**
     * 带token的glide
     */
    fun load(context: Context?, url: String?, iv: ImageView, mToken: String){
       val glideUrl: GlideUrl = GlideUrl(url,
           LazyHeaders.Builder()
               .addHeader("token", mToken)
               .build())

        if (isLoadImage) {
            iv.apply {
                Glide.with(context!!).clear(iv)
                val options = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.bg_placeholder)

                Glide.with(context!!)
                    .load(glideUrl)
                    .transition(DrawableTransitionOptions().crossFade())
                    .apply(options)
                    .into(iv)
            }
        }
    }
}