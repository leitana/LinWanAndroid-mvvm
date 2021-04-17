package com.lx.linwanandroid_mvvm.ui.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.databinding.ItemMainListBinding
import com.lx.linwanandroid_mvvm.model.bean.HomeArticle

/**
 * @title：HomeAdapter
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/04/14
 */
class HomeAdapter(private val mContext: Context, private var datas: MutableList<HomeArticle.DatasBean>): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.item_main_list, parent, false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = datas[position]
        holder.binding.viewModel = data

        holder.binding.tvArticleTag.run {
            if (!data.tags.isNullOrEmpty()) {
                visibility = View.VISIBLE
                text = data.tags!![0].name
            } else {
                visibility = View.GONE
            }
        }

        val authorStr = if (!data.author.isNullOrBlank()) data.author else data.shareUser
        holder.binding.tvArticleAuthor.text = authorStr
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemMainListBinding>(itemView)!!
    }

    fun setData(datas: MutableList<HomeArticle.DatasBean>) {
        this.datas = datas
        notifyDataSetChanged()
    }
}