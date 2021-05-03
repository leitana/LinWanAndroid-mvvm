package com.lx.linwanandroid_mvvm.videoCall.navigation

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.databinding.ItemNavigationBinding

/**
 * @title：NavigationAdapter
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/30
 */
class NavigationAdapter: BaseQuickAdapter<RTCItemEntity, BaseViewHolder>(R.layout.item_navigation) {

    /**
     * 当 ViewHolder 创建完毕以后，会执行此回掉
     * 可以在这里做任何你想做的事情
     */
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        // 绑定 view
        DataBindingUtil.bind<ItemNavigationBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: RTCItemEntity) {
        val binding = DataBindingUtil.getBinding<ItemNavigationBinding>(holder.itemView)!!
        binding.viewModel = item
    }
}