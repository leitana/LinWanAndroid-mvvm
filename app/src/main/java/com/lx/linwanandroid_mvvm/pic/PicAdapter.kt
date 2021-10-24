package com.lx.linwanandroid_mvvm.pic

import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.ImageUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.base.BaseViewModel
import com.lx.linwanandroid_mvvm.utils.ImageLoader

/**
 * @title：PicAdapter
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/10/23
 */
class PicAdapter: BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image) {
    override fun convert(holder: BaseViewHolder, item: String) {
//        holder.getView<TextView>(R.id.posTxt).text = item.id.toString()
        ImageLoader.load(context, item, holder.getView<ImageView>(R.id.imageView))
    }
}