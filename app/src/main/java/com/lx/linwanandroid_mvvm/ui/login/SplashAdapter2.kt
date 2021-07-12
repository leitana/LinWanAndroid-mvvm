package com.lx.linwanandroid_mvvm.ui.login

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid_mvvm.R

/**
 * @title：SplashAdapter2
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/07/12
 */
class SplashAdapter2: BaseQuickAdapter<Int, BaseViewHolder>(R.layout.fragment_splash) {
    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.getView<ImageView>(R.id.iv_splash).setImageResource(item)
    }
}