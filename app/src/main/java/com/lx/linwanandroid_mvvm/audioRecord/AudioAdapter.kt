package com.lx.linwanandroid_mvvm.audioRecord

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.databinding.ItemMainListBinding
import com.lx.linwanandroid_mvvm.databinding.ItemNormalAudioBinding
import com.lx.linwanandroid_mvvm.databinding.ItemSelectAudioBinding
import com.lx.linwanandroid_mvvm.model.bean.HomeArticle

/**
 * @title：AudioAdapter
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/05/04
 */
class AudioAdapter(private var datas: MutableList<AudioBean>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemViewType(position: Int): Int {
        val audioBean = datas[position]
        return if (!audioBean.isSelect) {
            0
        } else {
            1
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_normal_audio, parent, false)
            return ViewHolderNormal(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_select_audio, parent, false)
            return ViewHolderSelect(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderNormal) {
            holder.binding.data = datas[position]
        } else if (holder is ViewHolderSelect) {
            holder.binding.data = datas[position]
            holder.binding.ivPlay.setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int = datas.size

    class ViewHolderNormal(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemNormalAudioBinding>(itemView)!!
    }

    class ViewHolderSelect(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemSelectAudioBinding>(itemView)!!
    }

    fun setData(datas: MutableList<AudioBean>) {
        this.datas = datas
        notifyDataSetChanged()
    }
}