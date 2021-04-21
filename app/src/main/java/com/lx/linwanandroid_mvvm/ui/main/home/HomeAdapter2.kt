package com.lx.linwanandroid_mvvm.ui.main.home

import android.view.View
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.databinding.ItemMainListBinding
import com.lx.linwanandroid_mvvm.model.bean.HomeArticle

/**
 * @title：HomeAdapter2
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/20
 */
class HomeAdapter2: BaseQuickAdapter<HomeArticle.DatasBean, BaseViewHolder>(R.layout.item_main_list), LoadMoreModule {

    /**
     * 当 ViewHolder 创建完毕以后，会执行此回掉
     * 可以在这里做任何你想做的事情
     */
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        // 绑定 view
        DataBindingUtil.bind<ItemMainListBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: HomeArticle.DatasBean) {
        val binding = DataBindingUtil.getBinding<ItemMainListBinding>(holder.itemView)!!
        binding.viewModel = item

        binding.tvArticleTag.run {
            if (!item.tags.isNullOrEmpty()) {
                visibility = View.VISIBLE
                text = item.tags!![0].name
            } else {
                visibility = View.GONE
            }
        }

        val authorStr = if (!item.author.isNullOrBlank()) item.author else item.shareUser
        binding.tvArticleAuthor.text = authorStr
    }
}