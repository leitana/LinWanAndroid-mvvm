package com.lx.linwanandroid_mvvm.ui.main.knowledge

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.model.bean.KnowledgeSystem

/**
 * @title：KnowledgeTreeAdapter
 * @projectName LinWanAndroid
 * @description: <Description>
 * @author linxiao
 * @data Created in 2020/08/21
 */
class KnowledgeTreeAdapter():
    BaseQuickAdapter<KnowledgeSystem, BaseViewHolder>
        (R.layout.item_knowledge_tree_list) {
    override fun convert(holder: BaseViewHolder, item: KnowledgeSystem) {
        holder.setText(R.id.title_first, item.name)
        item.children.let {
            holder.setText(R.id.title_second,
                it?.joinToString("   ", transform = { child ->
                    Html.fromHtml(child.name)
                })
            )
        }
    }
}