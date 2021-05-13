package com.tencent.liteav.trtccalling.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.tencent.liteav.R

/**
 * @title：ImageLoader
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/06/29
 */
object ImageLoader {

    fun load(context: Context?, url: String?, iv: ImageView) {
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


    /**
     * 带token的glide
     */
    fun load(context: Context?, url: String?, iv: ImageView, mToken: String) {
        val glideUrl: GlideUrl = GlideUrl(
            url,
            LazyHeaders.Builder()
                .addHeader("token", mToken)
                .build()
        )

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