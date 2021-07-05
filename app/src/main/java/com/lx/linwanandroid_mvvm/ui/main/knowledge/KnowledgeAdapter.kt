package com.lx.linwanandroid_mvvm.ui.main.knowledge

import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.databinding.ItemKnowledgeListBinding
import com.lx.linwanandroid_mvvm.databinding.ItemMainListBinding
import com.lx.linwanandroid_mvvm.model.bean.KnowledgeSysArticle
import com.lx.linwanandroid_mvvm.utils.ImageLoader

/**
 * @title：KnowledgeAdapter
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/06/28
 */
class KnowledgeAdapter: BaseQuickAdapter<KnowledgeSysArticle.DatasBean, BaseViewHolder>(R.layout.item_knowledge_list), LoadMoreModule {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemKnowledgeListBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: KnowledgeSysArticle.DatasBean) {
        val binding = DataBindingUtil.getBinding<ItemKnowledgeListBinding>(holder.itemView)!!
        item ?: return
        holder ?: return
        val authorStr = if (item.author?.isNotEmpty()!!) item.author else item.shareUser
        holder.setText(R.id.tv_article_title, Html.fromHtml(item.title))
            .setText(R.id.tv_article_author, authorStr)
            .setText(R.id.tv_article_date, item.niceDate)
            .setImageResource(R.id.iv_like,
                if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not
            )
        val chapterName = when {
            item.chapterName?.isNotEmpty()?.let { item.superChapterName?.isNotEmpty()?.and(it) }!! ->
                "${item.superChapterName} / ${item.chapterName}"
            item.superChapterName?.isNotEmpty()!! -> item.superChapterName
            item.chapterName?.isNotEmpty()!! -> item.chapterName
            else -> ""
        }
        holder.setText(R.id.tv_article_chapterName, chapterName)

        if (!TextUtils.isEmpty(item.envelopePic)) {
            holder.getView<ImageView>(R.id.iv_article_thumbnail)
                .visibility = View.VISIBLE
            context?.let {
                ImageLoader.load(it, item.envelopePic, holder.getView(R.id.iv_article_thumbnail))
            }
        } else {
            holder.getView<ImageView>(R.id.iv_article_thumbnail)
                .visibility = View.GONE
        }
    }
}