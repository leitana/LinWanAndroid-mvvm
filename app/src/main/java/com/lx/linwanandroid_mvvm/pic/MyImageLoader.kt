package com.lx.linwanandroid_mvvm.pic

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.iielse.imageviewer.core.ImageLoader
import com.github.iielse.imageviewer.core.Photo

/**
 * @title：MyImageLoader
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/10/23
 */
class MyImageLoader: ImageLoader {
    override fun load(view: ImageView, data: Photo, viewHolder: RecyclerView.ViewHolder) {
        val it = (data as? PicData?)?.url ?: return
        Glide.with(view).load(it)
            .placeholder(view.drawable)
            .into(view)
    }



}